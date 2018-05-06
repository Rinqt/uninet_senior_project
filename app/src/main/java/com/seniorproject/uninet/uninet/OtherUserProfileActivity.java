package com.seniorproject.uninet.uninet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.seniorproject.uninet.uninet.Adapters.UniPostAdapter;
import com.seniorproject.uninet.uninet.ConstructorClasses.LoggedInUser;
import com.seniorproject.uninet.uninet.ConstructorClasses.UniPosts;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.Post;
import com.seniorproject.uninet.uninet.DatabaseClasses.PostPicture;
import com.seniorproject.uninet.uninet.DatabaseClasses.UserListingInfo;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherUserProfileActivity extends AppCompatActivity {

    private UniPostAdapter uniPostAdapter;
    private RecyclerView userPostFeed;
    private ArrayList<UniPosts> uniPosts;
    private String whoIsTheUser;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button detailedProfileButton;

    TextView friendPhotosLabel;
    TextView friendFriendsLabel;
    TextView friendFollowsLabel;

    CircleImageView friendProfilePhoto;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        // Declaration
        whoIsTheUser = getIntent().getStringExtra("UserID");
        swipeRefreshLayout = findViewById(R.id.other_user_profile_swiper);
        userPostFeed = findViewById(R.id.other_user_uni_post_list_view);
        detailedProfileButton = findViewById(R.id.user_profile_button);

        friendProfilePhoto = findViewById(R.id.other_user_profile_picture);
        friendPhotosLabel = findViewById(R.id.user_total_photos_label);
        friendFriendsLabel = findViewById(R.id.user_total_friends_label);
        friendFollowsLabel = findViewById(R.id.user_total_follows_label);

        UserListingInfo user = DatabaseMethods.GetUserNamePic(whoIsTheUser);
        setTitle(user.name);

        if (user.smallProfilePicture != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.smallProfilePicture, 0, user.smallProfilePicture.length);
            friendProfilePhoto.setImageBitmap(bitmap);
        }

        addUserPosts();
        refreshInformation();

        uniPostAdapter = new UniPostAdapter(this,  uniPosts, 1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        userPostFeed.setLayoutManager(linearLayoutManager);
        userPostFeed.setItemAnimator(new DefaultItemAnimator());
        userPostFeed.setAdapter(uniPostAdapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                new Handler().postDelayed(new Runnable()
                {
                    @Override public void run() {

                        refreshPosts();
                        refreshInformation();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        detailedProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePage = new Intent(getApplication(), ProfileInfoActivity.class);
                profilePage.putExtra("UserID", whoIsTheUser);
                profilePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(profilePage);
            }
        });
    }

    private void addUserPosts()
    {
        String showEveryone = DatabaseMethods.GetPrivacySettings(whoIsTheUser).showPostEveryone;
        if (showEveryone.equals("True"))
        {
            loadOpenProfile();
        }
        else
            loadCloseProfile();
    }

    private void loadCloseProfile()
    {
        List<Post> posts = DatabaseMethods.GetPosts(whoIsTheUser);
        uniPosts = new ArrayList<>();

        String friendship = DatabaseMethods.CheckFriendship(LoggedInUser.UserId, whoIsTheUser);
        if (friendship.equals("1"))
        {
            for (int i = posts.size() - 1 ; i >= 0; i--)
            {
                if (posts.get(i).userId.equals(whoIsTheUser))
                {
                    List<PostPicture> picturesOfPost = DatabaseMethods.GetPostPictures(posts.get(i).postId);
                    byte[] picturePost;
                    if (!picturesOfPost.isEmpty())
                        picturePost = picturesOfPost.get(0).picture;
                    else
                        picturePost = null;

                    uniPosts.add(new UniPosts
                            (whoIsTheUser,
                                    posts.get(i).postId,
                                    posts.get(i).name,
                                    posts.get(i).postDate,
                                    posts.get(i).postText,
                                    posts.get(i).location,
                                    posts.get(i).smallProfilePicture,
                                    picturePost,
                                    0)
                    );
                }
            }
        }
        else
            Toast.makeText(this, "Kullanıcının profili kapalıdır", Toast.LENGTH_SHORT).show();
    }

    private void loadOpenProfile()
    {
        List<Post> posts = DatabaseMethods.GetPosts(whoIsTheUser);
        uniPosts = new ArrayList<>();

        for (int i = posts.size() - 1 ; i >= 0; i--)
        {
            if (posts.get(i).userId.equals(whoIsTheUser))
            {
                List<PostPicture> picturesOfPost = DatabaseMethods.GetPostPictures(posts.get(i).postId);
                byte[] picturePost;
                if (!picturesOfPost.isEmpty())
                    picturePost = picturesOfPost.get(0).picture;
                else
                    picturePost = null;

                uniPosts.add(new UniPosts
                        (whoIsTheUser,
                                posts.get(i).postId,
                                posts.get(i).name,
                                posts.get(i).postDate,
                                posts.get(i).postText,
                                posts.get(i).location,
                                posts.get(i).smallProfilePicture,
                                picturePost,
                                0)
                );
            }
        }


    }

    private void refreshPosts()
    {
        swipeRefreshLayout.setRefreshing(true);
        userPostFeed.removeAllViews();

        uniPostAdapter = new UniPostAdapter(this, uniPosts, 1);
        userPostFeed.setAdapter(uniPostAdapter);

        Toast.makeText(this, R.string.refresh_successful, Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
    }


    protected void refreshInformation()
    {
        String friendsLabel, photosLabel, followsLabel;

        friendsLabel = (String.valueOf(DatabaseMethods.GetFriends(whoIsTheUser).size()));

        int pictureCount = 0;
        for (int i = 0; i < uniPosts.size(); i++){
            if(!DatabaseMethods.GetPostPictures(uniPosts.get(i).getUniPostId()).isEmpty())
                pictureCount++;
        }
        photosLabel = String.valueOf(pictureCount);
        followsLabel = (String.valueOf(DatabaseMethods.GetStudentFollowing(whoIsTheUser).size()));

        friendPhotosLabel.setText(photosLabel);
        friendFriendsLabel.setText(friendsLabel);
        friendFollowsLabel.setText(followsLabel);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
