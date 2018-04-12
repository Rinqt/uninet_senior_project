package com.seniorproject.uninet.uninet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaany on 28.02.2018.
 */
class UniPost
{

    public String uniPostId, userName, timeStamp, description;
    private byte[] profilePicture, postImage;

    public UniPost(String uniPostId, String userName, byte[] profilePicture, byte[] postImage, String timeStamp, String description) {
        this.uniPostId = uniPostId;
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.postImage = postImage;
        this.timeStamp = timeStamp;
        this.description = description;
    }

    public String getUniPostId() {
        return uniPostId;
    }

    public void setUniPostId(String uniPostId) {
        this.uniPostId = uniPostId;
    }

    public String getUserName() {
        return "Kaan YÖŞ";
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
        return "11.04.2018";
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDescription() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore " +
                "et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit ";
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
public class ListViewAdapter extends BaseAdapter {


    Context context;
    ArrayList<UniPost> list; // Currently empty.

    ListViewAdapter(Context c, int posts) {
        context = c;
        list = new ArrayList<UniPost>();

        if (posts == 0)
        {
            //TODO: Find a way to send UserId here
            List<Post> userPosts = DatabaseMethods.GetPosts(LoggedInUser.UserId);
            for (int i = userPosts.size() - 1 ; i >= 0; i--)
            {
                //TODO: Resolve the picture issue, add information that will stay hidden
                list.add(new UniPost(userPosts.get(i).postId, userPosts.get(i).name, userPosts.get(i).smallProfilePicture, userPosts.get(i).smallProfilePicture, userPosts.get(i).postDate, userPosts.get(i).postText));
            }

        }
        else if (posts == 1)
        {
            //TODO: Find a way to send UserId here
            List<Post> newsFeed = DatabaseMethods.GetNewsFeed(LoggedInUser.UserId);
            for (int i = newsFeed.size() - 1 ; i >= 0; i--)
            {
                //TODO: Resolve the picture issue, add information that will stay hidden
                list.add(new UniPost(newsFeed.get(i).postId, newsFeed.get(i).name, newsFeed.get(i).smallProfilePicture, newsFeed.get(i).smallProfilePicture, newsFeed.get(i).postDate, newsFeed.get(i).postText));
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


        UniPost temp = list.get(i);

        userName.setText(temp.userName);
        date.setText(temp.timeStamp);
        description.setText(temp.description);
        userPhoto.setImageResource(R.mipmap.awesome_kaan);
        postPicture.setImageResource(R.mipmap.ic_launcher_round);

        return uniPost;

    }
}
