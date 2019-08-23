package com.example.nightowltracker.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "LineItemEntity")

public class LineItemEntity {
    @PrimaryKey(autoGenerate = true)
    private int lineItemId;
    private String title;
    private String description;
    private String category;
    private Date assignDate;
    private Date dueDate;
    private int classId;

    @Ignore
    public LineItemEntity() {
    }

    public LineItemEntity(int lineItemId, String title, String description, String category, Date assignDate, Date dueDate, int classId) {
        this.lineItemId = lineItemId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.assignDate = assignDate;
        this.dueDate = dueDate;
        this.classId = classId;
    }

    @Ignore
    public LineItemEntity(String title, String description, String category, Date assignDate, Date dueDate, int classId) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.assignDate = assignDate;
        this.dueDate = dueDate;
        this.classId = classId;
    }

    public int getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(int lineItemId) {
        this.lineItemId = lineItemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "LineItemEntity{" +
                "lineItemId=" + lineItemId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", assignDate=" + assignDate +
                ", dueDate=" + dueDate +
                ", classId=" + classId +
                '}';
    }

    public ArrayList<String> createTableLineItem() {
        ArrayList lineItemTable = new ArrayList();
        String tableName = "LineItemEntity";
        String columnNamesAndDataTypes = "lineItemId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, category TEXT, assignDate DATE, dueDate DATE, classId INTEGER";
        lineItemTable.add(tableName);
        lineItemTable.add(columnNamesAndDataTypes);
        return lineItemTable;
    }

}
