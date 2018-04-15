package com.seniorproject.uninet.uninet.DatabaseClasses;

import android.database.DatabaseUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMethods {
    public static String LoginCheck(String username, String userpass){
        String result = "";
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/LoginCheck?a="+username+"?b="+userpass;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            result = jsonResult.getString("LoginCheckResult");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("LoginCheck", result.toString());
        return result;
    }

    public static UserListingInfo GetUserNamePic(String userId){
        UserListingInfo result = new UserListingInfo();
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetUserNamePic?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            jsonResult = jsonResult.getJSONObject("GetUserNamePicResult");
            result.userId = userId;
            result.name = jsonResult.getString("name");
            if(jsonResult.isNull("smallProfilePicture"))
                result.smallProfilePicture = null;
            else{
                JSONArray pictureBytes = jsonResult.getJSONArray("smallProfilePicture");
                result.smallProfilePicture = new byte[pictureBytes.length()];
                for(int j = 0; j < pictureBytes.length(); j++){
                    result.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("GetUserNamePic", result.toString());
        return result;

    }

    public static List<UserListingInfo> SearchUser(String name){
        List<UserListingInfo> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/SearchUser?a="+name;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("SearchUserResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject user = jsonArray.getJSONObject(i);
                UserListingInfo tempUser = new UserListingInfo();
                tempUser.userId = user.getString("userId");
                tempUser.name = user.getString("name");
                if(user.isNull("smallProfilePicture"))
                    tempUser.smallProfilePicture = null;
                else{
                    JSONArray pictureBytes = user.getJSONArray("smallProfilePicture");
                    tempUser.smallProfilePicture = new byte[pictureBytes.length()];
                    for(int j = 0; j < pictureBytes.length(); j++){
                        tempUser.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                    }
                }
                result.add(tempUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ProfileInfoStudent GetProfileInfoStudent(String userId){
        ProfileInfoStudent result = new ProfileInfoStudent();
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetProfileInfoStudent?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            jsonResult = jsonResult.getJSONObject("GetProfileInfoStudentResult");
            result.name = jsonResult.getString("name");
            result.email = jsonResult.getString("email");
            result.webPage = jsonResult.getString("webPage");
            result.phoneNumber = jsonResult.getString("phoneNumber");
            result.birthday = jsonResult.getString("birthday");
            result.birthday = result.birthday.split("\\s")[0];
            result.department = jsonResult.getString("department");
            result.relationship = jsonResult.getString("relationship");
            result.academicYear = jsonResult.getString("academicYear");
            if(jsonResult.isNull("smallProfilePicture"))
                result.smallProfilePicture = null;
            else{
                JSONArray pictureBytes = jsonResult.getJSONArray("smallProfilePicture");
                result.smallProfilePicture = new byte[pictureBytes.length()];
                for(int j = 0; j < pictureBytes.length(); j++){
                    result.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                }
            }
            if(jsonResult.isNull("bigProfilePicture"))
                result.bigProfilePicture = null;
            else{
                JSONArray pictureBytes = jsonResult.getJSONArray("bigProfilePicture");
                result.bigProfilePicture = new byte[pictureBytes.length()];
                for(int j = 0; j < pictureBytes.length(); j++){
                    result.bigProfilePicture[j] = (byte)pictureBytes.getInt(j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ProfileInfoTeacher GetProfileInfoTeacher(String userId){
        ProfileInfoTeacher result = new ProfileInfoTeacher();
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetProfileInfoTeacher?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            jsonResult = jsonResult.getJSONObject("GetProfileInfoTeacherResult");
            result.name = jsonResult.getString("name");
            result.email = jsonResult.getString("email");
            result.webPage = jsonResult.getString("webPage");
            result.phoneNumber = jsonResult.getString("phoneNumber");
            result.birthday = jsonResult.getString("birthday");
            result.birthday = result.birthday.split("\\s")[0];
            result.department = jsonResult.getString("department");
            result.relationship = jsonResult.getString("relationship");
            result.title = jsonResult.getString("title");
            if(jsonResult.isNull("smallProfilePicture"))
                result.smallProfilePicture = null;
            else{
                JSONArray pictureBytes = jsonResult.getJSONArray("smallProfilePicture");
                result.smallProfilePicture = new byte[pictureBytes.length()];
                for(int j = 0; j < pictureBytes.length(); j++){
                    result.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                }
            }
            if(jsonResult.isNull("bigProfilePicture"))
                result.bigProfilePicture = null;
            else{
                JSONArray pictureBytes = jsonResult.getJSONArray("bigProfilePicture");
                result.bigProfilePicture = new byte[pictureBytes.length()];
                for(int j = 0; j < pictureBytes.length(); j++){
                    result.bigProfilePicture[j] = (byte)pictureBytes.getInt(j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void UpdateProfileInfo(String userId, String email, String webPage, String phoneNumber, String relationship, String smallProfilePicture, String bigProfilePicture){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/UpdateProfileInfo?a="+userId+"&b="+email+"&c="+webPage+"&d="+phoneNumber+"&e="+relationship+"&f="+smallProfilePicture+"&g="+bigProfilePicture;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] GetUserBigPic(String userId){
        byte[] result = null;
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetUserBigPic?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            if(jsonResult.isNull("GetUserBigPicResult"))
                result = null;
            else{
                JSONArray pictureBytes = jsonResult.getJSONArray("GetUserBigPicResult");
                result = new byte[pictureBytes.length()];
                for(int j = 0; j < pictureBytes.length(); j++){
                    result[j] = (byte)pictureBytes.getInt(j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String GetStudentId(String userId){
        String result = "";
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetStudentId?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            result = jsonResult.getString("GetStudentIdResult");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String GetTeacherId(String userId){
        String result = "";
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetTeacherId?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            result = jsonResult.getString("GetTeacherIdResult");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void InsertRelation(String userId, String otherUserId, String relationType){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/InsertRelation?a="+userId+"&b="+otherUserId+"&c="+relationType;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RemoveFriendFollowUponBlock(String userId, String otherUserId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/RemoveFriendFollowUponBlock?a="+userId+"&b="+otherUserId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RemoveFriend(String userId, String otherUserId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/RemoveFriend?a="+userId+"&b="+otherUserId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RemoveBlock(String userId, String otherUserId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/RemoveBlock?a="+userId+"&b="+otherUserId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void DeclineFriendRequest(String userId, String otherUserId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/DeclineFriendRequest?a="+userId+"&b="+otherUserId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<UserListingInfo> GetBlocks(String userId){
        List<UserListingInfo> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetBlocks?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetBlocksResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject user = jsonArray.getJSONObject(i);
                UserListingInfo tempUser = new UserListingInfo();
                tempUser.userId = user.getString("userId");
                tempUser.name = user.getString("name");
                if(user.isNull("smallProfilePicture"))
                    tempUser.smallProfilePicture = null;
                else{
                    JSONArray pictureBytes = user.getJSONArray("smallProfilePicture");
                    tempUser.smallProfilePicture = new byte[pictureBytes.length()];
                    for(int j = 0; j < pictureBytes.length(); j++){
                        tempUser.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                    }
                }
                result.add(tempUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String CheckBlocked(String userId, String otherUserId){
        String result = "";
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/CheckBlocked?a="+userId+"&b="+otherUserId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            result = jsonResult.getString("CheckBlockedResult");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<UserListingInfo> GetFriends(String userId){
        List<UserListingInfo> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetFriends?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetFriendsResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject user = jsonArray.getJSONObject(i);
                UserListingInfo tempUser = new UserListingInfo();
                tempUser.userId = user.getString("userId");
                tempUser.name = user.getString("name");
                if(user.isNull("smallProfilePicture"))
                    tempUser.smallProfilePicture = null;
                else{
                    JSONArray pictureBytes = user.getJSONArray("smallProfilePicture");
                    tempUser.smallProfilePicture = new byte[pictureBytes.length()];
                    for(int j = 0; j < pictureBytes.length(); j++){
                        tempUser.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                    }
                }
                result.add(tempUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String CheckFriendship(String userId, String otherUserId){
        String result = "";
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/CheckFriendship?a="+userId+"&b="+otherUserId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            result = jsonResult.getString("CheckFriendshipResult");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<UserListingInfo> GetFriendRequests(String userId){
        List<UserListingInfo> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetFriendRequests?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetFriendRequestsResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject user = jsonArray.getJSONObject(i);
                UserListingInfo tempUser = new UserListingInfo();
                tempUser.userId = user.getString("userId");
                tempUser.name = user.getString("name");
                if(user.isNull("smallProfilePicture"))
                    tempUser.smallProfilePicture = null;
                else{
                    JSONArray pictureBytes = user.getJSONArray("smallProfilePicture");
                    tempUser.smallProfilePicture = new byte[pictureBytes.length()];
                    for(int j = 0; j < pictureBytes.length(); j++){
                        tempUser.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                    }
                }
                result.add(tempUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String CheckFriendRequest(String userId, String otherUserId){
        String result = "";
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/CheckFriendRequest?a="+userId+"&b="+otherUserId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            result = jsonResult.getString("CheckFriendRequestResult");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void InsertStudentFollow(String studentId, String teacherId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/InsertStudentFollow?a="+studentId+"&b="+teacherId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void InsertTeacherFollow(String teacherId, String studentId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/InsertStudentFollow?a="+teacherId+"&b="+studentId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RemoveStudentFollow(String studentId, String teacherId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/RemoveStudentFollow?a="+studentId+"&b="+teacherId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RemoveTeacherFollow(String teacherId, String studentId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/RemoveTeacherFollow?a="+teacherId+"&b="+studentId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<UserListingInfo> GetStudentFollowers(String studentId){
        List<UserListingInfo> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetStudentFollowers?a="+studentId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetStudentFollowersResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject user = jsonArray.getJSONObject(i);
                UserListingInfo tempUser = new UserListingInfo();
                tempUser.userId = user.getString("userId");
                tempUser.name = user.getString("name");
                if(user.isNull("smallProfilePicture"))
                    tempUser.smallProfilePicture = null;
                else{
                    JSONArray pictureBytes = user.getJSONArray("smallProfilePicture");
                    tempUser.smallProfilePicture = new byte[pictureBytes.length()];
                    for(int j = 0; j < pictureBytes.length(); j++){
                        tempUser.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                    }
                }
                result.add(tempUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<UserListingInfo> GetStudentFollowing(String studentId){
        List<UserListingInfo> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetStudentFollowing?a="+studentId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetStudentFollowingResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject user = jsonArray.getJSONObject(i);
                UserListingInfo tempUser = new UserListingInfo();
                tempUser.userId = user.getString("userId");
                tempUser.name = user.getString("name");
                if(user.isNull("smallProfilePicture"))
                    tempUser.smallProfilePicture = null;
                else{
                    JSONArray pictureBytes = user.getJSONArray("smallProfilePicture");
                    tempUser.smallProfilePicture = new byte[pictureBytes.length()];
                    for(int j = 0; j < pictureBytes.length(); j++){
                        tempUser.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                    }
                }
                result.add(tempUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<UserListingInfo> GetTeacherFollowers(String teacherId){
        List<UserListingInfo> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetTeacherFollowers?a="+teacherId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetTeacherFollowersResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject user = jsonArray.getJSONObject(i);
                UserListingInfo tempUser = new UserListingInfo();
                tempUser.userId = user.getString("userId");
                tempUser.name = user.getString("name");
                if(user.isNull("smallProfilePicture"))
                    tempUser.smallProfilePicture = null;
                else{
                    JSONArray pictureBytes = user.getJSONArray("smallProfilePicture");
                    tempUser.smallProfilePicture = new byte[pictureBytes.length()];
                    for(int j = 0; j < pictureBytes.length(); j++){
                        tempUser.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                    }
                }
                result.add(tempUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<UserListingInfo> GetTeacherFollowing(String teacherId){
        List<UserListingInfo> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetTeacherFollowing?a="+teacherId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetTeacherFollowingResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject user = jsonArray.getJSONObject(i);
                UserListingInfo tempUser = new UserListingInfo();
                tempUser.userId = user.getString("userId");
                tempUser.name = user.getString("name");
                if(user.isNull("smallProfilePicture"))
                    tempUser.smallProfilePicture = null;
                else{
                    JSONArray pictureBytes = user.getJSONArray("smallProfilePicture");
                    tempUser.smallProfilePicture = new byte[pictureBytes.length()];
                    for(int j = 0; j < pictureBytes.length(); j++){
                        tempUser.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                    }
                }
                result.add(tempUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<TranscriptCourse> GetTranscript(String userId){
        List<TranscriptCourse> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetTranscript?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetTranscriptResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject course = jsonArray.getJSONObject(i);
                TranscriptCourse tempUser = new TranscriptCourse();
                tempUser.courseUnit = course.getString("courseUnit");
                tempUser.courseUnitTitle = course.getString("courseUnitTitle");
                tempUser.successGrade = course.getString("successGrade");
                tempUser.ects = course.getString("ects");
                tempUser.point = course.getString("point");
                tempUser.educationYear = course.getString("educationYear");
                tempUser.semester = course.getString("semester");
                result.add(tempUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void SendMessage(String otherUserId, String userId, String userMessage){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/SendMessage?a="+otherUserId+"&b="+userId+"&c="+userMessage;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SendMessageExistingConversation(String conversationId, String userId, String userMessage){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/SendMessage?a="+conversationId+"&b="+userId+"&c="+userMessage;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String CheckExistingConversation(String userId, String otherUserId){
        String result = "";
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/CheckExistingConversation?a="+userId+"&b="+otherUserId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            result = jsonResult.getString("CheckExistingConversationResult");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void EraseMessage(String userId, String messageId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/EraseMessage?a="+userId+"&b="+messageId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void EraseConversation(String userId, String conversationId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/EraseConversation?a="+userId+"&b="+conversationId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Message> GetMessages(String userId, String conversationId){
        List<Message> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetMessages?a="+userId+"&b="+conversationId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetMessagesResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject course = jsonArray.getJSONObject(i);
                Message tempMessage = new Message();
                tempMessage.userId = course.getString("userId");
                tempMessage.messageId = course.getString("messageId");
                tempMessage.name = course.getString("name");
                tempMessage.userMessage = course.getString("userMessage");
                tempMessage.messageDate = course.getString("messageDate");
                result.add(tempMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Conversation> GetConversations(String userId){
        List<Conversation> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetConversations?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetConversationsResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject course = jsonArray.getJSONObject(i);
                Conversation tempConv = new Conversation();
                tempConv.conversationId = course.getString("conversationId");
                tempConv.name = course.getString("name");

                if(course.isNull("smallProfilePicture"))
                    tempConv.smallProfilePicture = null;
                else{
                    JSONArray pictureBytes = course.getJSONArray("smallProfilePicture");
                    tempConv.smallProfilePicture = new byte[pictureBytes.length()];
                    for(int j = 0; j < pictureBytes.length(); j++){
                        tempConv.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                    }
                }

                tempConv.userMessage = course.getString("userMessage");
                result.add(tempConv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<TimeTableCourse> GetUserTimeTable(String userId){
        List<TimeTableCourse> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetUserTimeTable?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetUserTimeTableResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject course = jsonArray.getJSONObject(i);
                TimeTableCourse tempCourse = new TimeTableCourse();
                tempCourse.startTime = course.getString("startTime");
                tempCourse.endTime = course.getString("endTime");
                tempCourse.weekDay = course.getString("weekDay");
                tempCourse.theoryClass = course.getString("theoryClass");
                tempCourse.courseCode = course.getString("courseCode");
                tempCourse.branchNumber = course.getString("branchNumber");
                tempCourse.courseName = course.getString("courseName");
                tempCourse.classroom = course.getString("classroom");
                tempCourse.quota = course.getString("quota");
                tempCourse.name = course.getString("name");
                result.add(tempCourse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static PrivacySettings GetPrivacySettings(String userId){
        PrivacySettings result = new PrivacySettings();
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetPrivacySettings?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            jsonResult = jsonResult.getJSONObject("GetPrivacySettingsResult");
            result.showPostEveryone = jsonResult.getString("showPostEveryone");
            result.showLocationEveryone = jsonResult.getString("showLocationEveryone");
            result.receiveMessageFromEveryone = jsonResult.getString("receiveMessageFromEveryone");
            result.notifications = jsonResult.getString("notifications");
            result.showBirthdayEveryone = jsonResult.getString("showBirthdayEveryone");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void UpdatePrivacySettings(String userId, String showPostEveryone, String showLocationEveryone, String receiveMessageFromEveryone, String notifications, String showBirthdayEveryone){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/UpdateProfileInfo?a="+userId+"&b="+showPostEveryone+"&c="+showLocationEveryone+"&d="+receiveMessageFromEveryone+"&e="+notifications+"&f="+showBirthdayEveryone;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SendPost(String userId, String postText, String location, String picturesList) {
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/SendPost?a="+userId+"&b="+ postText +"&c="+ location +"&d="+picturesList;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void RemovePost(String postId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/RemovePost?a="+postId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Post> GetPosts(String userId){
        List<Post> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetPosts?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetPostsResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject post = jsonArray.getJSONObject(i);
                Post tempPost = new Post();
                tempPost.userId = post.getString("userId");
                tempPost.postId = post.getString("postId");
                tempPost.name = post.getString("name");
                if(post.isNull("smallProfilePicture"))
                    tempPost.smallProfilePicture = null;
                else{
                    JSONArray pictureBytes = post.getJSONArray("smallProfilePicture");
                    tempPost.smallProfilePicture = new byte[pictureBytes.length()];
                    for(int j = 0; j < pictureBytes.length(); j++){
                        tempPost.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                    }
                }
                tempPost.postText = URLDecoder.decode(post.getString("postText"), "UTF-8");
                tempPost.location = post.getString("location");
                tempPost.postDate = post.getString("postDate");

                result.add(tempPost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void InsertPostPictures(String postId, String picture){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/RemovePost?a="+postId+"&b="+picture;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<PostPicture> GetPostPictures(String postId){
        List<PostPicture> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetPostPictures?a="+postId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetPostPicturesResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject picture = jsonArray.getJSONObject(i);
                PostPicture tempPostPicture = new PostPicture();
                tempPostPicture.postId = picture.getString("postId");
                JSONArray pictureBytes = picture.getJSONArray("picture");
                tempPostPicture.picture = new byte[pictureBytes.length()];
                for(int j = 0; j < pictureBytes.length(); j++){
                    tempPostPicture.picture[j] = (byte)pictureBytes.getInt(j);
                }
                result.add(tempPostPicture);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Post> GetNewsFeed(String userId){
        List<Post> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetNewsFeed?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetNewsFeedResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject post = jsonArray.getJSONObject(i);
                Post tempPost = new Post();
                tempPost.userId = post.getString("userId");
                tempPost.postId = post.getString("postId");
                tempPost.name = post.getString("name");
                if(post.isNull("smallProfilePicture"))
                    tempPost.smallProfilePicture = null;
                else{
                    JSONArray pictureBytes = post.getJSONArray("smallProfilePicture");
                    tempPost.smallProfilePicture = new byte[pictureBytes.length()];
                    for(int j = 0; j < pictureBytes.length(); j++){
                        tempPost.smallProfilePicture[j] = (byte)pictureBytes.getInt(j);
                    }
                }
                tempPost.postText = post.getString("postText");
                tempPost.location = post.getString("location");
                tempPost.postDate = post.getString("postDate");

                result.add(tempPost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void AddLectureToUser(String userId, String lecturesCourseId){
        String url = "http://uninetapplication.cloudapp.net/Service1.svc/AddLectureToUser?a="+userId+"&b="+lecturesCourseId;
        try {
            new DatabaseClass().execute(url, "POST").get();
            //Successful Message?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<LecturesCourse> SearchLecture(String lectureName){
        List<LecturesCourse> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/SearchLecture?a="+lectureName;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("SearchLectureResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject course = jsonArray.getJSONObject(i);
                LecturesCourse tempCourse = new LecturesCourse();
                tempCourse.lecturesCourseId = course.getString("lecturesCourseId");
                tempCourse.lectureName = course.getString("lectureName");
                result.add(tempCourse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<LecturesCourse> GetUserLectures(String userId){
        List<LecturesCourse> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetUserLectures?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetUserLecturesResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject course = jsonArray.getJSONObject(i);
                LecturesCourse tempCourse = new LecturesCourse();
                tempCourse.lecturesCourseId = course.getString("lecturesCourseId");
                tempCourse.lectureName = course.getString("lectureName");
                result.add(tempCourse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<LectureTeacher> GetLectureTeachers(String lecturesCourseId){
        List<LectureTeacher> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetLectureTeachers?a="+lecturesCourseId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetLectureTeachersResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject teacher = jsonArray.getJSONObject(i);
                LectureTeacher tempTeacher = new LectureTeacher();
                tempTeacher.userId = teacher.getString("userId");
                tempTeacher.name = teacher.getString("name");
                result.add(tempTeacher);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<CourseContent> GetCourseContents(String lecturesCourseId){
        List<CourseContent> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetCourseContents?a="+lecturesCourseId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetCourseContentsResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject content = jsonArray.getJSONObject(i);
                CourseContent tempContent = new CourseContent();
                tempContent.content = content.getString("content");
                tempContent.contentDate = content.getString("contentDate");
                result.add(tempContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Assignment> GetCourseAssignments(String lecturesCourseId){
        List<Assignment> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetCourseAssignments?a="+lecturesCourseId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetCourseAssignmentsResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject assignment = jsonArray.getJSONObject(i);
                Assignment tempAssignment = new Assignment();
                tempAssignment.title = assignment.getString("title");
                tempAssignment.dueDate = assignment.getString("dueDate");
                result.add(tempAssignment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Assignment> GetUserAssignments(String userId){
        List<Assignment> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetUserAssignments?a="+userId;
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetUserAssignmentsResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject assignment = jsonArray.getJSONObject(i);
                Assignment tempAssignment = new Assignment();
                tempAssignment.title = assignment.getString("title");
                tempAssignment.dueDate = assignment.getString("dueDate");
                result.add(tempAssignment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<LunchSchedule> GetLunchSchedule(){
        List<LunchSchedule> result = new ArrayList<>();

        String url = "http://uninetapplication.cloudapp.net/Service1.svc/GetLunchSchedule";
        try {
            JSONObject jsonResult = new JSONObject(new DatabaseClass().execute(url, "GET").get());
            JSONArray jsonArray = jsonResult.getJSONArray("GetLunchScheduleResult");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject lunch = jsonArray.getJSONObject(i);
                LunchSchedule tempLunch = new LunchSchedule();
                tempLunch.food = lunch.getString("food");
                tempLunch.lunchDate = lunch.getString("lunchDate");
                result.add(tempLunch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
