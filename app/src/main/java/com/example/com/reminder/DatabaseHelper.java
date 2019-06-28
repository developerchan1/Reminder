package com.example.com.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Reminder.db";
    public static final String TABLE_NAME = "Schedule_table";
    public static final String COL_1 = "TITLE";
    public static final String COL_2 = "SUBTITLE";
    public static final String COL_3 = "TIME";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" (TITLE TEXT, SUBTITLE TEXT, TIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }

    public void insertData(String title, String subtitle, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,title);
        contentValues.put(COL_2,subtitle);
        contentValues.put(COL_3,time);

        long result = db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME, null);
        return res;
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
        db.close();
    }
}
