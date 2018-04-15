package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.OtherUserProfileActivity;
import com.seniorproject.uninet.uninet.R;
import com.seniorproject.uninet.uninet.UniPosts;

import java.util.List;

public class PostListAdapter extends ArrayAdapter<UniPosts> {


    private Context mContext;
    private int mResource;
    private int posts;
    private int lastPosition = -1;

    static class ViewHolder {
        TextView name;
        TextView timeStamp;
        TextView description;
        ImageView userPicture;
        ImageView postPicture;
    }

    public PostListAdapter(@NonNull Context context, int posts, int resource, @NonNull List<UniPosts> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.posts = posts;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder postViewHolder;
        final String userID = getItem(position).getUserID();
        String userName = getItem(position).getUserName();
        String postTimeStamp = getItem(position).getTimeStamp();
        String postDescription = getItem(position).getDescription();
        String postLocation = getItem(position).getLocation();
        byte[] userProfilePicture = getItem(position).getProfilePicture();
        byte[] postInsidePicture = getItem(position).getPostImage();

        if (convertView == null) {

            LayoutInflater postInflater = LayoutInflater.from(mContext);
            convertView = postInflater.inflate(mResource, parent, false);

            postViewHolder = new ViewHolder();

            postViewHolder.name = convertView.findViewById(R.id.user_name);
            postViewHolder.description = convertView.findViewById(R.id.uni_post_description);
            postViewHolder.timeStamp = convertView.findViewById(R.id.time_stamp);
            postViewHolder.userPicture = convertView.findViewById(R.id.profile_picture);
            postViewHolder.postPicture = convertView.findViewById(R.id.uni_post_image);

            convertView.setTag(postViewHolder);
        }
        else
        {
            postViewHolder = (ViewHolder) convertView.getTag();
        }

        String postWithLocation = postTimeStamp + " || " + postLocation;
        postViewHolder.name.setText(userName);
        postViewHolder.timeStamp.setText(postWithLocation);
        postViewHolder.description.setText(postDescription);
        postViewHolder.userPicture.setImageResource(R.mipmap.awesome_kaan);
        postViewHolder.postPicture.setImageResource(R.mipmap.university_logo);
        //postViewHolder.postPicture.setImageResource(userProfilePicture[position]);
        //postViewHolder.postPicture.setImageResource(postInsidePicture[position]);




        /*
         * use post = 0 to get user posts
         * use post = 1 to get feed screen posts
         */
        if (posts == 0)
        {
            postViewHolder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //context.startActivity(new Intent(context, HomeActivity.class));
                }
            });

            postViewHolder.userPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //context.startActivity(new Intent(context, HomeActivity.class));
                }
            });
        }
        else if (posts == 1)
        {
            postViewHolder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent otherUserProfileIntent = new Intent(mContext, OtherUserProfileActivity.class);
                    otherUserProfileIntent.putExtra("UserID", "1");
                    otherUserProfileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(otherUserProfileIntent);
                }
            });

            postViewHolder.userPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent otherUserProfileIntent = new Intent(mContext, OtherUserProfileActivity.class);
                    otherUserProfileIntent.putExtra("UserID", "1");
                    otherUserProfileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(otherUserProfileIntent);
                }
            });
        }


        return convertView;























    }
}
