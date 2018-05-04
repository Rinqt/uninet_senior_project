package com.seniorproject.uninet.uninet;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.Adapters.FriendListAdapter;
import com.seniorproject.uninet.uninet.ConstructorClasses.Friends;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.UserListingInfo;

import java.util.ArrayList;
import java.util.List;

public class FriendSearch extends AppCompatActivity {

    private String whoIsTheUser;
    StoredUserInformation userInformation;

    SearchView searchView;
    RecyclerView friendListRecycler;
    private ArrayList<Friends> friendList;

    private Button addFriend;
    private Button messageFriend;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_friend_search);

        userInformation = new StoredUserInformation(this);
        whoIsTheUser = userInformation.getUserId();

        friendListRecycler = findViewById(R.id.friend_search_recycler_view);
        addFriend = findViewById(R.id.friend_search_add_friend);
        messageFriend = findViewById(R.id.friend_search_message_friend);

        friendList = new ArrayList<>();

        LoadFriends();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);


        FriendListAdapter friendSearchAdapter = new FriendListAdapter(this, friendList, 1);

        friendListRecycler.setLayoutManager(linearLayoutManager);
        friendListRecycler.setItemAnimator(new DefaultItemAnimator());
        friendListRecycler.setAdapter(friendSearchAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_search, menu);

        final MenuItem friend = menu.findItem(R.id.search);

        searchView = (SearchView) friend.getActionView();

        searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                if (!searchView.isIconified())
                {
                    searchView  .setIconified(true);
                }

                friend.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {



                return false;
            }
        });











        return true;
    }

    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }

    private List<ClipData.Item> filter(List<ClipData.Item> pl, String query)
    {
        query=query.toLowerCase();
        final List<ClipData.Item> filteredModeList=new ArrayList<>();
        for (ClipData.Item model:pl)
        {
            final String text=model.toString().toLowerCase();
            if (text.startsWith(query))
            {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }

    private void LoadFriends()
    {
        List<UserListingInfo> userListingInfo = DatabaseMethods.GetFriends(whoIsTheUser);

        if (userListingInfo != null)
        {
            for (int i = 0; i < userListingInfo.size(); i++)
            {
                friendList.add(new Friends(userListingInfo.get(i).userId, userListingInfo.get(i).name,  userListingInfo.get(i).smallProfilePicture));
            }
        }
        else
            // TODO Default değerler ekle
            friendList.add(new Friends(null, "You have no friends.", null));
    }
}
