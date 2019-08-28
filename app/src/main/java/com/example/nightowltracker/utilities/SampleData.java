package com.example.nightowltracker.utilities;

import com.example.nightowltracker.database.AcademicSessionEntity;

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

    public static ArrayList<AcademicSessionEntity> getAcademicSessions() {
        ArrayList<AcademicSessionEntity> as = new ArrayList<>();
        as.add(new AcademicSessionEntity(1, "Term 1", Date.valueOf("2019-09-04"), Date.valueOf("2019-12-20")));
        as.add(new AcademicSessionEntity(2, "Term 2", Date.valueOf("2020-01-04"), Date.valueOf("2020-03-20")));
        as.add(new AcademicSessionEntity(3, "Term 3", Date.valueOf("2020-03-30"), Date.valueOf("2020-06-12")));
        as.add(new AcademicSessionEntity(4, "Term 4", Date.valueOf("2020-07-01"), Date.valueOf("2020-08-30")));
        as.add(new AcademicSessionEntity(5, "Term 5", Date.valueOf("2020-09-08"), Date.valueOf("2020-12-21")));
        return as;
    }



}
