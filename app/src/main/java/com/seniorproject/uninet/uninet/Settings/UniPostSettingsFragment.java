package com.seniorproject.uninet.uninet.Settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.R;
import com.seniorproject.uninet.uninet.StoredUserInformation;

public class UniPostSettingsFragment extends Fragment{

    private static final String TAG = "UniPostSettingsFragment";

    StoredUserInformation userInformation;

    ImageView backArrowButton;
    ImageView saveSettingsButton;


    Switch mUniPostPrivacy;
    Switch mLocationPrivacy;
    Switch mMessagePrivacy;
    Switch mNotifications;
    Switch mBirthdayPrivacy;

    private boolean isThereAChangePrivacy;

    static private boolean change = false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View profileSettingsView = inflater.inflate(R.layout.fragment_uni_post_settings, container, false);
        Log.d(TAG, "OnCreateView: UniPostSettingsFragment");
        return profileSettingsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userInformation = new StoredUserInformation(getContext());

        backArrowButton = (getActivity()).findViewById(R.id.back_arrow_button);
        saveSettingsButton = (getActivity()).findViewById(R.id.privacy_apply_settings_button);

        mUniPostPrivacy = getActivity().findViewById(R.id.protect_uni_posts_switch);
        mLocationPrivacy = getActivity().findViewById(R.id.location_switch);
        mMessagePrivacy = getActivity().findViewById(R.id.messaging_switch);
        mNotifications = getActivity().findViewById(R.id.notification_switch);
        mBirthdayPrivacy = getActivity().findViewById(R.id.birthday_switch);

        // Switch change listeners

        mUniPostPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uniPost = String.valueOf(mUniPostPrivacy.isChecked());
                uniPost = uniPost.substring(0, 1).toUpperCase() + uniPost.substring(1);

                if (!uniPost.equals(userInformation.getUniPostPrivacy()))
                {
                    saveSettingsButton.setImageResource(R.drawable.ic_apply_settings);
                    isThereAChangePrivacy = true;
                }


            }
        });

        mLocationPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = String.valueOf(mLocationPrivacy.isChecked());
                location = location.substring(0, 1).toUpperCase() + location.substring(1);

                if (!location.equals(userInformation.getLocationPrivacy()))
                {
                    saveSettingsButton.setImageResource(R.drawable.ic_apply_settings);
                    isThereAChangePrivacy = true;
                }

            }
        });

        mMessagePrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String messaging = String.valueOf(mMessagePrivacy.isChecked());
                messaging = messaging.substring(0, 1).toUpperCase() + messaging.substring(1);

                Log.i(TAG, "MessagingPrivacy: " + userInformation.getMessagingPrivacy());

                if (!messaging.equals(userInformation.getMessagingPrivacy()))
                {
                    saveSettingsButton.setImageResource(R.drawable.ic_apply_settings);
                    isThereAChangePrivacy = true;
                }
            }
        });

        mNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notification = String.valueOf(mNotifications.isChecked());
                notification = notification.substring(0, 1).toUpperCase() + notification.substring(1);

                if (!notification.equals(userInformation.getNotification()))
                {
                    saveSettingsButton.setImageResource(R.drawable.ic_apply_settings);
                    isThereAChangePrivacy = true;

                }
            }
        });


        mBirthdayPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String birthday = String.valueOf(mBirthdayPrivacy.isChecked());
                birthday = birthday.substring(0, 1).toUpperCase() + birthday.substring(1);

                if (!birthday.equals(userInformation.getBirthdayPrivacy()))
                {
                    saveSettingsButton.setImageResource(R.drawable.ic_apply_settings);
                    isThereAChangePrivacy = true;

                }
            }
        });

        setSwitches();





        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: Navigation to Home Activity");
                getActivity().finish();
            }
        });

        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: Save Changes");
                saveChanges();
            }
        });


    }

    private void setSwitches()
    {
        if (userInformation.getUniPostPrivacy().equals("False"))
        {
            mUniPostPrivacy.setChecked(false);
        }
        else
            mUniPostPrivacy.setChecked(true);

        if (userInformation.getLocationPrivacy().equals("False"))
        {
            mLocationPrivacy.setChecked(false);
        }
        else
            mLocationPrivacy.setChecked(true);

        if (userInformation.getMessagingPrivacy().equals("False"))
        {
            mMessagePrivacy.setChecked(false);
        }
        else
            mMessagePrivacy.setChecked(true);

        if (userInformation.getNotification().equals("False"))
        {
            mNotifications.setChecked(false);
        }
        else
            mBirthdayPrivacy.setChecked(true);

        if (userInformation.getBirthdayPrivacy().equals("False"))
        {
            mBirthdayPrivacy.setChecked(false);
        }
        else
            mBirthdayPrivacy.setChecked(true);

    }

    private void saveChanges()
    {
        if (isThereAChangePrivacy)
        {

            String userID = userInformation.getUserId();

            String uniPostPrivacy = String.valueOf(mUniPostPrivacy.isChecked());
            String locationPrivacy =  String.valueOf(mLocationPrivacy.isChecked());
            String messagingPrivacy = String.valueOf( mMessagePrivacy.isChecked());
            String notification =  String.valueOf(mNotifications.isChecked());
            String birthdayPrivacy =  String.valueOf(mBirthdayPrivacy.isChecked());

            String newUniPostPrivacy = "";
            String newLocationPrivacy = "";
            String newMessagingPrivacy = "";
            String newNotification = "";
            String newBirthdayPrivacy = "";

            if (!userInformation.getUniPostPrivacy().equals(uniPostPrivacy))
            {
                newUniPostPrivacy = uniPostPrivacy;
            }
            if (!userInformation.getLocationPrivacy().equals(locationPrivacy))
            {
                newLocationPrivacy = locationPrivacy;
            }
            if (!userInformation.getMessagingPrivacy().equals(messagingPrivacy))
            {
                newMessagingPrivacy = messagingPrivacy;
            }
            if (!userInformation.getNotification().equals(notification))
            {
                newNotification = notification;
            }
            if (!userInformation.getBirthdayPrivacy().equals(birthdayPrivacy))
            {
                newBirthdayPrivacy = birthdayPrivacy;
            }

            DatabaseMethods.UpdatePrivacySettings(
                    userID,
                    newUniPostPrivacy,
                    newLocationPrivacy,
                    newMessagingPrivacy,
                    newNotification,
                    newBirthdayPrivacy);

            userInformation.setUniPostPrivacy(newUniPostPrivacy);
            userInformation.setLocationPrivacy(newLocationPrivacy);
            userInformation.setMessagingPrivacy(newMessagingPrivacy);
            userInformation.setNotification(newNotification);
            userInformation.setBirthdayPrivacy(newBirthdayPrivacy);

            Toast.makeText(getContext(), getString(R.string.save_settings), Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getContext(),  getString(R.string.no_changes), Toast.LENGTH_SHORT).show();

    }



}
