package com.seniorproject.uninet.uninet.ConstructorClasses;

public class Messages {

    private String friendUserName, lastMessage, conversationID;
    private byte[] friendSmallProfilePicture;

    public Messages(String friendUserName, String lastMessage, String friendID, byte[] friendSmallProfilePicture) {
        this.friendUserName = friendUserName;
        this.lastMessage = lastMessage;
        this.conversationID = friendID;
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

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public byte[] getFriendSmallProfilePicture() {
        return friendSmallProfilePicture;
    }

    public void setFriendSmallProfilePicture(byte[] friendSmallProfilePicture) {
        this.friendSmallProfilePicture = friendSmallProfilePicture;
    }
}
