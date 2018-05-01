package com.seniorproject.uninet.uninet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.seniorproject.uninet.uninet.Adapters.SettingsFragmentStatePagerAdapter;
import com.seniorproject.uninet.uninet.Settings.ProfileSettingsFragment;
import com.seniorproject.uninet.uninet.Settings.UniPostSettingsFragment;

import java.util.ArrayList;

public class UserSettingsActivity extends AppCompatActivity {

    private static final String TAG = "UserSettingsActivity";

    private Context mContext;
    private ImageView backArrow;
    private int fragmentNo;

    private SettingsFragmentStatePagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        mContext = UserSettingsActivity.this;
        Log.d(TAG, "onCreate: Started");

        // Declarations
        backArrow = findViewById(R.id.back_arrow_button);
        mViewPager = findViewById(R.id.view_pager_container);
        mRelativeLayout = findViewById(R.id.relative_layout_1);

        setSettings();
        setFragments();
        settingsSelector();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: go back to Home Activity");
                finish();

            }
        });

    }

    private void setSettings() {
        Log.d(TAG, "setSettings: Create User Account Settings List");

        ListView settingList = findViewById(R.id.all_settings_container);

        ArrayList<String> options = new ArrayList<>();

        /*
         * Fragment 0: ProfileSettingsFragment
         * Fragment 1: UniPostSettingsFragment
         */
        options.add(getString(R.string.pref_header_profile));
        options.add(getString(R.string.btn_title_privacy));


        final ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, options);
        settingList.setAdapter(adapter);

        settingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: navigating the fragment number " + position);
                setViewPager(position);

            }
        });
    }

    private void setFragments() {
        pagerAdapter = new SettingsFragmentStatePagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragmentToList(new ProfileSettingsFragment(), getString(R.string.pref_header_profile));
        pagerAdapter.addFragmentToList(new UniPostSettingsFragment(), getString(R.string.btn_title_privacy));
    }

    // Responsible for navigating between fragments:
    private void setViewPager(int fragmentNumber) {
        mRelativeLayout.setVisibility(View.GONE);
        Log.d(TAG, "setViewPager: navigate the fragment number: " + fragmentNumber);
        fragmentNo = fragmentNumber;


        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(fragmentNumber);

    }

    private void settingsSelector() {
        Intent intent = getIntent();

        if (intent.hasExtra("ProfileInfoProfileSettings")) {
            Log.i(TAG, "Received intent: ProfileSettings");
            setViewPager(pagerAdapter.getFragmentNumber(getString(R.string.pref_header_profile)));
        } else if (intent.hasExtra("ProfileInfoPrivacy")) {
            Log.i(TAG, "Received intent: ProfileSettings");
            setViewPager(pagerAdapter.getFragmentNumber(getString(R.string.btn_title_privacy)));
        }

    }

    @Override
    public void onBackPressed() {
        changeListener();
    }

    public void changeListener() {

        if (ProfileSettingsFragment.isThereAChange)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(getString(R.string.unsaved_changes_title));
            builder.setMessage(getString(R.string.unsaved_changes_description));

            builder.setPositiveButton(getString(R.string.unsaved_changes_yes), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Leave the page
                    finish();
                    dialog.dismiss();
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
        else
            super.onBackPressed();


    }

}
