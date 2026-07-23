package com.example.routinetracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.routinetracker.data.RoutineRepository;
import com.example.routinetracker.model.Routine;
import com.example.routinetracker.notification.NotificationHelper;

public class AddEditActivity extends AppCompatActivity {

    EditText etReminderName;
    EditText etDateTime;
    RadioGroup rgImportance;
    Button btnSave;

    RoutineRepository repository;
    String routineId = null; // null = add mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        etReminderName = findViewById(R.id.etReminderName);
        etDateTime = findViewById(R.id.etDateTime);
        rgImportance = findViewById(R.id.rgImportance);
        btnSave = findViewById(R.id.btnSave);
        findViewById(R.id.btnAddEditBack).setOnClickListener(v -> finish()); // back button

        repository = new RoutineRepository(this); // data manager
        etDateTime.setOnClickListener(v -> {

            // Telefonun şu anki güncel zaman bilgilerini çekiyoruz
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            int year = calendar.get(java.util.Calendar.YEAR);
            int month = calendar.get(java.util.Calendar.MONTH);
            int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
            int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
            int minute = calendar.get(java.util.Calendar.MINUTE);

            // saat Çarkını hazırlıyoruz (takvimden sonra otomatik açılacak)
            android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(this,
                    (timeView, selectedHour, selectedMinute) -> {

                        // takvimden gelen tarihin yanına saati de şık bir formatla ekliyoruz
                        String currentText = etDateTime.getText().toString();
                        String finalDateTime = currentText + " — " + String.format(java.util.Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);

                        etDateTime.setText(finalDateTime); // kutunun içine veriyi yazar (Örn: 10.08.2026 — 12:05)
                    }, hour, minute, true); // true = 24 saat formatı

            //  takvim ekranını hazırlıyoruz
            android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(this,
                    (dateView, selectedYear, selectedMonth, selectedDay) -> {

                        // Android'de aylar 0'dan başladığı için seçilen aya +1 ekliyoruz
                        String formattedDate = String.format(java.util.Locale.getDefault(), "%02d.%02d.%d", selectedDay, (selectedMonth + 1), selectedYear);
                        etDateTime.setText(formattedDate);

                        timePickerDialog.show();

                    }, year, month, day);

            datePickerDialog.show();
        });
        // check the users if they add or edit a reminder.
        routineId = getIntent().getStringExtra("ROUTINE_ID");
        if (routineId != null) {
            // editing mode.
            etReminderName.setText(getIntent().getStringExtra("ROUTINE_TITLE"));
            etDateTime.setText(getIntent().getStringExtra("ROUTINE_TIME"));
        }

        // save button functunallty.
        btnSave.setOnClickListener(v -> {

            //get text input from user to save the new reminder or edit existing one.
            String reminder_name = etReminderName.getText().toString(); // getText = gets the data, toString = conversion to string
            String date_time = etDateTime.getText().toString();

            int selectedId = rgImportance.getCheckedRadioButtonId(); // gives the id of the button selected.

            // convert selected radio button to importance string
            String importance;
            if (selectedId == R.id.rbLevelHigh) importance = "HIGH";
            else if (selectedId == R.id.rbLevelMedium) importance = "MEDIUM";
            else importance = "LOW";

            // depending on mode creates or edits.
            Routine routine = new Routine(
                    routineId != null ? routineId : repository.generateId(),
                    reminder_name,
                    date_time,
                    true,
                    importance
            );
            repository.saveRoutine(routine);

            // parse the date data to extract the info need for notification call.
            try {
                // split date and hour.
                String[] parts = date_time.split(" — ");
                if (parts.length == 2) {
                    // split hours and minutes
                    String[] timeParts = parts[1].split(":");
                    int hour = Integer.parseInt(timeParts[0]); // get hour as int
                    int minute = Integer.parseInt(timeParts[1]); // get minute as int
                    NotificationHelper.setRoutineAlarm(this, hour, minute, reminder_name); // schedule the alarm
                }
            } catch (Exception e) {
                // if it fails skip it.
                e.printStackTrace();
            }

            finish();
        });
    }
}