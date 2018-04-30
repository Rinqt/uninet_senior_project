package com.seniorproject.uninet.uninet;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Objects;


public class HomeActivity extends AppCompatActivity
        implements
        FeedFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        LecturesFragment.OnFragmentInteractionListener,
        MessagesFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {


    ViewPager viewPager;
    TabLayout tabLayout;
    DrawerLayout drawerLayout;
    SessionChecker sessionChecker;
    StoredUserInformation userInformation;

    ActionBarDrawerToggle toggle;

    private String[] myFragmentTags = {"MyFeedFragment", "MyProfileFragment", "MyLecturesFragment", "MyMessagesFragment"};

    private int[] tabIcons = {
            R.mipmap.home_icon,
            R.mipmap.profile_icon,
            R.mipmap.lectures_icon,
            R.mipmap.messages_icon
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Set Toolbar
        final Toolbar myToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.app_name);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //Session
        sessionChecker = new SessionChecker(this);
        userInformation = new StoredUserInformation(this);

        if(!sessionChecker.loggedIn())
        {
            logout();
        }

        String loginInfo = sessionChecker.GetLoginInfo();
        String userId  = loginInfo.split(",")[0];
        String teacherId  = loginInfo.split(",")[1];
        String studentId  = loginInfo.split(",")[2];

        LoggedInUser.UserId = userId;
        LoggedInUser.TeacherId = teacherId.equals("null") ? null : teacherId;
        LoggedInUser.StudentId = studentId.equals("null") ? null : studentId;



        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);
        drawerLayout = findViewById(R.id.drawer_layout); // Hidden drawer tanımı
        NavigationView navigationView = findViewById(R.id.nav_view);




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLocationPermission();

            }
        });

        toggle = new ActionBarDrawerToggle(this, drawerLayout,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerSlideAnimationEnabled(false);

        navigationView.setNavigationItemSelectedListener(this);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();




        //New post refresh change
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //TODO: refresh only when needed
            @Override
            public void onPageSelected(int position) {
                RefreshCurrentPage();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("FRAGMENTTAG", "" + tab.getPosition());
                if(tab.getPosition() == 0)
                {
                    android.support.v4.app.Fragment feedList = getSupportFragmentManager().findFragmentByTag(myFragmentTags[0]);
                    ListView fList = ((FeedFragment)feedList).uniPostFeed;
                    int fListHeight = fList.getHeight();
                    fList.smoothScrollToPositionFromTop(0, fListHeight/2, 10);
                }
                else if (tab.getPosition() == 1)
                {
                    android.support.v4.app.Fragment profileList = getSupportFragmentManager().findFragmentByTag(myFragmentTags[1]);
                    ListView pList = ((ProfileFragment)profileList).unipost_list;
                    int pListHeight = pList.getHeight();
                    pList.smoothScrollToPositionFromTop(0, pListHeight/2, 10);
                }


            }
        });
    }

    //New post refresh change
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //1 is send new post
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                RefreshCurrentPage();
            }
        }
    }

    //New post refresh change
    private  void RefreshCurrentPage(){

        int item = viewPager.getCurrentItem();
        android.support.v4.app.Fragment page = getSupportFragmentManager().findFragmentByTag(myFragmentTags[item]);

        Log.i("Internet", ""+ PackageManager.PERMISSION_GRANTED);

        if(haveNetworkConnection())
        {
            if(page != null){
                if(item == 0) {
                    ((FeedFragment)page).refreshPosts();
                }
                else if(item == 1) {
                    ((ProfileFragment)page).refreshPosts();
                }
            }
        }
        else
            Toast.makeText(HomeActivity.this, getString(R.string.internet_connection_not_valid), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.search:
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_timeTable) {
            Intent timeTableIntent = new Intent(this, TimeTableActivity.class);
            startActivity(timeTableIntent);
        }

        else if (id == R.id.nav_transcript)
        {
            Intent transcriptIntent = new Intent(this, TranscriptActivity.class);
            startActivity(transcriptIntent);

        }
        else if (id == R.id.nav_diningList)
        {
            Intent diningList = new Intent(this, DiningActivity.class);
            startActivity(diningList);

        }
        else if (id == R.id.nav_settings)
        {
            Intent settingsIntent = new Intent(this, UserSettingsActivity.class);
            startActivity(settingsIntent);

        }
        else if (id == R.id.nav_logout)
        {
            logout();

        }
        else if (id == R.id.nav_contact)
        {
            Intent mailIntent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:uninet@uninetapplication.com");
            mailIntent.setData(data);
            startActivity(mailIntent);

        }
        else if (id == R.id.nav_version)
        {
            Intent versionIntent = new Intent(this, VersionActivity.class);
            startActivity(versionIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }



    private void setupTabIcons() // 4 ana tabin iconu ayarlama
    {
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(tabIcons[3]);
    }

    //New post refresh change
    // Create fragments
    private void setupViewPager(ViewPager viewPager)
    {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        FeedFragment feed = new FeedFragment();
        adapter.addFragment(feed);
        getSupportFragmentManager().beginTransaction().add(feed, "MyFeedFragment");

        ProfileFragment profile = new ProfileFragment();
        adapter.addFragment(profile);
        getSupportFragmentManager().beginTransaction().add(profile, "MyProfileFragment");

        LecturesFragment lectures = new LecturesFragment();
        adapter.addFragment(lectures);
        getSupportFragmentManager().beginTransaction().add(lectures, "MyLecturesFragment");

        MessagesFragment messages = new MessagesFragment();
        adapter.addFragment(messages);
        getSupportFragmentManager().beginTransaction().add(messages, "MyMessagesFragment");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_search, menu);
        return true;
    }

    private void logout()
    {
        AlertDialog.Builder logoutAlertDialog = new AlertDialog.Builder(this);
        logoutAlertDialog.setTitle(getString(R.string.logout_title));
        logoutAlertDialog.setMessage(getString(R.string.logout_message));

        logoutAlertDialog.setPositiveButton(getString(R.string.logout_positive_answer), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Close the dialog box and app
                dialogInterface.dismiss();
                sessionChecker.setUserLoggedIn(false);
                sessionChecker.setLoginInfo("");

                LoggedInUser.UserId = null;
                LoggedInUser.TeacherId = null;
                LoggedInUser.StudentId = null;

                sessionChecker.editor.clear().commit();
                userInformation.dataEditor.clear().commit();

                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginIntent);
                finish();
            }
        });

        logoutAlertDialog.setNegativeButton(getString(R.string.logout_negative_answer), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Toast.makeText(HomeActivity.this, getString(R.string.logout_thanks), Toast.LENGTH_SHORT).show();

            }
        });

        AlertDialog alertDialog = logoutAlertDialog.create();
        alertDialog.show();
    }

    public boolean haveNetworkConnection() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkStatus = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo status : networkStatus)
        {
            if (status.getTypeName().equalsIgnoreCase("WIFI"))
                if (status.isConnected())
                    haveConnectedWifi = true;
            if (status.getTypeName().equalsIgnoreCase("MOBILE"))
                if (status.isConnected())
                    haveConnectedMobile = true;
        }

        Log.i("Connection WIFI", "" + haveConnectedWifi);
        Log.i("Connection MOBILE", "" + haveConnectedMobile);
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void requestLocationPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // After user give the permission
                        Intent sendNewPostIntent = new Intent(getApplicationContext(), SendNewPostActivity.class);
                        startActivityForResult(sendNewPostIntent, 1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // After permission denial
                        if (response.isPermanentlyDenied()) {
                            Toast.makeText(getApplication(), R.string.description_need_permission, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
}
