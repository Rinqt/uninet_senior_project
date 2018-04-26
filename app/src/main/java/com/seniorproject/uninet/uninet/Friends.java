package com.seniorproject.uninet.uninet;

public class Friends {

    private String friendName, friendId;
    private byte[] friendPicture;

    public Friends(String friendId, String friendName,  byte[] friendPicture) {
        this.friendId = friendId;
        this.friendName = friendName;
        this.friendPicture = friendPicture;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public byte[] getFriendPicture() {
        return friendPicture;
    }

    public void setFriendPicture(byte[] friendPicture) {
        this.friendPicture = friendPicture;
    }
}
