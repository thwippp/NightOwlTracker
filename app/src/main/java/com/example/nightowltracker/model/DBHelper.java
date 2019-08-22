package com.example.nightowltracker.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NightOwl.db";
    private static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void createTable(String tableName, String columnsAndDataTypes){
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnsAndDataTypes + ")";
        this.getWritableDatabase().execSQL(sql);
    }

    public void createTable(String tableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (sessionId INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, startDate DATE, endDate DATE)";
        this.getWritableDatabase().execSQL(sql);
    }

    public void dropTable(String tableName){
        String sql = "DROP TABLE IF EXISTS " + tableName;
        this.getWritableDatabase().execSQL(sql);
    }

}