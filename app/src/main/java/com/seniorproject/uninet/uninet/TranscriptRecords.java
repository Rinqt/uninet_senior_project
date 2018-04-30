package com.seniorproject.uninet.uninet;

public class TranscriptRecords  {



    public static final int FALL = 9;
    public static final int SPRING = 8;
    public static final int HEADER = 1;
    public static final int COURSE = 2;
    public static final int SUMMARYTITLE = 3;
    public static final int SUMMARY = 4;

    private String unit;
    private String unitTitle;
    private String successGrade;
    private String ects;
    private String point;
    private String educationYear;
    private String semester;
    private int type;


    TranscriptRecords(String unit, String unitTitle, String successGrade, String ects, String point, String educationYear, String semester, int type) {
        this.unit = unit;
        this.unitTitle = unitTitle;
        this.successGrade = successGrade;
        this.ects = ects;
        this.point = point;
        this.educationYear = educationYear;
        this.semester = semester;
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitTitle() {
        return unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getSuccessGrade() {
        return successGrade;
    }

    public void setSuccessGrade(String successGrade) {
        this.successGrade = successGrade;
    }

    public String getEcts() {
        return ects;
    }

    public void setEcts(String ects) {
        this.ects = ects;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getEducationYear() {
        return educationYear;
    }

    public void setEducationYear(String educationYear) {
        this.educationYear = educationYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
