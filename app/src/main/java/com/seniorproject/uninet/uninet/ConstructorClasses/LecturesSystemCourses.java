package com.seniorproject.uninet.uninet.ConstructorClasses;

public class LecturesSystemCourses {

    private String lectureId, lectureCode, lectureName;

    public LecturesSystemCourses(String lectureId, String lectureCode, String lectureName) {
        this.lectureId = lectureId;
        this.lectureCode =lectureCode;
        this.lectureName = lectureName;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getLectureCode() {
        String name = lectureName;
        String[] code = name.split("-");
        return code[0];
    }

    public void setLectureCode(String lectureCode) {
        this.lectureCode = lectureCode;
    }

    public String getLectureName() {
        String name = lectureName;
        String[] code = name.split("-");
        return code[1].trim();
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }
}
