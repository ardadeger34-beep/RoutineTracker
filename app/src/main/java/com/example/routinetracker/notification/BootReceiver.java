package com.example.routinetracker.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

// Telefon yeniden başlatıldığında, daha önce kurduğumuz alarmları
// sisteme tekrar yüklemek için kullanılan "başlangıç tetikleyicisi".
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // Telefon açılma işlemi (boot) tamamlandı mı diye kontrol ediyoruz
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            // Logcat'te hata takibi yapabilmek için mesaj yazdırdık
            Log.d("BootReceiver", "Phone is active, alarm services will restart.");

            // İleride buraya NotificationHelper'dan alarm kurma
            // fonksiyonunu çağırıp tüm alarmları güncelleyebiliriz.
        }
    }
}