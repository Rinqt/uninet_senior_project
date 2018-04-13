package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

public class OtherUserProfileActivity extends AppCompatActivity {

    private ListView userPosts;
    private ListViewAdapter userPostsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        swipeRefreshLayout = findViewById(R.id.other_user_profile_swiper);

        userPosts = findViewById(R.id.other_user_uni_post_list_view);
        userPostsAdapter = new ListViewAdapter(this, 2);
        userPosts.setAdapter(userPostsAdapter);



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
        userPostsAdapter.notifyDataSetChanged();
        userPosts.setAdapter(new ListViewAdapter(getApplicationContext(), 1));

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
