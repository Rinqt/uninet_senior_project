package com.seniorproject.uninet.uninet.ConstructorClasses;

public class User {

    public static final int FRIEND = 0;
    public static final int NOT_FRIEND = 1;

    private String userId;
    private String userName;
    private String title;
    private byte[] userPhoto;
    private int type;

    public User(String userId, String userName, String title, byte[] userPhoto, int type) {
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.userPhoto = userPhoto;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(byte[] userPhoto) {
        this.userPhoto = userPhoto;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
