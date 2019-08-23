package com.example.nightowltracker.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "Enrollment")

public class Enrollment {
    @PrimaryKey(autoGenerate = true)
    private int enrollmentId;
    private int userId;
    private int classId;

    @Ignore
    public Enrollment() {
    }

    public Enrollment(int enrollmentId, int userId, int classId) {
        this.enrollmentId = enrollmentId;
        this.userId = userId;
        this.classId = classId;
    }

    @Ignore
    public Enrollment(int userId, int classId) {
        this.userId = userId;
        this.classId = classId;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", userId=" + userId +
                ", classId=" + classId +
                '}';
    }

    public ArrayList<String> createTableEnrollment() {
        ArrayList enrollmentTable = new ArrayList();
        String tableName = "Enrollment";
        String columnNamesAndDataTypes = "enrollmentId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userId INTEGER, classId INTEGER";
        enrollmentTable.add(tableName);
        enrollmentTable.add(columnNamesAndDataTypes);
        return enrollmentTable;
    }

}
