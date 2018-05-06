package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.Adapters.UserSearchAdapter;
import com.seniorproject.uninet.uninet.ConstructorClasses.User;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.UserListingInfo;

import java.util.ArrayList;
import java.util.List;

public class UserSearchActivity extends AppCompatActivity {

    private static final String TAG = "UserSearchActivity";

    private String whoIsTheUser;
    StoredUserInformation userInformation;

    EditText searchBox;
    RecyclerView friendListRecycler;
    private ArrayList<User> userList;

    private Button searchButton;

    private Button addFriend;
    private Button messageFriend;

    int textlength = 0;
    private List<UserListingInfo> tempUsers = new ArrayList<>();
    InputMethodManager keyboardHider;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_friend_search);
        keyboardHider = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        userInformation = new StoredUserInformation(this);
        whoIsTheUser = userInformation.getUserId();

        friendListRecycler = findViewById(R.id.friend_search_recycler_view);
        searchButton = findViewById(R.id.search_button);
        addFriend = findViewById(R.id.friend_search_add_friend);
        messageFriend = findViewById(R.id.friend_search_message_friend);
        searchBox = findViewById(R.id.search_user);

        userList = new ArrayList<>();



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        final UserSearchAdapter userSearchAdapter = new UserSearchAdapter(userList, this);

        friendListRecycler.setLayoutManager(linearLayoutManager);
        friendListRecycler.setItemAnimator(new DefaultItemAnimator());
        friendListRecycler.setAdapter(userSearchAdapter);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "searchButton OnClick");
                keyboardHider.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                String searchText = searchBox.getText().toString();
                startUserSearch(searchText);
                searchBox.setText("");

            }
        });



        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL)
                {
                    String searchText = searchBox.getText().toString();
                    startUserSearch(searchText);
                    searchBox.setText("");
                }
                return false;
            }
        });


        findViewById(R.id.search_user_activity_constraint_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                keyboardHider.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
    }

    private void startUserSearch(String name)
    {
        friendListRecycler.removeAllViews();
        userList.clear();


        if (name.equals("") || name.trim().isEmpty())
        {
            name = null;
        }
        List<UserListingInfo> userListingInfo = DatabaseMethods.SearchUser(name);

        if (userListingInfo.size() == 0)
        {
            Toast.makeText(this, "No User found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (int i = 0; i < userListingInfo.size(); i++)
            {
                if (!userListingInfo.get(i).userId.equals(whoIsTheUser))
                {
                    // Check if searched user is a friend
                     if(DatabaseMethods.CheckFriendship(whoIsTheUser, userListingInfo.get(i).userId).equals("1"))
                     {
                         // Add searched user as a friends to recycler view by using TPYE 0
                         if (userTypeControl(userListingInfo.get(i).userId))
                         {
                             userList.add(new User(userListingInfo.get(i).userId, userListingInfo.get(i).name, "", userListingInfo.get(i).smallProfilePicture, 0));
                         }
                         else
                             {
                                 String title = DatabaseMethods.GetProfileInfoTeacher(userListingInfo.get(i).userId).title;
                                 userList.add(new User(userListingInfo.get(i).userId, userListingInfo.get(i).name, title, userListingInfo.get(i).smallProfilePicture, 0));
                             }
                     }
                     else
                     {
                         if (userTypeControl(userListingInfo.get(i).userId))
                         {
                             userList.add(new User(userListingInfo.get(i).userId, userListingInfo.get(i).name, "", userListingInfo.get(i).smallProfilePicture, 1));
                         }
                         else
                         {
                             String title = DatabaseMethods.GetProfileInfoTeacher(userListingInfo.get(i).userId).title;
                             userList.add(new User(userListingInfo.get(i).userId, userListingInfo.get(i).name, title, userListingInfo.get(i).smallProfilePicture, 1));

                         }
                     }
                        // Add searched user as not friends to recycler view by using TPYE 1

                }
            }
        }
    }

    /*
    Returns true if user is student
    */
    private boolean userTypeControl(String friendID)
    {
        if (DatabaseMethods.GetUserType(friendID).equals("False"))
        {
            return true;
        }
        else
            return false;
    }
}

