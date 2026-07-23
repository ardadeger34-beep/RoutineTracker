package com.example.routinetracker.notification;

import com.example.routinetracker.AlarmReceiver; // AlarmReceiver'ı buraya bağladık

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import java.util.Calendar;

// Uygulamanın bildirim ve alarm işlerini yöneten "mutfak" sınıfı
public class NotificationHelper {
    public static final String CHANNEL_ID = "routine_channel";

    // Bildirimlerin ekrana düşmesi için gereken kanalı oluşturur (Android 8+ zorunluluğu)
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Reminder Notifications", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    // Gerçek bildirimi oluşturan ve kullanıcıya gösteren kısım
    public static void sendNotification(Context context, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Reminder")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true); // Tıklandığında bildirim otomatik kaybolur

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }

    // Alarmı kuran metot. Arkadaşlar, rutin eklerken bu metodu çağıracak!
    public static void setRoutineAlarm(Context context, int hour, int minute, String routineName) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("ROUTINE_NAME", routineName);

        // Alarm vaktinde AlarmReceiver'ı tetikleyecek olan anahtar
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Kullanıcının seçtiği saati sisteme yüklüyoruz
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Eğer seçilen saat geçtiyse, alarmı ertesi güne kur
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        // Alarmı kuruyoruz. "setExact" ile tam vaktinde çalışmasını garanti ediyoruz
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}