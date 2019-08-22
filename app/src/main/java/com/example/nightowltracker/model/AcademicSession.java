package com.example.nightowltracker.model;


import java.sql.Date;
import java.util.ArrayList;

public class AcademicSession {
    private int sessionId;
    private String title;
    private Date startDate;
    private Date endDate;

//    private Date stringToDate(String stringDate) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
//        try {
//            return formatter.parse(stringDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public AcademicSession(){

    }

    public AcademicSession(int sessionId, String title, Date startDate, Date endDate) {
        this.sessionId = sessionId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
//        this.startDate = stringToDate(startDate);
//        this.endDate = stringToDate(endDate);
    }

    public AcademicSession(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ArrayList<String> createTableAcademicSession(){
        ArrayList<String> as = new ArrayList();
        String tableName = "AcademicSession";
        String columnNamesAndDataTypes = "sessionId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, title TEXT, startDate DATE, endDate DATE";
        as.add(tableName);
        as.add(columnNamesAndDataTypes);

        return as;
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
}
