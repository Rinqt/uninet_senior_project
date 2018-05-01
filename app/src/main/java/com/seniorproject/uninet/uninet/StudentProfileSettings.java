package com.seniorproject.uninet.uninet;

public class StudentProfileSettings {

    public String studentEmail;
    public String studentWebPage;
    public String studentPhoneNumber;
    public String studentRelationship;
    public byte[] studentSmallProfilePicture;
    public byte[] studentBigProfilePicture;

    public StudentProfileSettings(String studentEmail,
                                  String studentWebPage,
                                  String studentPhoneNumber,
                                  String studentRelationship,
                                  byte[] studentSmallProfilePicture,
                                  byte[] studentBigProfilePicture)
    {
        this.studentEmail = studentEmail;
        this.studentWebPage = studentWebPage;
        this.studentPhoneNumber = studentPhoneNumber;
        this.studentRelationship = studentRelationship;
        this.studentSmallProfilePicture = studentSmallProfilePicture;
        this.studentBigProfilePicture = studentBigProfilePicture;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentWebPage() {
        return studentWebPage;
    }

    public void setStudentWebPage(String studentWebPage) {
        this.studentWebPage = studentWebPage;
    }

    public String getStudentPhoneNumber() {
        return studentPhoneNumber;
    }

    public void setStudentPhoneNumber(String studentPhoneNumber) {
        this.studentPhoneNumber = studentPhoneNumber;
    }

    public String getStudentRelationship() {
        return studentRelationship;
    }

    public void setStudentRelationship(String studentRelationship) {
        this.studentRelationship = studentRelationship;
    }

    public byte[] getStudentSmallProfilePicture() {
        return studentSmallProfilePicture;
    }

    public void setStudentSmallProfilePicture(byte[] studentSmallProfilePicture) {
        this.studentSmallProfilePicture = studentSmallProfilePicture;
    }

    public byte[] getStudentBigProfilePicture() {
        return studentBigProfilePicture;
    }

    public void setStudentBigProfilePicture(byte[] studentBigProfilePicture) {
        this.studentBigProfilePicture = studentBigProfilePicture;
    }
}
