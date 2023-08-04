package com.example.soliteproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_Button;

    MyDatabaseHelper myDB;
    ArrayList<String> med_id,medicine_name, date, time;
    CustomAdapter customAdapter;
    @Override
    protected void onActivityResult(int requestCOde, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCOde,resultCode,data);
        if (resultCode == 0){
            storeDataInArray();
            recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.Recycleview);

        add_Button = findViewById(R.id.add_data);

        add_Button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });



        myDB = new MyDatabaseHelper(MainActivity.this);
        med_id = new ArrayList<>();
        medicine_name = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();

        storeDataInArray();
        customAdapter = new CustomAdapter(MainActivity.this,this,med_id, medicine_name, date, time);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


    }
@SuppressLint("NotifyDataSetChanged")
protected void onResume() {
    super.onResume();
    storeDataInArray();
    customAdapter.notifyDataSetChanged();
}


    void storeDataInArray() {
        med_id.clear();
        medicine_name.clear();
        date.clear();
        time.clear();
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                med_id.add(cursor.getString(0));
                medicine_name.add(cursor.getString(1));
                date.add(cursor.getString(2));
                time.add(cursor.getString(3));
            }
        }
    }
}
