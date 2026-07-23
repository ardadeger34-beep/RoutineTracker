package com.example.routinetracker;

import com.example.routinetracker.notification.NotificationHelper;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// Alarm vaktinde sistemi uyandıran ve bildirim tetikleyen "alıcı" sınıf
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Alarm kurulurken gönderilen rutin ismini yakalıyoruz
        String routineName = intent.getStringExtra("ROUTINE_NAME");

        // Eğer bir isim tanımlanmadıysa varsayılan metni kullan
        if (routineName == null) {
            routineName = "Rutin zamanı!";
        }

        // Alarm çaldığı an NotificationHelper üzerinden bildirimi ekrana bas
        NotificationHelper.sendNotification(context, routineName);
    }
}