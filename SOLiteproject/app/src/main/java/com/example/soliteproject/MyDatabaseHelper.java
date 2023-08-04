package com.example.soliteproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String DATABASE_NAME = "medicine_alarm.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Medicine_alarm";
    private static final String COLUMN_ID = "_id";
    private static final String MEDICINE_NAME = "Medicine_name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";

//    private static final String COLUMN_DATE_TIME = "date_time";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MEDICINE_NAME + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addAlarm(String Medicine_name, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MEDICINE_NAME, Medicine_name);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TIME, time);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result != -1) {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String MED_NAME, String DATE, String TIME) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MEDICINE_NAME, MED_NAME);
        cv.put(COLUMN_DATE, DATE);
        cv.put(COLUMN_TIME, TIME);

        long results = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (results == 0) {
            Toast.makeText(context, "No rows updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
         }
            db.close();
        }
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result =db.delete(TABLE_NAME,"_id=?",new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
        }

    }

}

