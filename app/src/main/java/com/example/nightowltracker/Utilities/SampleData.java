package com.example.nightowltracker.Utilities;

import com.example.nightowltracker.Model.AcademicSession;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SampleData {

    private static final String SAMPLE_TEXT_1 = "Term 1";
    private static final String SAMPLE_TEXT_2 = "Term 2";
    private static final String SAMPLE_TEXT_3 = "Term 3";

    private static Date getDate(int diff){
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return (Date) cal.getTime();
    }

    public static ArrayList<AcademicSession> getAcademicSessions(){
        ArrayList<AcademicSession> as = new ArrayList<>();
        as.add(new AcademicSession(1, "Term 1", Date.valueOf("2019-09-04"), Date.valueOf("2019-12-20")));
        as.add(new AcademicSession(2, "Term 2", Date.valueOf("2020-01-04"), Date.valueOf("2020-03-20")));
        as.add(new AcademicSession(3, "Term 3", Date.valueOf("2020-03-30"), Date.valueOf("2020-06-12")));
        return as;
    }



}
