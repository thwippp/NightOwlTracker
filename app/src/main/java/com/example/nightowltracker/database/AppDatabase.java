package com.example.nightowltracker.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {AcademicSessionEntity.class, ClassEntity.class, LineItemEntity.class}, version = 2, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AppDatabase.db";
    private static final Object LOCK = new Object(); // no race conditions
    private static volatile AppDatabase instance;  // stored in main memory
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("CREATE TABLE IF NOT EXISTS `AcademicSession` (`sessionId` INTEGER, `title` TEXT, `startDate` DATE, `endDate` DATE, PRIMARY KEY(`sessionId`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Class` (`classId` INTEGER, `title` TEXT, `classCode` TEXT, `status` TEXT, `sessionId` INTEGER, PRIMARY KEY(`classId`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `LineItem` (`lineItemId` INTEGER, `title` TEXT, `description` TEXT, `category` TEXT, `assignDate` DATE, `dueDate` DATE, `classId` INTEGER, PRIMARY KEY(`lineItemId`))");
        }
    };

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
        }
        synchronized (LOCK) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
            return instance;
        }
    }

    public abstract AcademicSessionDao academicSessionDao();

    public abstract ClassDao classDao();

    public abstract LineItemDao lineItemDao();
}


