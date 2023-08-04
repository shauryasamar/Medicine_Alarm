package com.example.soliteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Date;
import java.util.Locale;


public class AddActivity extends AppCompatActivity {
    EditText name, date, time;
    Button set_alar_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

        name = findViewById(R.id.Med_name);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        set_alar_time = findViewById(R.id.set_alarm);

        set_alar_time.setOnClickListener(v -> {
            if (isValidTimeFormat(time) && isValidDate(date)) {
            try (MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this)) {
                myDB.addAlarm(name.getText().toString().trim(),
                        date.getText().toString().trim(),
                        time.getText().toString().trim());
                addAlarm();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,"AddAct Error",Toast.LENGTH_SHORT).show();

            }
            } else {
                Toast.makeText(AddActivity.this, "Invalid time format", Toast.LENGTH_SHORT).show();
            }

        });



    }

    void addAlarm(){
        try {
            String dateStr = date.getText().toString().trim();
            String timeStr = time.getText().toString().trim();

            String[] dateParts = dateStr.split("/");


            String[] timeParts = timeStr.split(":");


            int year = Integer.parseInt(dateParts[2]);
            int month = Integer.parseInt(dateParts[1]) - 1;
            int day = Integer.parseInt(dateParts[0]);
            int hours = Integer.parseInt(timeParts[0]);
            int minutes = Integer.parseInt(timeParts[1]);


            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hours, minutes);


            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_HOUR, calendar.get(Calendar.HOUR_OF_DAY));
            intent.putExtra(AlarmClock.EXTRA_MINUTES, calendar.get(Calendar.MINUTE));
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, name.getText().toString().trim());
            intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);


            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error setting alarm", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isValidTimeFormat(EditText time) {

        String timeStr = time.getText().toString().trim();


        String[] parts = timeStr.split(":");
        if (parts.length != 2) {
            return false;
        }

        try {

            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);


            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
                return false;
            }
        } catch (NumberFormatException e) {

            return false;
        }

        return true;
    }

    private boolean isValidDate(EditText date) {
        String dateStr = date.getText().toString().trim();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dateStr);
            return true;
        } catch (java.text.ParseException e) {
            Toast.makeText(AddActivity.this, "Invalid date format. Please use dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}