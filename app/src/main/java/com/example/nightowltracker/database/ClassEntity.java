package com.example.nightowltracker.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "Class")

public class ClassEntity {
    @PrimaryKey(autoGenerate = true)
    private int classId;
    private String title;
    private String classCode;
    private String status;
    private int sessionId;

    @Ignore
    public ClassEntity() {
    }

    public ClassEntity(int classId, String title, String classCode, String status, int sessionId) {
        this.classId = classId;
        this.title = title;
        this.classCode = classCode;
        this.status = status;
        this.sessionId = sessionId;
    }

    @Ignore
    public ClassEntity(String title, String classCode, String status, int sessionId) {
        this.title = title;
        this.classCode = classCode;
        this.status = status;
        this.sessionId = sessionId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "ClassEntity{" +
                "classId=" + classId +
                ", title='" + title + '\'' +
                ", classCode='" + classCode + '\'' +
                ", status='" + status + '\'' +
                ", sessionId=" + sessionId +
                '}';
    }

    public ArrayList<String> createTableClass() {
        ArrayList classTable = new ArrayList();
        String tableName = "Class";
        String columnNamesAndDataTypes = "classId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, title TEXT, classCode TEXT, status TEXT, sessionId INTEGER";
        classTable.add(tableName);
        classTable.add(columnNamesAndDataTypes);
        return classTable;
    }

}