package com.example.nightowltracker.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "User")

public class User {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String username;
    private String givenName;
    private String familyName;
    private String role;
    private String email;
    private String phone;
    private String sms;

    @Ignore
    public User() {
    }

    public User(int userId, String username, String givenName, String familyName, String role, String email, String phone, String sms) {
        this.userId = userId;
        this.username = username;
        this.givenName = givenName;
        this.familyName = familyName;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.sms = sms;
    }

    @Ignore
    public User(String username, String givenName, String familyName, String role, String email, String phone, String sms) {
        this.username = username;
        this.givenName = givenName;
        this.familyName = familyName;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.sms = sms;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", sms='" + sms + '\'' +
                '}';
    }

    public ArrayList<String> createTableUser() {
        ArrayList userTable = new ArrayList();
        String tableName = "User";
        String columnNamesAndDataTypes = "userId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, username TEXT, givenName TEXT, familyName TEXT, role TEXT, email TEXT, phone TEXT, sms TEXT";
        userTable.add(tableName);
        userTable.add(columnNamesAndDataTypes);
        return userTable;
    }

}
