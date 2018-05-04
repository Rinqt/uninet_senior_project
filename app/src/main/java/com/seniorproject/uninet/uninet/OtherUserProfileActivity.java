package com.seniorproject.uninet.uninet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.Adapters.PostListAdapter;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.Post;
import com.seniorproject.uninet.uninet.DatabaseClasses.UserListingInfo;

import java.util.ArrayList;
import java.util.List;

public class OtherUserProfileActivity extends AppCompatActivity {

    private ListView userPosts;
    private PostListAdapter postListAdapter;
    private ArrayList<UniPosts> uniPosts;
    private String whoIsTheUser;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button detailedProfileButton;

    TextView friendPhotosLabel;
    TextView friendFriendsLabel;
    TextView friendFollowsLabel;

    ImageView friendProfilePhoto;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        // Declaration
        whoIsTheUser = getIntent().getStringExtra("UserID");
        swipeRefreshLayout = findViewById(R.id.other_user_profile_swiper);
        userPosts = findViewById(R.id.other_user_uni_post_list_view);
        detailedProfileButton = findViewById(R.id.user_profile_button);

        friendProfilePhoto = findViewById(R.id.profile_photo);
        friendPhotosLabel = findViewById(R.id.user_total_photos_label);
        friendFriendsLabel = findViewById(R.id.user_total_friends_label);
        friendFollowsLabel = findViewById(R.id.user_total_follows_label);

        List<Post> postList = DatabaseMethods.GetPosts(whoIsTheUser);
        uniPosts = new ArrayList<>();

        UserListingInfo user = DatabaseMethods.GetUserNamePic(whoIsTheUser);

        if (user.smallProfilePicture != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.smallProfilePicture, 0, user.smallProfilePicture.length);
            friendProfilePhoto.setImageBitmap(bitmap);
        }

        refreshInformation();

        for (int i = postList.size() - 1 ; i >= 0; i--)
        {
            //TODO: Am i missing post Picture or DatabaseMethods.GetPosts is not returning post Picture?
            uniPosts.add(new UniPosts(whoIsTheUser, postList.get(i).postId,
                    postList.get(i).name,
                    postList.get(i).postDate,
                    postList.get(i).postText,
                    postList.get(i).location,
                    postList.get(i).smallProfilePicture,
                    postList.get(i).smallProfilePicture));
        }

        postListAdapter = new PostListAdapter(this, 0, R.layout.edit_uni_post_template, uniPosts);
        userPosts.setAdapter(postListAdapter);

        // uniPostların olduğu list view refreshToSwipe özelliği ile çakışıyordu.
        // View ilk elemana ulaştığı zaman swipe yapılabilir kontrolü eklendi.
        userPosts.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem == 0 && isListAtTop()){
                    swipeRefreshLayout.setEnabled(true);
                }else{
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("TAG", "onRefresh called from SwipeRefreshLayout");
                refreshPosts();
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

    private void refreshPosts()
    {
        postListAdapter.notifyDataSetChanged();
        userPosts.setAdapter(new PostListAdapter(this, 0, R.layout.edit_uni_post_template, uniPosts));

        Toast.makeText(this, R.string.refresh_successful, Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    // To check if we are at top of the UniPost List.
    private boolean isListAtTop()
    {
        if(userPosts.getChildCount() == 0) return true;
        return userPosts.getChildAt(0).getTop() == 0;
    }

    protected void refreshInformation()
    {
        String friendsLabel, photosLabel, followsLabel;

        friendsLabel = (String.valueOf(DatabaseMethods.GetFriends(whoIsTheUser).size()));
        photosLabel = "error";
        followsLabel = (String.valueOf(DatabaseMethods.GetStudentFollowing(whoIsTheUser).size()));

        friendPhotosLabel.setText(photosLabel);
        friendFriendsLabel.setText(friendsLabel);
        friendFollowsLabel.setText(followsLabel);
    }
}
