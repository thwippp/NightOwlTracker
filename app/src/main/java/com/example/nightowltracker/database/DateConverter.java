package com.example.nightowltracker.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    // TODO figure out what you want to use... date vs timestamp

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
