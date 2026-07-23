package com.example.routinetracker.data;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.routinetracker.model.Routine;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoutineRepository {
    private static final String PREF_NAME = "routine_prefs"; //telefonun deposunda hangi isimle kaydedeceğimiz
    private static final String KEY_ROUTINES = "routines"; //o depoda hangi anahtarla tutacağımız
    private final SharedPreferences prefs; //

    public RoutineRepository(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public List<Routine> getAllRoutines() {
        List<Routine> list = new ArrayList<>();
        try {
            String json = prefs.getString(RoutineRepository.KEY_ROUTINES, "[]");
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Routine r = new Routine(
                        obj.getString("id"),
                        obj.getString("title"),
                        obj.getString("time"),
                        obj.getBoolean("notificationEnabled"),
                        obj.optString("importance", "LOW")
                );
                r.setCompleted(obj.getBoolean("completed"));
                list.add(r);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;

    }

    //save kısmı
    public void saveRoutine(Routine routine) {
        List<Routine> list = getAllRoutines();
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(routine.getId())) {
                list.set(i, routine);
                found = true;
                break;
            }
        }
        if (!found) list.add(routine);
        saveAll(list);
    }

    //delete kısmı
    public void deleteRoutine(String id) {
        List<Routine> list = getAllRoutines(); //(SharedPreferences içinde) o ana kadar kayıtlı olan bütün rutinleri çekip list adındaki canlı sepetimizin içine dolduruyor.
        list.removeIf(r -> r.getId().equals(id));
        saveAll(list);// güncel son halini alıyoruz ve bitiyor
    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }

    private void saveAll(List<Routine> list) {
        try {
            JSONArray array = new JSONArray();
            for (Routine r : list) {
                JSONObject obj = new JSONObject();
                obj.put("id", r.getId());
                obj.put("title", r.getTitle());
                obj.put("time", r.getTime());
                obj.put("completed", r.isCompleted());
                obj.put("notificationEnabled", r.isNotificationEnabled());
                obj.put("importance", r.getImportance());
                array.put(obj);
            }
            prefs.edit().putString(RoutineRepository.KEY_ROUTINES, array.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}