package com.seniorproject.uninet.uninet;

import android.content.Context;
import android.content.SharedPreferences;

public class StoredUserInformation {

    private SharedPreferences dataPrefs;
    SharedPreferences.Editor dataEditor;
    Context context;


    public StoredUserInformation(Context context) {
        dataPrefs = context.getSharedPreferences("user_information", Context.MODE_PRIVATE);
        dataEditor = dataPrefs.edit();
        this.context = context;
    }

    private void userId(String info)
    {
        dataEditor.putString("user_id", info);
        dataEditor.commit();
    }


    private void userName(String info)
    {
        dataEditor.putString("user_name", info);
        dataEditor.commit();
    }

    private void userDepartment(String info)
    {
        dataEditor.putString("user_department", info);
        dataEditor.commit();
    }

    private void mailAddress(String info)
    {
        dataEditor.putString("user_key_total_mail_address", info);
        dataEditor.commit();
    }

    private void phoneNumber(String info)
    {
        dataEditor.putString("user_key_total_phone_number", info);
        dataEditor.commit();
    }

    private void relationshipStatus(String info)
    {
        dataEditor.putString("user_key_total_relationship_status", info);
        dataEditor.commit();

    }

    private void webPage(String info)
    {
        dataEditor.putString("user_key_total_web_page", info);
        dataEditor.commit();
    }


    private void friendsNumber(String info)
    {
        dataEditor.putString("user_key_total_friends", info);
        dataEditor.commit();
    }


    private void followersNumber(String info)
    {
        dataEditor.putString("user_key_total_follower", info);
        dataEditor.commit();
    }


    private void followsNumber(String info)
    {
        dataEditor.putString("user_key_total_follows", info);
        dataEditor.commit();
    }


    private void uniPostsNumber(String info)
    {
        dataEditor.putString("user_key_total_uni_posts", info);
        dataEditor.commit();
    }

    private void educationYear(String info)
    {
        dataEditor.putString("user_key_total_education_year", info);
        dataEditor.commit();
    }

    private void photosNumber(String info)
    {
        dataEditor.putString("user_key_total_photos", info);
        dataEditor.commit();
    }

    private void uniPostPrivacy(String info)
    {
        dataEditor.putString("user_key_uni_post_privacy", info);
        dataEditor.commit();
    }

    private void locationPrivacy(String info)
    {
        dataEditor.putString("user_key_location_privacy", info);
        dataEditor.commit();
    }

    private void messagingPrivacy(String info)
    {
        dataEditor.putString("user_key_messaging_privacy", info);
        dataEditor.commit();
    }

    private void birthdayPrivacy(String info)
    {
        dataEditor.putString("user_key_birthday_privacy", info);
        dataEditor.commit();
    }

    private void notification(String info)
    {
        dataEditor.putString("user_key_notification", info);
        dataEditor.commit();
    }

    private void birthday(String info)
    {
        dataEditor.putString("user_birth_day", info);
        dataEditor.commit();
    }



    // SETTERS
    public void setUserId(String info)
    {
        userId(info);
    }

    public void setUserName(String info)
    {
        userName(info);
    }

    public void setDepartment(String info)
    {
        userDepartment(info);
    }

    public void setFriendsNumber(String info)
    {
        friendsNumber(info);
    }


    public void setFollowersNumber(String info)
    {
        followersNumber(info);
    }

    public void setFollowsNumber(String info)
    {
        followsNumber(info);
    }


    public void setUniPostsNumber(String info)
    {
        uniPostsNumber(info);
    }

    public void setEducationYear(String info)
    {
        educationYear(info);
    }

    public void setPhotosNumber(String info)
    {
        photosNumber(info);
    }

    public void setMailAddress(String info)
    {
        mailAddress(info);
    }

    public void setPhoneNumber(String info)
    {
        phoneNumber(info);
    }

    public void setRelationshipStatus(String info)
    {
        relationshipStatus(info);
    }

    public void setWebPage(String info)
    {
        webPage(info);
    }


    public void setUniPostPrivacy(String info)
    {
        uniPostPrivacy(info);
    }

    public void setLocationPrivacy(String info)
    {
        locationPrivacy(info);
    }

    public void setMessagingPrivacy(String info)
    {
        messagingPrivacy(info);
    }

    public void setBirthdayPrivacy (String info)
    {
        birthdayPrivacy(info);
    }

    public void setNotification (String info)
    {
        notification(info);
    }

    public void setBityhday (String info)
    {
        birthday(info);
    }


    // GETTERS
    public String getUserId()
    {
        return dataPrefs.getString("user_id", "");
    }

    public String getUserName()
    {
        return dataPrefs.getString("user_name", "");
    }

    public String getDepartment()
    {
        return dataPrefs.getString("user_department", "");
    }

    public String getFriendsNumber() {
        return dataPrefs.getString("user_key_total_friends", "");
    }

    public String getFollowersNumber() {
        return dataPrefs.getString("user_key_total_follower", "");
    }

    public String getFollowsNumber() {
        return dataPrefs.getString("user_key_total_follows", "");
    }

    public String getUniPostsNumber() {
        return dataPrefs.getString("user_key_total_uni_posts", "");
    }

    public String getEducationYear() {
        return dataPrefs.getString("user_key_total_education_year", "");
    }

    public String getPhotosNumber() {
        return dataPrefs.getString("user_key_total_photos", "");
    }

    public String getMailAddress() {
        return dataPrefs.getString("user_key_total_mail_address", "");
    }

    public String getPhoneNumber() {
        return dataPrefs.getString("user_key_total_phone_number", "");
    }

    public String getRelationshipStatus() {
        return dataPrefs.getString("user_key_total_relationship_status", "");
    }

    public String getWebPage() {
        return dataPrefs.getString("user_key_total_web_page", "");
    }

    public String getUniPostPrivacy() {
        return dataPrefs.getString("user_key_uni_post_privacy", "");
    }

    public String getLocationPrivacy() {
        return dataPrefs.getString("user_key_location_privacy", "");
    }

    public String getMessagingPrivacy() {
        return dataPrefs.getString("user_key_messaging_privacy", "");
    }

    public String getBirthdayPrivacy() {
        return dataPrefs.getString("user_key_birthday_privacy", "");
    }

    public String getNotification() {
        return dataPrefs.getString("user_key_notification", "");
    }

    public String getBirthday() {
        return dataPrefs.getString("user_birth_day", "");
    }




}
