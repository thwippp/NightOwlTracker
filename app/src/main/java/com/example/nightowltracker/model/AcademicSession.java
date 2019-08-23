package com.example.nightowltracker.model;


import java.sql.Date;
import java.util.ArrayList;

public class AcademicSession {
    private int sessionId;
    private String title;
    private Date startDate;
    private Date endDate;

    public AcademicSession(){

    }

    public AcademicSession(int sessionId, String title, Date startDate, Date endDate) {
        this.sessionId = sessionId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public AcademicSession(String title, Date startDate, Date endDate) {
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
        return "AcademicSession{" +
                "sessionId=" + sessionId +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    // TODO set FKs on ALL tables

    public ArrayList<String> createTableAcademicSession() {
        ArrayList academicSessionTable = new ArrayList();
        String tableName = "AcademicSession";
        String columnNamesAndDataTypes = "sessionId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, title TEXT, startDate DATE, endDate DATE";
        academicSessionTable.add(tableName);
        academicSessionTable.add(columnNamesAndDataTypes);
        return academicSessionTable;
    }

}
