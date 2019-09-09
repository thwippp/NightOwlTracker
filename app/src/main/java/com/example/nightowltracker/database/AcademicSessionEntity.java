package com.example.nightowltracker.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "AcademicSession")

public class AcademicSessionEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sessionId")
    private int sessionId;

    @ColumnInfo(name = "title")
    private String title;
    private Date startDate;
    private Date endDate;

    @Ignore
    public AcademicSessionEntity() {

    }

    public AcademicSessionEntity(int sessionId, String title, Date startDate, Date endDate) {
        this.sessionId = sessionId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Ignore
    public AcademicSessionEntity(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "AcademicSessionEntity{" +
                "sessionId=" + sessionId +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    // TODO set FKs on ALL tables

    public ArrayList<String> createTableAcademicSession() {
        ArrayList academicSessionTable = new ArrayList();
        String tableName = "AcademicSessionEntity";
        String columnNamesAndDataTypes = "sessionId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, title TEXT, startDate DATE, endDate DATE";
        academicSessionTable.add(tableName);
        academicSessionTable.add(columnNamesAndDataTypes);
        return academicSessionTable;
    }

}
