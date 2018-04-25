package com.seniorproject.uninet.uninet;

public class UserConversations {

    public static final int RECEIVER = 0;
    public static final int SENDER = 1;

    private String userId;
    private String messageId;
    private String name;
    private String userMessage;
    private String messageDate;
    private int type;

    UserConversations(String userId, String messageId, String name, String userMessage, String messageDate, int type) {
        this.userId = userId;
        this.messageId = messageId;
        this.name = name;
        this.userMessage = userMessage;
        this.messageDate = messageDate;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
