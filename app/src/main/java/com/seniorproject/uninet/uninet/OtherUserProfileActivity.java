package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.Adapters.PostListAdapter;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.Post;

import java.util.ArrayList;
import java.util.List;

public class OtherUserProfileActivity extends AppCompatActivity {

    private ListView userPosts;
    private PostListAdapter postListAdapter;
    private ArrayList<UniPosts> uniPosts;
    private String whoIsTheUser;
    private SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        // Declaration
        whoIsTheUser = getIntent().getStringExtra("UserID");
        swipeRefreshLayout = findViewById(R.id.other_user_profile_swiper);
        userPosts = findViewById(R.id.other_user_uni_post_list_view);

        List<Post> postList = DatabaseMethods.GetPosts(whoIsTheUser);
        uniPosts = new ArrayList<>();

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

        postListAdapter = new PostListAdapter(this, 0, R.layout.uni_post_template, uniPosts);
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
    }

    private void refreshPosts()
    {
        postListAdapter.notifyDataSetChanged();
        userPosts.setAdapter(new PostListAdapter(this, 0, R.layout.uni_post_template, uniPosts));

        Toast.makeText(this, R.string.refresh_successful, Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    // To check if we are at top of the UniPost List.
    private boolean isListAtTop()
    {
        if(userPosts.getChildCount() == 0) return true;
        return userPosts.getChildAt(0).getTop() == 0;
    }



}
