package com.seniorproject.uninet.uninet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView userMail;
    TextView userPhone;
    TextView userRelationshipStatus;
    TextView userWebPage;

    // ImageViews
    ImageView uniPostImage;
    ImageView educationYearImage;
    ImageView totalPhotosImage;
    ImageView totalFriendsImage;
    ImageView totalFollowsImage;
    ImageView getTotalFollowersImage;


    String whoIsTheUser;
    String universityYearName;

    StoredUserInformation userInformation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile_info);


        whoIsTheUser = LoggedInUser.UserId;

        userInformation = new StoredUserInformation(this);


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


        userMail = findViewById(R.id.email_text_result);
        userPhone = findViewById(R.id.user_phone_number_text_result);
        userRelationshipStatus = findViewById(R.id.user_relationship_text_result);
        userWebPage = findViewById(R.id.user_web_page_text_result);

        // ImageView Declarations
        uniPostImage = findViewById(R.id.total_pots_image);
        educationYearImage = findViewById(R.id.university_progress_image);
        totalPhotosImage = findViewById(R.id.upload_photos_image);
        totalFriendsImage = findViewById(R.id.total_friends_image);
        totalFollowsImage = findViewById(R.id.total_follows_image);
        getTotalFollowersImage = findViewById(R.id.total_followers_image);


        //Set labels with values
        String educationYear = calculateYearName(Integer.valueOf(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).academicYear));

        userName.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).name);
        userDepartment.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).department);
        userYear.setText(educationYear);

        String postNumber = String.valueOf(DatabaseMethods.GetPosts(whoIsTheUser).size()) + " " + getResources().getString(R.string.user_total_post_number);
        totalPost.setText(postNumber);
        totalPictures.setText("0");
        totalFriend.setText(String.valueOf(DatabaseMethods.GetFriends(whoIsTheUser).size()));
        totalFollower.setText(String.valueOf(DatabaseMethods.GetStudentFollowers(whoIsTheUser).size()));
        totalFollow.setText(String.valueOf(DatabaseMethods.GetStudentFollowing(whoIsTheUser).size()));


        userMail.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).email);
        userPhone.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).phoneNumber);
        userRelationshipStatus.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).relationship);
        userWebPage.setText(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).webPage);





        setProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(ProfileInfoActivity.this, SettingsActivity.class);
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
                Intent privacyIntent = new Intent(ProfileInfoActivity.this, SettingsActivity.class);
                privacyIntent.putExtra( PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.SafetyAndPrivacy.class.getName() );
                privacyIntent.putExtra( PreferenceActivity.EXTRA_NO_HEADERS, true );
                startActivity(privacyIntent);
            }
        });
    }



    private String calculateYearName(int year)
    {
        universityYearName = "";

        if (year == 0)
        {
            universityYearName = getResources().getString(R.string.preparation_year);
            return universityYearName;

        }
        else if (year == 1)
        {
            universityYearName = getResources().getString(R.string.first_year);
            return universityYearName;
        }
        else if (year == 2)
        {
            universityYearName = getResources().getString(R.string.second_year);
            return universityYearName;
        }
        else if (year == 3)
        {
            universityYearName = getResources().getString(R.string.third_year);
            return universityYearName;
        }
        else if (year == 4)
        {
            universityYearName = getResources().getString(R.string.fourth_year);
            return universityYearName;
        }

        return universityYearName;
    }

    public void imageClicked(View view)
    {
        if (view.getId() == R.id.total_pots_image)
        {
            snackBarMaker(view, getString(R.string.snack_bar_total_posts_image, totalPost.getText().toString()));
        }
        else if (view.getId() == R.id.university_progress_image)
        {
            snackBarMaker(view, getString(R.string.snack_bar_university_progress_image, universityYearName));
        }
        else if (view.getId() == R.id.total_photos_image_container)
        {
            snackBarMaker(view, getString(R.string.snack_bar_profile_image, totalPictures.getText().toString()));
        }
        else if (view.getId() == R.id.total_friends_container)
        {
            snackBarMaker(view, getString(R.string.snack_bar_total_friends_image, totalFriend.getText().toString()));

        }
        else if (view.getId() == R.id.total_follows_container)
        {
            snackBarMaker(view, getString(R.string.snack_bar_total_follows_image, totalFollow.getText().toString()));

        }
        else if (view.getId() == R.id.total_followers_container)
        {
            snackBarMaker(view, getString(R.string.snack_bar_total_followers_image, totalFollower.getText().toString()));
        }
        else if (view.getId() == R.id.email_text_result)
        {
            snackBarMakerWithAction(view, userMail.getText().toString());
        }
        else if (view.getId() == R.id.user_phone_number_text_result)
        {
            snackBarMakerWithAction(view, userPhone.getText().toString());
        }
        else if (view.getId() == R.id.user_relationship_text_result)
        {
            snackBarMakerWithAction(view, userRelationshipStatus.getText().toString());
        }
        else if (view.getId() == R.id.user_web_page_text_result)
        {
            snackBarMakerWithAction(view, userWebPage.getText().toString());
        }
    }

    private void snackBarMaker(View view, String info)
    {
        Snackbar.make(view, info, Snackbar.LENGTH_SHORT).show();
    }

    private void snackBarMakerWithAction(View view, final String info)
    {
        Snackbar.make(view, info, Snackbar.LENGTH_LONG).setAction(R.string.snack_bar_copy_text, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyText(info);
            }
        }).show();
    }

    private void copyText(String text)
    {
        ClipboardManager clipboard = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData clip = ClipData.newPlainText(getResources().getString(R.string.snack_bar_copy_text_successful), text);
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplication(), R.string.snack_bar_copy_text_successful, Toast.LENGTH_SHORT).show();
    }



}