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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.Adapters.FriendListAdapter;
import com.seniorproject.uninet.uninet.Adapters.UserSearchAdapter;
import com.seniorproject.uninet.uninet.ConstructorClasses.Friends;
import com.seniorproject.uninet.uninet.ConstructorClasses.User;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.UserListingInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FriendSearch extends AppCompatActivity {

    private String whoIsTheUser;
    StoredUserInformation userInformation;

    EditText searcBox;
    RecyclerView friendListRecycler;
    private ArrayList<User> userList;

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
        searcBox = findViewById(R.id.search_user);

        userList = new ArrayList<>();

        LoadUsers();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        final UserSearchAdapter userSearchAdapter = new UserSearchAdapter(userList, this);

        friendListRecycler.setLayoutManager(linearLayoutManager);
        friendListRecycler.setItemAnimator(new DefaultItemAnimator());
        friendListRecycler.setAdapter(userSearchAdapter);


        searcBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Search or Filter
                String who = searcBox.getText().toString().toLowerCase(Locale.getDefault());
                userSearchAdapter.myFilter(who);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void LoadUsers() {
        List<UserListingInfo> userListingInfo = DatabaseMethods.SearchUser("Alican");
        List<UserListingInfo> friends = DatabaseMethods.GetFriends(whoIsTheUser);

        if (userListingInfo != null)
        {
            for (int i = 0; i < userListingInfo.size(); i++)
            {
                for (int k = 0; k < friends.size(); k++)
                {
                    if(friends.get(k).userId.equals(userListingInfo.get(i).userId))
                    {
                        userList.add(new User(userListingInfo.get(i).userId, userListingInfo.get(i).name, userListingInfo.get(i).smallProfilePicture, 0));
                        break;
                    }
                    else if (!friends.get(k).userId.equals(userListingInfo.get(i).userId))
                        userList.add(new User(userListingInfo.get(i).userId, userListingInfo.get(i).name, userListingInfo.get(i).smallProfilePicture, 1));

                }
                // Check if searched user is a friend or not

            }
        }

    }
}
