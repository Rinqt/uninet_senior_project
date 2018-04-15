package com.seniorproject.uninet.uninet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.LunchSchedule;
import com.seniorproject.uninet.uninet.DatabaseClasses.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaany on 28.02.2018.
 */
class UniPost
{

    private String uniPostId, userName, timeStamp, description, userID, location;
    private byte[] profilePicture, postImage;

    public UniPost(String uniPostId, String userName, byte[] profilePicture, byte[] postImage, String timeStamp, String description, String userID, String location) {
        this.uniPostId = uniPostId;
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.postImage = postImage;
        this.timeStamp = timeStamp;
        this.description = description;
        this.userID = userID;
        this.location = location;
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
}


public class ListViewAdapter extends BaseAdapter {

    int posts;
    Context context;
    ArrayList<UniPost> list; // Currently empty.

    ListViewAdapter(Context c, int posts, String whoIsTheUser) {
        /*
        * use post = 0 to get user posts
        * use post = 1 to get feed screen posts
        */
        context = c;
        list = new ArrayList<UniPost>();
        this.posts = posts;

        if (posts == 0)
        {
            //TODO: Find a way to send UserId here
            List<Post> userPosts = DatabaseMethods.GetPosts(whoIsTheUser);
            for (int i = userPosts.size() - 1 ; i >= 0; i--)
            {
                //TODO: Resolve the picture issue, add information that will stay hidden
                list.add(new UniPost(userPosts.get(i).postId, userPosts.get(i).name, userPosts.get(i).smallProfilePicture, userPosts.get(i).smallProfilePicture, userPosts.get(i).postDate, userPosts.get(i).postText, whoIsTheUser, userPosts.get(i).location));
            }

        }
        else if (posts == 1)
        {
            //TODO: Find a way to send UserId here
            List<Post> newsFeed = DatabaseMethods.GetNewsFeed(LoggedInUser.UserId);
            for (int i = newsFeed.size() - 1 ; i >= 0; i--)
            {
                //TODO: Resolve the picture issue, add information that will stay hidden
                list.add(new UniPost(newsFeed.get(i).postId, newsFeed.get(i).name, newsFeed.get(i).smallProfilePicture, newsFeed.get(i).smallProfilePicture, newsFeed.get(i).postDate, newsFeed.get(i).postText, newsFeed.get(i).userId, newsFeed.get(i).location));
            }
        }
        else if (posts == 2)
        {
            //TODO: Find a way to send UserId here
            List<LunchSchedule> diningList = DatabaseMethods.GetLunchSchedule();
            for (int i = diningList.size() - 1 ; i >= 0; i--)
            {
                //TODO: Add Dining List

            }
        }
    }

    @Override
    public int getCount() { // Need to return number of elements
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        // 1. Get the root view
        // 2. Use the root view to find other views
        // 3. Set the values

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View uniPost = layoutInflater.inflate(R.layout.uni_post_template, viewGroup, false);

        TextView userName = uniPost.findViewById(R.id.user_name);
        TextView date = uniPost.findViewById(R.id.time_stamp);
        TextView description = uniPost.findViewById(R.id.uni_post_description);
        ImageView userPhoto = uniPost.findViewById(R.id.profile_picture);
        ImageView postPicture = uniPost.findViewById(R.id.uni_post_image);

        if (posts == 0)
        {
            userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //context.startActivity(new Intent(context, HomeActivity.class));
                }
            });

            userPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //context.startActivity(new Intent(context, HomeActivity.class));
                }
            });
        }
        else if (posts == 1)
        {
            userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent otherUserProfileIntent = new Intent(context, OtherUserProfileActivity.class);
                    otherUserProfileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(otherUserProfileIntent);
                }
            });

            userPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent otherUserProfileIntent = new Intent(context, OtherUserProfileActivity.class);
                    otherUserProfileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(otherUserProfileIntent);
                }
            });
        }


        UniPost temp = list.get(i);

        String dateWithLocation = temp.getTimeStamp() + " || " + temp.getLocation();

        userName.setText(temp.getUserName());
        date.setText(dateWithLocation);
        description.setText(temp.getDescription());
        userPhoto.setImageResource(R.mipmap.awesome_kaan);
        postPicture.setImageResource(R.mipmap.ic_launcher_round);

        return uniPost;

    }
}
