package com.seniorproject.uninet.uninet;

public class UserPrivacySettings {

    public String postPrivacy;
    public String locationPrivacy;
    public String messageToEveryone;
    public String notification;
    public String birthdayPrivacy;

    public UserPrivacySettings(String postPrivacy, String locationPrivacy, String messageToEveryone, String notification, String birthdayPrivacy) {
        this.postPrivacy = postPrivacy;
        this.locationPrivacy = locationPrivacy;
        this.messageToEveryone = messageToEveryone;
        this.notification = notification;
        this.birthdayPrivacy = birthdayPrivacy;
    }

    public String getPostPrivacy() {
        return postPrivacy;
    }

    public void setPostPrivacy(String postPrivacy) {
        this.postPrivacy = postPrivacy;
    }

    public String getLocationPrivacy() {
        return locationPrivacy;
    }

    public void setLocationPrivacy(String locationPrivacy) {
        this.locationPrivacy = locationPrivacy;
    }

    public String getMessageToEveryone() {
        return messageToEveryone;
    }

    public void setMessageToEveryone(String messageToEveryone) {
        this.messageToEveryone = messageToEveryone;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getBirthdayPrivacy() {
        return birthdayPrivacy;
    }

    public void setBirthdayPrivacy(String birthdayPrivacy) {
        this.birthdayPrivacy = birthdayPrivacy;
    }
}

