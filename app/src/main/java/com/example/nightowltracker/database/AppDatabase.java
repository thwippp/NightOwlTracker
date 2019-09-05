package com.example.nightowltracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {AcademicSessionEntity.class, ClassEntity.class, LineItemEntity.class}, version = 2, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AppDatabase.db";  // was AppDatabase.db
    private static final Object LOCK = new Object(); // no race conditions
    private static volatile AppDatabase instance;  // stored in main memory

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
        }
        synchronized (LOCK) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).build();
            }
        }
        return instance;
    }

    public abstract AcademicSessionDao academicSessionDao();

    public abstract ClassDao classDao();

    public abstract LineItemDao lineItemDao();
}
