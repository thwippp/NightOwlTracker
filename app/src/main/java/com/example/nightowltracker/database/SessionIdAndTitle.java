package com.example.nightowltracker.database;

import java.util.ArrayList;

public class SessionIdAndTitle {
    public static ArrayList<Integer> sessionIdArrayList = new ArrayList<>();
    public static ArrayList<String> titleArrayList = new ArrayList<>();

    public int sessionId;
    public String title;

    public SessionIdAndTitle(int sessionId, String title) {
        this.sessionId = sessionId;
        this.title = title;
    }

    public static void clearSessionIdArrayList() {
        sessionIdArrayList.clear();
    }

    public static void clearTitleArrayList() {
        titleArrayList.clear();
    }

    public static void addSessionId(int sessionId) {
        sessionIdArrayList.add(sessionId);
        System.out.println("Added " + sessionId + " to sessionIdArrayList.");
    }

    public static void addTitle(String title) {
        titleArrayList.add(title);
        System.out.println("Added " + title + " to titleArrayList.");
    }

    public static ArrayList<Integer> getSessionIdArrayList() {
        return sessionIdArrayList;
    }

    public static void setSessionIdArrayList(ArrayList<Integer> sessionIdArrayList) {
        SessionIdAndTitle.sessionIdArrayList = sessionIdArrayList;
    }

    public static ArrayList<String> getTitleArrayList() {
        return titleArrayList;
    }

    public static void setTitleArrayList(ArrayList<String> titleArrayList) {
        SessionIdAndTitle.titleArrayList = titleArrayList;
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
}