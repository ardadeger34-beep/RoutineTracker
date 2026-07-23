package com.example.routinetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

// Ayarlar sayfası: Kullanıcı tercihlerini yönetir
public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); // Tasarım dosyasını bağlar

        // Kullanıcı ayarlarını hafızada tutmak için bir dosya oluşturur
        prefs = getSharedPreferences("RoutinePrefs", Context.MODE_PRIVATE);

        // Geri dön butonu: Sayfayı kapatıp ana ekrana döner
        findViewById(R.id.btnSettingsBack).setOnClickListener(view -> finish());

        // Ses ayarı: Kullanıcıyı telefonun kendi bildirim sesi ayarlarına yönlendirir
        findViewById(R.id.btnSelectSound).setOnClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            startActivity(intent);
        });

        // Bildirim şalteri: Kullanıcının tercihini kaydeder
        SwitchCompat switchNotifications = findViewById(R.id.switchNotifications);
        switchNotifications.setChecked(prefs.getBoolean("notifications", true));
        switchNotifications.setOnCheckedChangeListener((v, isChecked) ->
                prefs.edit().putBoolean("notifications", isChecked).apply());

        // Titreşim şalteri: Kullanıcı titreşimi açıp kapatabilir
        SwitchCompat switchVibration = findViewById(R.id.switchVibration);
        switchVibration.setChecked(prefs.getBoolean("vibration", true));
        switchVibration.setOnCheckedChangeListener((v, isChecked) ->
                prefs.edit().putBoolean("vibration", isChecked).apply());
    }
}