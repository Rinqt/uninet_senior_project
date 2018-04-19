package com.seniorproject.uninet.uninet;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;

/**
 * Created by kaany on 27.02.2018.
 */

public class ProfileInfoActivity extends AppCompatActivity {

    // Buttons
    Button setProfileButton;
    Button friendsButton;
    Button photosButton;
    Button privacyButton;

    // TextViews
    TextView userName;
    TextView userDepartment;
    TextView userYear;
    TextView totalPost;
    TextView totalPictures;
    TextView totalFriend;
    TextView totalFollow;
    TextView totalFollower;

    TextView userFaculty;
    TextView userMail;
    TextView userPhone;
    TextView userRelationshipStatus;
    TextView userWebPage;

    String whoIsTheUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile_info);


        whoIsTheUser = LoggedInUser.UserId;

        //Button Declarations
        setProfileButton = findViewById(R.id.set_profile_button);
        friendsButton = findViewById(R.id.friends_button);
        photosButton = findViewById(R.id.photos_button);
        privacyButton = findViewById(R.id.privacy_button);

        //TextView Declarations
        userName = findViewById(R.id.user_name);
        userDepartment = findViewById(R.id.department);
        userYear = findViewById(R.id.user_year);
        totalPost = findViewById(R.id.user_total_post_label);
        totalPictures = findViewById(R.id.user_total_photos_label);
        totalFriend = findViewById(R.id.user_total_friends_label);
        totalFollow = findViewById(R.id.user_total_follows_label);
        totalFollower = findViewById(R.id.user_total_followers_label);

        userFaculty = findViewById(R.id.faculty_text_result);
        userMail = findViewById(R.id.email_text_result);
        userPhone = findViewById(R.id.user_phone_number_text_result);
        userRelationshipStatus = findViewById(R.id.user_relationship_text_result);
        userWebPage = findViewById(R.id.user_web_page_text_result);


        //Set labels with values
        userName.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).name);
        userDepartment.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).department);
        userYear.setText(String.valueOf(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).academicYear));
        totalPost.setText(String.valueOf(DatabaseMethods.GetPosts(whoIsTheUser).size()));
        totalPictures.setText("0");
        totalFriend.setText(String.valueOf(DatabaseMethods.GetFriends(whoIsTheUser).size()));
        totalFollower.setText(String.valueOf(DatabaseMethods.GetStudentFollowers(whoIsTheUser).size()));
        totalFollow.setText(String.valueOf(DatabaseMethods.GetStudentFollowing(whoIsTheUser).size()));

        userFaculty.setText("DB de fakülte yok");
        userMail.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).email);
        userPhone.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).phoneNumber);
        userRelationshipStatus.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).relationship);
        userWebPage.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).webPage);





        setProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                settingsIntent.putExtra( PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.ProfilePreferenceFragment.class.getName() );
                settingsIntent.putExtra( PreferenceActivity.EXTRA_NO_HEADERS, true );
                startActivity(settingsIntent);
            }
        });

        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        photosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        privacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Class içindeki Fragmentı çağırmayı putExtra ile yapıyoruz.
                Intent privacyIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                privacyIntent.putExtra( PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.SafetyAndPrivacy.class.getName() );
                privacyIntent.putExtra( PreferenceActivity.EXTRA_NO_HEADERS, true );
                startActivity(privacyIntent);
            }
        });
    }









}