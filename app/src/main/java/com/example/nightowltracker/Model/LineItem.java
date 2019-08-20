package com.example.nightowltracker.Model;

import java.util.Date;

public class LineItem {

    private int lineItemId;
    private String title;
    private String description;
    private String category;
    private Date assignDate;
    private Date dueDate;
    private int classId;

    public LineItem() {
    }

    public LineItem(int lineItemId, String title, String description, String category, Date assignDate, Date dueDate, int classId) {
        this.lineItemId = lineItemId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.assignDate = assignDate;
        this.dueDate = dueDate;
        this.classId = classId;
    }

    public LineItem(String title, String description, String category, Date assignDate, Date dueDate, int classId) {
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
        return "LineItem{" +
                "lineItemId=" + lineItemId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", assignDate=" + assignDate +
                ", dueDate=" + dueDate +
                ", classId=" + classId +
                '}';
    }
}
