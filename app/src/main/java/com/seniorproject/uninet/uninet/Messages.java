package com.seniorproject.uninet.uninet;

public class Messages {

    private String friendUserName, lastMessage;
    private byte[] friendSmallProfilePicture;

    public Messages(String friendUserName, String lastMessage, byte[] friendSmallProfilePicture) {
        this.friendUserName = friendUserName;
        this.lastMessage = lastMessage;
        this.friendSmallProfilePicture = friendSmallProfilePicture;
    }

    public String getFriendUserName() {
        return friendUserName;
    }

    public void setFriendUserName(String friendUserName) {
        this.friendUserName = friendUserName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public byte[] getFriendSmallProfilePicture() {
        return friendSmallProfilePicture;
    }

    public void setFriendSmallProfilePicture(byte[] friendSmallProfilePicture) {
        this.friendSmallProfilePicture = friendSmallProfilePicture;
    }
}
