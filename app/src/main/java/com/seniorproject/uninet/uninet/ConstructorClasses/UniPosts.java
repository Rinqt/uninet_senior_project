package com.seniorproject.uninet.uninet.ConstructorClasses;

public class UniPosts  {

    public static final int FRIEND_POST = 0;
    public static final int USER_POST = 1;

    private String uniPostId, userName, timeStamp, description, userID, location;
    private byte[] profilePicture, postImage;
    private int type;

    public UniPosts(String userID, String uniPostId, String userName, String timeStamp, String description, String location, byte[] profilePicture, byte[] postImage, int type) {
        this.userID = userID;
        this.uniPostId = uniPostId;
        this.userName = userName;
        this.timeStamp = timeStamp;
        this.description = description;
        this.location = location;
        this.profilePicture = profilePicture;
        this.postImage = postImage;
        this.type = type;
    }

    public String getUniPostId() {
        return uniPostId;
    }

    public void setUniPostId(String uniPostId) {
        this.uniPostId = uniPostId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public byte[] getPostImage() {
        return postImage;
    }

    public void setPostImage(byte[] postImage) {
        this.postImage = postImage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
