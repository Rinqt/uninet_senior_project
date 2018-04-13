package com.seniorproject.uninet.uninet;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import java.util.Locale;


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

    ActionBarDrawerToggle toggle;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Session
        sessionChecker = new SessionChecker(this);

        if(!sessionChecker.loggedIn()){
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
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout); // Hidden drawer tanımı
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerSlideAnimationEnabled(false);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

        } else if (id == R.id.nav_transcript) {
            Intent transcriptIntent = new Intent(this, TranscriptActivity.class);
            startActivity(transcriptIntent);

        } else if (id == R.id.nav_diningList) {
            Intent diningList = new Intent(this, DiningActivity.class);
            startActivity(diningList);

        } else if (id == R.id.nav_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);

        } else if (id == R.id.nav_logout) {
            logout();

        } else if (id == R.id.nav_contact) {
            Intent mailIntent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:uninet@uninetapplication.com");
            mailIntent.setData(data);
            startActivity(mailIntent);

        } else if (id == R.id.nav_version) {
            Intent versionIntent = new Intent(this, VersionActivity.class);
            startActivity(versionIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }



    private void setupTabIcons() // 4 ana tabin iconu ayarlama
    {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    // Create fragments
    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FeedFragment());
        adapter.addFragment(new ProfileFragment());
        adapter.addFragment(new LecturesFragment());
        adapter.addFragment(new MessagesFragment());
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
                Toast.makeText(HomeActivity.this, getString(R.string.logout_thanks), Toast.LENGTH_LONG).show();

            }
        });

        AlertDialog alertDialog = logoutAlertDialog.create();
        alertDialog.show();
    }
}
