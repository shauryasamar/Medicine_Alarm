package com.example.soliteproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private final Context context;
    Activity activity;
    private final ArrayList<String> med_id, medicine_name, date, time;

    public CustomAdapter(Activity activity, Context context, ArrayList<String> med_id, ArrayList<String> medicine_name, ArrayList<String> date, ArrayList<String> time) {
        this.context = context;
        this.activity= activity;
        this.med_id = med_id;
        this.medicine_name = medicine_name;
        this.date = date;
        this.time = time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_layout_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.med_id_txt.setText(String.valueOf(med_id.get(position)));
        holder.med_Name_txt.setText(String.valueOf(medicine_name.get(position)));
        holder.date_txt.setText(String.valueOf(date.get(position)));
        holder.time_txt.setText(String.valueOf(time.get(position)));

//        final int clickedPosition = holder.getAdapterPosition();


        holder.mainLayout.setOnClickListener(v -> {

//            final int clickedPosition = holder.getAdapterPosition();
            int clickedPosition = holder.getAdapterPosition();
            if (clickedPosition != RecyclerView.NO_POSITION) {
                String clickedMedId = med_id.get(clickedPosition);
                String clickedMedicineName = medicine_name.get(clickedPosition);
                String clickedDate = date.get(clickedPosition);
                String clickedTime = time.get(clickedPosition);

                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", clickedMedId);
                intent.putExtra("name", clickedMedicineName);
                intent.putExtra("date", clickedDate);
                intent.putExtra("time", clickedTime);
                activity.startActivityForResult(intent,1);
            }

        });


    }

    @Override
    public int getItemCount() {
        return med_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView med_id_txt,med_Name_txt, date_txt, time_txt;
        LinearLayout mainLayout;
        Animation translate_anim;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            med_id_txt = itemView.findViewById(R.id.med_id_txt);
            med_Name_txt= itemView.findViewById(R.id.med_name_txt);
            date_txt = itemView.findViewById(R.id.date_txt);
            time_txt = itemView.findViewById(R.id.time_txt);
            mainLayout = itemView.findViewById(R.id.Main_layout);
            translate_anim = AnimationUtils.loadAnimation(context,R.anim.translateanim);
            mainLayout.setAnimation(translate_anim);
        }

    }
//    public static void updateItem(int position) {
//        notifyItemChanged(position);
//    }
}
