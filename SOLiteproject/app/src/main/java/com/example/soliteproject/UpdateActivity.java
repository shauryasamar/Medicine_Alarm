package com.example.soliteproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.DialogInterface;
//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
//import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class UpdateActivity extends AppCompatActivity {
    TextView Medicine_input, date_input, time_input;
    Button update,delete;
    String id, Medicine, date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Medicine_input = findViewById(R.id.Med_name2);
        date_input = findViewById(R.id.date2);
        time_input = findViewById(R.id.time2);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        getAndSetIntentData();
        update.setOnClickListener(v -> {
            Medicine = Medicine_input.getText().toString().trim();
            date = date_input.getText().toString().trim();
            time = time_input.getText().toString().trim();
            try (MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this)) {
                myDB.updateData(id, Medicine, date, time);
//                CustomAdapter.updateItem(clickedPosition);
            } catch (Exception e) {

                Toast.makeText(UpdateActivity.this, "Error updating data", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            finish();
        });

        delete.setOnClickListener(v -> confirmDialog());



    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("date")) {
            id = getIntent().getStringExtra("id");
            Medicine= getIntent().getStringExtra("name");
            time = getIntent().getStringExtra("time");
            date = getIntent().getStringExtra("date");

            Medicine_input.setText(Medicine);
            time_input.setText(time);
            date_input.setText(date);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + Medicine + "?");
        builder.setMessage("Are you sure you want to delete " + Medicine + "?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            try (MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this)) {
                myDB.deleteOneRow(id);
                deleteAlarm();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.create().show();
    }

    void deleteAlarm() {
        try {
            String dateStr = date;
            String timeStr = time;

            String[] dateParts = dateStr.split("/");


            String[] timeParts = timeStr.split(":");


            int year = Integer.parseInt(dateParts[2]);
            int month = Integer.parseInt(dateParts[1]) - 1;
            int day = Integer.parseInt(dateParts[0]);
            int hours = Integer.parseInt(timeParts[0]);
            int minutes = Integer.parseInt(timeParts[1]);


            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hours, minutes);


            Intent intent = new Intent(AlarmClock.ACTION_DISMISS_ALARM);


            startActivity(intent);
//            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error deleting alarm", Toast.LENGTH_SHORT).show();
        }
    }
}
