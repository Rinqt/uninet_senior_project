package com.seniorproject.uninet.uninet.ConstructorClasses;

public class UniPosts  {

    private String uniPostId, userName, timeStamp, description, userID, location;
    private byte[] profilePicture, postImage;

    public UniPosts(String userID, String uniPostId, String userName, String timeStamp, String description, String location, byte[] profilePicture, byte[] postImage) {
        this.userID = userID;
        this.uniPostId = uniPostId;
        this.userName = userName;
        this.timeStamp = timeStamp;
        this.description = description;
        this.location = location;
        this.profilePicture = profilePicture;
        this.postImage = postImage;
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
}
