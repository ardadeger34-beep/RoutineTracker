package com.example.routinetracker.model;

public class Routine {
    private String id;
    private String title;
    private String time;
    private boolean completed;
    private boolean notificationEnabled;
    private String importance ;

    public Routine(String id, String title, String time, boolean notificationEnabled, String importance) {
        this.id = id; //this.id = id; this bizim javanın içindeki idyi =in sağındaki de dışarıdan tanımlanan idnin bizim kodumuza tanımlandığını ifade ediyor
        this.title = title;
        this.time = time;
        this.completed = false;
        this.notificationEnabled = notificationEnabled;
        this.importance = importance ;
    }
    //Mevcut İsmi Çekeriz Encapsulation

    //get
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public boolean isCompleted() {
        return completed;
    }//Java'da eğer bir değişkenin veri tipi boolean ise (yani sadece true veya false değeri alabiliyorsa), onun getter metodunun başına get değil, İngilizce'de soru eki olan is (mı/mi) getirilir.

    public boolean isNotificationEnabled() {
        return notificationEnabled;
    }
//set

    //Neden id için setter yok? ID bir kez oluşturulunca değişmemeli — rutinin kimliği sabit kalır, sadece içeriği değişir.
    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

}