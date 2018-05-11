package com.example.lap10255.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDB.db";
    public static final String INFO_TABLE_NAME = "info";
    public static final String INFO_COLUMN_ID = "id";
    public static final String INFO_COLUMN_NAME = "name";
    public static final String INFO_COLUMN_AGE = "age";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + INFO_TABLE_NAME +
                "(id integer primary key, name text, age integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + INFO_TABLE_NAME);
        onCreate(db);
    }

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public long insertInfo(Info i) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", i.getName());
        contentValues.put("age", i.getAge());
        return db.insert("info", null, contentValues);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("select " + INFO_COLUMN_ID + ", " + INFO_COLUMN_NAME + ", " + INFO_COLUMN_AGE + " from "
                + INFO_TABLE_NAME, null);
        return res;
    }
    public boolean deleteData(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(INFO_TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        return true;
    }
}
