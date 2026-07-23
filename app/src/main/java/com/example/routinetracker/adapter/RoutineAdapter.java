package com.example.routinetracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.routinetracker.R;
import com.example.routinetracker.model.Routine;
import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {

    private List<Routine> routineList;
    private OnRoutineClickListener listener;

    public interface OnRoutineClickListener {
        void onRoutineClick(Routine routine);//-> Kullanıcı listeden bir rutine dokunduğunda (örneğin detayını görmek veya düzenlemek istediğinde) ne olacağını belirler.

        void onRoutineDelete(Routine routine);//-> Bir rutin silinmek istendiğinde Kişi 2'nin o swipe hareketini yaptığı an ne olacağını belirler.
    }

    public RoutineAdapter(List<Routine> routineList, OnRoutineClickListener listener) {
        this.routineList = routineList;
        this.listener = listener;

    }
    //3 zorunlu metod
    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_routine, parent, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        Routine routine = routineList.get(position);
        holder.tvReminderName.setText(routine.getTitle());
        holder.tvDateTime.setText(routine.getTime());

        // set dot color based on importance level
        int color;
        switch (routine.getImportance()) {
            case "HIGH":
                color = android.graphics.Color.parseColor("#FF0000");
                break;
            case "MEDIUM":
                color = android.graphics.Color.parseColor("#FFA500");
                break;
            default:
                color = android.graphics.Color.parseColor("#00C853");
                break;
        }
        holder.viewImportanceDot.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color)
        );
        holder.viewImportanceDot.setVisibility(View.VISIBLE);
        holder.itemView.setOnClickListener(v -> listener.onRoutineClick(routine));
    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }

    public void updateList(List<Routine> newList) {
        this.routineList = newList;
        notifyDataSetChanged();
    }
    //ViewHolder sınıfı
    public static class RoutineViewHolder extends RecyclerView.ViewHolder {
        TextView tvReminderName;
        TextView tvDateTime;
        View viewImportanceDot;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReminderName = itemView.findViewById(R.id.tvReminderName);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            viewImportanceDot = itemView.findViewById(R.id.viewImportanceDot);
        }
    }
}