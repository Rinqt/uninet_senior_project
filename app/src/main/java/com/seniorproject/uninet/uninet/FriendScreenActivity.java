package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.seniorproject.uninet.uninet.Adapters.FriendListAdapter;
import com.seniorproject.uninet.uninet.ConstructorClasses.Friends;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.UserListingInfo;

import java.util.ArrayList;
import java.util.List;

public class FriendScreenActivity extends AppCompatActivity {


    StoredUserInformation userInformation;
    String whoIsTheUser;

    RecyclerView friendRecyclerView;

    ArrayList<Friends> friendsList;

    String otherUserID;
    String otherUserName;
    int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_friends_list);

        // Declaration
        userInformation = new StoredUserInformation(this);
        whoIsTheUser = userInformation.getUserId();

        otherUserName = getIntent().getStringExtra("UserName");
        otherUserID = getIntent().getStringExtra("UserID");


        if (!whoIsTheUser.equals(otherUserID))
        {
            whoIsTheUser = otherUserID;
            type = 0;
        }
        else
            type = 1;

        friendRecyclerView = findViewById(R.id.friends_list);
        friendsList = new ArrayList<>();

        loadFriends();

        FriendListAdapter friendListAdapter = new FriendListAdapter(this, friendsList, type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        friendRecyclerView.setLayoutManager(linearLayoutManager);
        friendRecyclerView.setItemAnimator(new DefaultItemAnimator());
        friendRecyclerView.setAdapter(friendListAdapter );




    }

    private void loadFriends()
    {
       List<UserListingInfo> userFriends = DatabaseMethods.GetFriends(whoIsTheUser);

        for (int i = 0; i < userFriends.size(); i++) {
            friendsList.add(new Friends(userFriends.get(i).userId, userFriends.get(i).name, userFriends.get(i).smallProfilePicture));
        }


    }

}