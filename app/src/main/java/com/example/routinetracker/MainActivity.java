package com.example.routinetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.routinetracker.adapter.RoutineAdapter;
import com.example.routinetracker.data.RoutineRepository;
import com.example.routinetracker.model.Routine;
import com.example.routinetracker.notification.NotificationHelper;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton btnAdd;
    ImageButton btnMotivation;

    RoutineRepository repository;
    RoutineAdapter adapter;
    ImageButton btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView); // links buttons to activity.
        btnAdd = findViewById(R.id.btnAdd);
        btnMotivation = findViewById(R.id.btnMotivation);
        btnSettings = findViewById(R.id.btnSettings);
        NotificationHelper.createNotificationChannel(this); //creates a notification channel that can pass the notificatoins.

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditActivity.class) ;
            startActivity(intent);
        }); // creates an intent for add button.

        btnMotivation.setOnClickListener(v -> {
            Intent intent = new Intent(this, MotivationActivity.class) ;
            startActivity(intent);
        }); // creates an intent for motivation button.

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }); // creates an intent for settings page.

        //layout manager tells recycler view how to manage items. Like top to bottom in date order.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new RoutineRepository(this); // creates a data manager.
        adapter = new RoutineAdapter(repository.getAllRoutines(), new RoutineAdapter.OnRoutineClickListener() { // creates a bridge between adapter and recycler view.
            @Override
            public void onRoutineClick(Routine routine) { //for editing a reminder.

                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra("ROUTINE_ID", routine.getId());
                intent.putExtra("ROUTINE_TITLE", routine.getTitle());
                intent.putExtra("ROUTINE_TIME", routine.getTime());
                startActivity(intent);
                //edits a existing reminder,
            }

            @Override
            public void onRoutineDelete(Routine routine) { // fired when a reminder is deleted.
                repository.deleteRoutine(routine.getId());
                adapter.updateList(repository.getAllRoutines());
            }
        });
        recyclerView.setAdapter(adapter); // connects the adapter and recycler.

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder target) { return false; }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getBindingAdapterPosition();

                        Routine routine = repository.getAllRoutines().get(position);
                        repository.deleteRoutine(routine.getId());
                        adapter.updateList(repository.getAllRoutines()); //deletes the reminder.
                    }
                }
        );
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override // refresh the main page automaticly.
    protected void onResume() {
        super.onResume();
        adapter.updateList(repository.getAllRoutines());
    }
}