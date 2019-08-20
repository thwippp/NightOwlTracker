package com.example.nightowltracker.Model;

public class Enrollment {

    private int enrollmentId;
    private int userId;
    private int classId;

    public Enrollment() {
    }

    public Enrollment(int enrollmentId, int userId, int classId) {
        this.enrollmentId = enrollmentId;
        this.userId = userId;
        this.classId = classId;
    }

    public Enrollment(int userId, int classId) {
        this.userId = userId;
        this.classId = classId;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", userId=" + userId +
                ", classId=" + classId +
                '}';
    }
}
