package com.seniorproject.uninet.uninet;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.ProfileInfoStudent;
import com.seniorproject.uninet.uninet.DatabaseClasses.UserListingInfo;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kaany on 27.02.2018.
 */

public class ProfileInfoActivity extends AppCompatActivity {

    // Buttons
    Button setProfileButton;
    Button friendsButton;
    Button photosButton;
    Button privacyButton;
    Button addFriend;
    Button removeFriend;
    Button blockFriend;

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

    CircleImageView userProfilePicture;
    CircleImageView friendRequestIcon;


    String whoIsTheUser;
    String otherUserId;
    String universityYearName;

    StoredUserInformation userInformation;
    UserListingInfo otherUserInformation;

    boolean isLoggedInUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile_info);

        userInformation = new StoredUserInformation(this);
        userProfilePicture = findViewById(R.id.content_profile_info_profile_picture);
        addFriend = findViewById(R.id.add_friend_button);
        removeFriend = findViewById(R.id.delete_friend_button);
        blockFriend = findViewById(R.id.block_friend_button);
        friendRequestIcon = findViewById(R.id.friend_request_icon);
        photosButton = findViewById(R.id.friends_button);
        setProfileButton = findViewById(R.id.set_profile_button);

        otherUserId = getIntent().getStringExtra("UserID");

        whoIsTheUser = userInformation.getUserId();

        // Eğer kullanıcı direk profilinden detaylara gitmek isterse otherUserId null dönüyor
        if (otherUserId == null)
            otherUserId = whoIsTheUser;


        otherUserInformation = DatabaseMethods.GetUserNamePic(otherUserId);

        // If profile belongs to current user, hide add/remove buttons
        if (otherUserId.equals(userInformation.getUserId()))
        {
            setTitle(userInformation.getUserName());
            addFriend.setVisibility(View.GONE);
            removeFriend.setVisibility(View.GONE);
            blockFriend.setVisibility(View.GONE);
            checkFriendshipRequests();
            isLoggedInUser = true;
        }
        else
            {
                // If user is not a friend
                setTitle(otherUserInformation.name);
                isLoggedInUser = false;
                friendRequestIcon.setVisibility(View.GONE);
                if (DatabaseMethods.CheckFriendship(whoIsTheUser, otherUserId).equals("1"))
                {
                    addFriend.setVisibility(View.GONE);
                    removeFriend.setVisibility(View.VISIBLE);
                    blockFriend.setVisibility(View.VISIBLE);

                }
                else
                {
                    addFriend.setVisibility(View.VISIBLE);
                    removeFriend.setVisibility(View.GONE);
                    blockFriend.setVisibility(View.GONE);
                }

            }







        if (otherUserInformation.smallProfilePicture != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(otherUserInformation.smallProfilePicture, 0, otherUserInformation.smallProfilePicture.length);
            userProfilePicture.setImageBitmap(bitmap);
        }



        if (!whoIsTheUser.contains(otherUserId))
        {
            // Posttan gidilen kullanıcının profilini yükle:
            loadOtherUserProfile();
        }
        else
            // Giriş yapmış olan kullanıcının profilini yükle
            loadCurrentUserProfile();








        setProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileSettings = new Intent(ProfileInfoActivity.this, UserSettingsActivity.class);
                profileSettings.putExtra("ProfileInfoProfileSettings", getString(R.string.pref_header_profile));
                startActivity(profileSettings);
            }
        });

        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent friendScreen = new Intent(getApplication(), FriendScreenActivity.class);
                friendScreen.putExtra("UserName", otherUserInformation.name);
                friendScreen.putExtra("UserID", otherUserId);

                startActivity(friendScreen);
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
                Intent profileSettings = new Intent(ProfileInfoActivity.this, UserSettingsActivity.class);
                profileSettings.putExtra("ProfileInfoPrivacy", getString(R.string.btn_title_privacy));
                startActivity(profileSettings);
            }
        });

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFriendRequest();

            }
        });

        removeFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeUser();

            }
        });

        blockFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockUser();

            }
        });

    }





    private void blockUser()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.description_block));
        builder.setMessage(getString(R.string.description_block_request));

        builder.setPositiveButton(getString(R.string.unsaved_changes_yes), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                DatabaseMethods.InsertRelation(whoIsTheUser, otherUserId, "0", null);
                DatabaseMethods.RemoveFriendFollowUponBlock(whoIsTheUser, otherUserId);
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton(getString(R.string.unsaved_changes_no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    private void checkFriendshipRequests()
    {
        //TODO Friendship requeste göre kırmızı butonu aç kapa
        // Eğer request varsa Arkadaşlar butonunda kaç request olduğunu gösteren bir yazı çıkıcak
    }

    private void sendFriendRequest()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //builder.setTitle(getString(R.string.unsaved_changes_title));
        builder.setMessage(getString(R.string.description_friendship_request));

        builder.setPositiveButton(getString(R.string.description_add_friend), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Leave the page
                DatabaseMethods.InsertRelation(whoIsTheUser, otherUserId, "1", "1");
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton(getString(R.string.unsaved_changes_no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Stay in the page
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void removeUser()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //builder.setTitle(getString(R.string.unsaved_changes_title));
        builder.setMessage(getString(R.string.description_friendship_removal));

        builder.setPositiveButton(getString(R.string.description_remove_friend), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Leave the page
                DatabaseMethods.RemoveFriend(whoIsTheUser, otherUserId);
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton(getString(R.string.unsaved_changes_no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Stay in the page
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

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
        if (isLoggedInUser)
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

        }

        if (view.getId() == R.id.email_text_result)
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

    private void loadCurrentUserProfile()
    {
        String id, name, department, year, post, friend, follow, follower, mail, phone, relation, webPage, postPicture;

        name = userInformation.getUserName();
        department = userInformation.getDepartment();
        year = userInformation.getEducationYear();
        post = userInformation.getUniPostsNumber();
        postPicture = userInformation.getPhotosNumber();
        friend = userInformation.getFriendsNumber();
        follow = userInformation.getFollowsNumber();
        follower = userInformation.getFollowersNumber();
        mail = userInformation.getMailAddress();
        phone = userInformation.getPhoneNumber();
        relation = userInformation.getRelationshipStatus();
        webPage = userInformation.getWebPage();

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
        String educationYear = calculateYearName(Integer.valueOf(year));

        userName.setText(name);
        userDepartment.setText(department);
        userYear.setText(educationYear);

        String postNumber = String.valueOf(post + " " + getResources().getString(R.string.user_total_post_number));
        totalPost.setText(postNumber);
        totalPictures.setText(postPicture);
        totalFriend.setText(friend);
        totalFollow.setText(follow);
        totalFollower.setText(follower);

        userMail.setText(mail);
        userPhone.setText(phone);
        userRelationshipStatus.setText(relation);
        userWebPage.setText(webPage);
    }

    private void loadOtherUserProfile()
    {
        String friendId, name, department, year, post, friend, follow, follower, mail, phone, relation, webPage;

        friendId = otherUserId;

        ProfileInfoStudent friendInfo = DatabaseMethods.GetProfileInfoStudent(friendId);

        name = friendInfo.name;
        department = friendInfo.department;
        year = friendInfo.academicYear;
        post = (String.valueOf(DatabaseMethods.GetPosts(friendId).size()));
        friend = (String.valueOf(DatabaseMethods.GetFriends(friendId).size()));
        follow = (String.valueOf(DatabaseMethods.GetStudentFollowing(friendId).size()));
        follower = (String.valueOf(DatabaseMethods.GetStudentFollowers(friendId).size()));
        mail = friendInfo.email;
        phone = friendInfo.phoneNumber;
        relation = friendInfo.relationship;
        webPage = friendInfo.webPage;

        //Button Declarations
        setProfileButton = findViewById(R.id.set_profile_button);
        friendsButton = findViewById(R.id.friends_button);
        photosButton = findViewById(R.id.photos_button);
        privacyButton = findViewById(R.id.privacy_button);

        buttonHider();
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
        String educationYear = calculateYearName(Integer.valueOf(year));

        userName.setText(name);
        userDepartment.setText(department);
        userYear.setText(educationYear);

        String postNumber = String.valueOf(post + " " + getResources().getString(R.string.user_total_post_number));
        totalPost.setText(postNumber);
        totalPictures.setText("error");
        totalFriend.setText(friend);
        totalFollow.setText(follow);
        totalFollower.setText(follower);

        userMail.setText(mail);
        userPhone.setText(phone);
        userRelationshipStatus.setText(relation);
        userWebPage.setText(webPage);
    }

    private void buttonHider()
    {
        setProfileButton.setVisibility(View.GONE);
        privacyButton.setVisibility(View.GONE);
    }



}