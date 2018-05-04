package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.seniorproject.uninet.uninet.Adapters.FriendListAdapter;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.UserListingInfo;

import java.util.ArrayList;
import java.util.List;

public class FriendsListActivity extends AppCompatActivity {

    private String whoIsTheUser;
    StoredUserInformation userInformation;

    private ArrayList<Friends> friendList;
    private RecyclerView friendScreen;

    private Button messageWriteButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_friends_list);

        // Declarations
        userInformation = new StoredUserInformation(this);
        whoIsTheUser = userInformation.getUserId();
        messageWriteButton = findViewById(R.id.friend_search_add_friend);
        friendList = new ArrayList<>();
        friendScreen = findViewById(R.id.friends_list);

        LoadFriends();

        FriendListAdapter friendListAdapter = new FriendListAdapter(this, friendList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        friendScreen.setLayoutManager(linearLayoutManager);
        friendScreen.setItemAnimator(new DefaultItemAnimator());
        friendScreen.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        friendScreen.setAdapter(friendListAdapter);



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
