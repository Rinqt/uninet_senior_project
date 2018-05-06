package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.ConstructorClasses.UniPosts;
import com.seniorproject.uninet.uninet.OtherUserProfileActivity;
import com.seniorproject.uninet.uninet.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostListAdapter extends ArrayAdapter<UniPosts> {


    private Context mContext;
    private int mResource;
    private int posts;

    static class ViewHolder {
        TextView name;
        TextView timeStamp;
        TextView description;
        CircleImageView userPicture;
        ImageView postPicture;
    }

    public  PostListAdapter(@NonNull Context context, int posts, int resource, @NonNull List<UniPosts> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.posts = posts;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder postViewHolder = null;
        final String userID = getItem(position).getUserID();
        String userName = getItem(position).getUserName();
        String postTimeStamp = getItem(position).getTimeStamp();
        String postDescription = getItem(position).getDescription();
        String postLocation = getItem(position).getLocation();
        byte[] userProfilePicture = getItem(position).getProfilePicture();
        byte[] postInsidePicture = getItem(position).getPostImage();


        LayoutInflater postInflater = LayoutInflater.from(mContext);
        convertView = postInflater.inflate(mResource, parent, false);

        postViewHolder = new ViewHolder();

        postViewHolder.name = convertView.findViewById(R.id.user_name);
        postViewHolder.description = convertView.findViewById(R.id.uni_post_description);
        postViewHolder.timeStamp = convertView.findViewById(R.id.time_stamp);
        postViewHolder.userPicture = convertView.findViewById(R.id.profile_picture);
        postViewHolder.postPicture = convertView.findViewById(R.id.uni_post_image);

        convertView.setTag(postViewHolder);



        String postWithLocation = postTimeStamp + " || " + postLocation;
        postViewHolder.name.setText(userName);
        postViewHolder.timeStamp.setText(postWithLocation);
        postViewHolder.description.setText(postDescription);

        if (userProfilePicture != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(userProfilePicture, 0, userProfilePicture.length);
            postViewHolder.userPicture.setImageBitmap(bitmap);
        }

        if (postInsidePicture != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(postInsidePicture, 0, postInsidePicture.length);
            postViewHolder.postPicture.setImageBitmap(bitmap);
        }


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
                    otherUserProfileIntent.putExtra("UserID", userID);
                    otherUserProfileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(otherUserProfileIntent);
                }
            });

            postViewHolder.userPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent otherUserProfileIntent = new Intent(mContext, OtherUserProfileActivity.class);
                    otherUserProfileIntent.putExtra("UserID", userID);
                    otherUserProfileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(otherUserProfileIntent);
                }
            });
        }


        return convertView;
    }
}
