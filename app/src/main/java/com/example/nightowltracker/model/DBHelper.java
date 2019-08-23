package com.example.nightowltracker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.util.ArrayList;

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

    public void dropTable(String tableName){
        String sql = "DROP TABLE IF EXISTS " + tableName;
        this.getWritableDatabase().execSQL(sql);
    }

    public void addFK(String alterTableSqlString) {
        this.getWritableDatabase().execSQL(alterTableSqlString);
    }

    // FKs
    public String setForeignKeyString(String fkName, String fkColName, String rTableName, String rTableColName) {
        return "PRAGMA foreign_keys=off; " +
                "BEGIN TRANSACTION; " +
                "CONSTRAINT " + fkName + " FOREIGN KEY (" + fkColName + ") REFERENCES " + rTableName + "(" + rTableColName + ") " +
                "COMMIT;" +
                "PRAGMA foreign_keys=on;";
    }

    public boolean insertIntoAcademicSessionTable(String title, Date startDate, Date endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("startDate", String.valueOf(startDate));
        contentValues.put("endDate", String.valueOf(endDate));

        long result = db.insert("AcademicSession", null, contentValues);
        return result != -1;
    }

    // TODO Re-usable insert statements
//    public boolean insertIntoTable(String tableName, List values){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        return true;
//    }

    public ArrayList<String> getAS() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM AcademicSession";
        ArrayList<String> terms = null;

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            // returned data
            if (cursor.moveToFirst()) {
                do {
                    terms.add(cursor.getString(0));
                    terms.add(cursor.getString(1));
                    terms.add(cursor.getString(2));
                    // display search results here or in recyclerview
                } while (cursor.moveToNext());
            }
        } else {
            System.out.println("No data found. :( ");
        }
        cursor.close();
        return terms;
    }

}