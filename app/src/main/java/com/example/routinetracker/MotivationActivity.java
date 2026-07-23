package com.example.routinetracker;

import android.os.Bundle;
import android.os.Handler;//arka plandaki işçiden (Thread) gelen veriyi Android'in ana ekranına (UI Thread) güvenli bir şekilde aktarmaktır. Bunu sağlayan mekanizma android.os paketinin içinde yer alır.
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;//miras için

import com.example.routinetracker.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MotivationActivity extends AppCompatActivity {//extends AppCompatActivity: Bu sınıfın bir Android ekranı (Activity) olduğunu belirtiyoruz

    private TextView tvQuote; //Erişilebilirlik Bağı
    private Button btnFetch; //Erişilebilirlik Bağı
    private ProgressBar progressBar; //Erişilebilirlik Bağı

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Android Aktivite Yaşam Döngüsünün (Activity Lifecycle) ilk ve en önemli metodudur . Ekran ilk yaratıldığında burası tetiklenir.
        super.onCreate(savedInstanceState); //Android, önce senin kendi üst sınıfında (AppCompatActivity) yapman gereken arka plan hazırlıklarını, işletim sistemiyle olan bağlantıları ve ekran kurulumlarını bir tamamla
        setContentView(R.layout.activity_motivation); // hangi xml dosyasını kullanacağımızı söylüyoruz

        tvQuote = findViewById(R.id.tv_motivation_quote);   //java ve xml'i birbirine bağlayan köprü
        btnFetch = findViewById(R.id.btn_fetch_new); //Android Studio, projedeki tüm kaynakları (resimler, tasarımlar, ID'ler) takip edebilmek için arka planda otomatik olarak R (Resource/Kaynak) adında devasa bir Java sınıfı oluşturur.
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.btnMotivationBack).setOnClickListener(v -> finish()); // back button

        fetchQuote();//sözü otomatik getirir

        btnFetch.setOnClickListener(v -> fetchQuote()); //kullanıcı butona bastıkça sözü manuel olarak getirir.

    }
    private void fetchQuote() {
        //(Usability)
        // İnternetten veri çekilirken kullanıcıya "bekle" mesajı vermek için çarkı görünür yapıyoruz.
        progressBar.setVisibility(View.VISIBLE);
        // Kullanıcı veri yüklenirken butona üst üste basıp uygulamayı kilitlemesin diye butonu geçici olarak kapatıyoruz.
        btnFetch.setEnabled(false);
        // Ekranda eski bir söz kalmasın diye TextView'ı temizliyoruz.
        tvQuote.setText("");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String quote = "";
            String author = "";
            try {
                URL url = new URL("https://web-production-58b96.up.railway.app/quote");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                org.json.JSONArray array = new org.json.JSONArray(response.toString());
                org.json.JSONObject obj = array.getJSONObject(0);
                quote = obj.getString("q");
                author = obj.getString("a");

            } catch (Exception e) {
                quote = "Never give up!";
                author = "arda";
            }

            final String finalQuote = quote;
            final String finalAuthor = author;

            handler.post(() -> {
                tvQuote.setText(finalQuote);
                ((TextView) findViewById(R.id.tv_motivation_author)).setText("— " + finalAuthor);
                progressBar.setVisibility(View.GONE);
                btnFetch.setEnabled(true);
            });
        });
    }
}


