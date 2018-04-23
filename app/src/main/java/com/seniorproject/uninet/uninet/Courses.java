package com.seniorproject.uninet.uninet;

public class Courses {

    public static final int COURSE_TYPE = 0;
    public static final int DAY_TYPE = 9;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;


    private String courseTime;
    private String courseCode;
    private String courseName;
    private String classroom;
    private String lecturer;
    private String date;
    private int type;

    Courses(String courseTime, String courseCode, String courseName, String classroom, String lecturer, String date, int type) {
        this.courseTime = courseTime;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.classroom = classroom;
        this.lecturer = lecturer;
        this.date = date;
        this.type = type;

    }

    public String getTime() {
        return courseTime;
    }

    public void setTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCode() {
        return courseCode;
    }

    public void setCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return courseName;
    }

    public void setName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
