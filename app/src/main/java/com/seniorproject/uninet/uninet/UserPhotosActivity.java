package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.seniorproject.uninet.uninet.Adapters.PhotosAdapter;
import com.seniorproject.uninet.uninet.ConstructorClasses.Photos;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.Post;
import com.seniorproject.uninet.uninet.DatabaseClasses.PostPicture;

import java.util.ArrayList;
import java.util.List;

public class UserPhotosActivity extends AppCompatActivity {


    RecyclerView photosFeed;
    private String whoIsTheUser;
    private String userID;
    private ArrayList<Photos> photo;

    private StoredUserInformation userInformation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        userInformation = new StoredUserInformation(this);
        whoIsTheUser = userInformation.getUserId();

        userID = getIntent().getStringExtra("FriendId");

        if(userID != null && !userID.isEmpty())
        {
            whoIsTheUser = userID;
        }

        photosFeed = findViewById(R.id.user_photo_container);
        photo = new ArrayList<>();

        addPhotos();
        
        
        PhotosAdapter messageAdapter = new PhotosAdapter(this, photo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        photosFeed.setLayoutManager(linearLayoutManager);
        photosFeed.setItemAnimator(new DefaultItemAnimator());
        photosFeed.setAdapter(messageAdapter);

    }

    private void addPhotos()
    {
        List<Post> posts = DatabaseMethods.GetPosts(whoIsTheUser);

        for (int i = 0; i < posts.size(); i++)
        {
            String postID = posts.get(i).postId;
            List<PostPicture> postsWithPicture = DatabaseMethods.GetPostPictures(postID);

            if (!postsWithPicture.isEmpty())
            {
                for (int k = 0; k < postsWithPicture.size(); k++)
                {
                    byte[] smallProfilePicture = postsWithPicture.get(k).picture;
                    photo.add(new Photos(smallProfilePicture));
                }
            }
        }
    }
}
