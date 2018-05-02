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
                else
                {
                    if (checkOtherSettings2())
                    {
                        saveSettingsButton.setImageResource(R.drawable.ic_unsaved_settings);
                        isThereAChangePrivacy = false;
                    }
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
                else
                {
                    if (checkOtherSettings2())
                    {
                        saveSettingsButton.setImageResource(R.drawable.ic_unsaved_settings);
                        isThereAChangePrivacy = false;
                    }
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
                else
                {
                    if (checkOtherSettings2())
                    {
                        saveSettingsButton.setImageResource(R.drawable.ic_unsaved_settings);
                        isThereAChangePrivacy = false;
                    }
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
                else
                {
                    if (checkOtherSettings2())
                    {
                        saveSettingsButton.setImageResource(R.drawable.ic_unsaved_settings);
                        isThereAChangePrivacy = false;
                    }
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
                else
                {
                    if (checkOtherSettings2())
                    {
                        saveSettingsButton.setImageResource(R.drawable.ic_unsaved_settings);
                        isThereAChangePrivacy = false;
                    }
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
                savePrivacySettings();
            }
        });


    }

    private void setSwitches()
    {
        Log.d(TAG, "----------CURRENT VALUES------------------: ");
        Log.d(TAG, "UniPost: " + userInformation.getUserId());
        Log.d(TAG, "UniPost: " + userInformation.getUniPostPrivacy());
        Log.d(TAG, "Location: " + userInformation.getLocationPrivacy());
        Log.d(TAG, "Message: " + userInformation.getMessagingPrivacy());
        Log.d(TAG, "Notification: " + userInformation.getNotification());
        Log.d(TAG, "Birthday: " + userInformation.getBirthdayPrivacy());
        Log.d(TAG, "----------CURRENT VALUES END--------------: ");


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
            mNotifications.setChecked(true);

        if (userInformation.getBirthdayPrivacy().equals("False"))
        {
            mBirthdayPrivacy.setChecked(false);
        }
        else
            mBirthdayPrivacy.setChecked(true);

        Log.d(TAG, "----------SWITCH VALUES------------------: ");

        Log.d(TAG, "SWITCH UniPost: " + String.valueOf(mUniPostPrivacy.isChecked()));
        Log.d(TAG, "SWITCH Location: " + String.valueOf(mLocationPrivacy.isChecked()));
        Log.d(TAG, "SWITCH Message: " + String.valueOf(mMessagePrivacy.isChecked()));
        Log.d(TAG, "SWITCH Notification: " + String.valueOf(mNotifications.isChecked()));
        Log.d(TAG, "SWITCH Birthday: " + String.valueOf(mBirthdayPrivacy.isChecked()));
        Log.d(TAG, "----------SWITCH VALUES END--------------: ");

    }

    private void savePrivacySettings()
    {
        if (isThereAChangePrivacy)
        {
            String userID = userInformation.getUserId();

            String uniPost = String.valueOf(mUniPostPrivacy.isChecked());
            uniPost = uniPost.substring(0, 1).toUpperCase() + uniPost.substring(1);

            String location =  String.valueOf(mLocationPrivacy.isChecked());
            location = location.substring(0, 1).toUpperCase() + location.substring(1);

            String message = String.valueOf(mMessagePrivacy.isChecked());
            message = message.substring(0, 1).toUpperCase() + message.substring(1);

            String notification = String.valueOf(mNotifications.isChecked());
            notification = notification.substring(0, 1).toUpperCase() + notification.substring(1);

            String birthday = String.valueOf(mBirthdayPrivacy.isChecked());
            birthday = birthday.substring(0, 1).toUpperCase() + birthday.substring(1);

            String newUniPostPrivacy;
            String newLocationPrivacy;
            String newMessagingPrivacy;
            String newNotification;
            String newBirthdayPrivacy;

            if (!userInformation.getUniPostPrivacy().equals(uniPost))
            {
                newUniPostPrivacy = uniPost;
            }
            else
                newUniPostPrivacy = userInformation.getUniPostPrivacy();

            if (!userInformation.getLocationPrivacy().equals(location))
            {
                newLocationPrivacy = location;
            }
            else
                newLocationPrivacy = userInformation.getLocationPrivacy();

            if (!userInformation.getMessagingPrivacy().equals(message))
            {
                newMessagingPrivacy = message;
            }
            else
                newMessagingPrivacy = userInformation.getMessagingPrivacy();

            if (!userInformation.getNotification().equals(notification))
            {
                newNotification = notification;
            }
            else
                newNotification = userInformation.getNotification();

            if (!userInformation.getBirthdayPrivacy().equals(birthday))
            {
                newBirthdayPrivacy = birthday;
            }
            else
                newBirthdayPrivacy = userInformation.getBirthdayPrivacy();

            DatabaseMethods.UpdatePrivacySettings(userID, newUniPostPrivacy, newLocationPrivacy, newMessagingPrivacy, newNotification, newBirthdayPrivacy);

            userInformation.setUniPostPrivacy(newUniPostPrivacy);
            userInformation.setLocationPrivacy(newLocationPrivacy);
            userInformation.setMessagingPrivacy(newMessagingPrivacy);
            userInformation.setNotification(newNotification);
            userInformation.setBirthdayPrivacy(newBirthdayPrivacy);

            Toast.makeText(getContext(), getString(R.string.save_settings), Toast.LENGTH_SHORT).show();

            //Set apply button to gray again
            saveSettingsButton.setImageResource(R.drawable.ic_unsaved_settings);
            isThereAChangePrivacy = false;
        }
        else
            Toast.makeText(getContext(),  getString(R.string.no_changes), Toast.LENGTH_SHORT).show();

    }

    private boolean checkOtherSettings2()
    {
        //TODO birthday serverdan doğru geliyor mu kontrol et..
        // Birthday true iken if in içine giriyor :/

        String uniPost = String.valueOf(mUniPostPrivacy.isChecked());
        uniPost = uniPost.substring(0, 1).toUpperCase() + uniPost.substring(1);

        String location =  String.valueOf(mLocationPrivacy.isChecked());
        location = location.substring(0, 1).toUpperCase() + location.substring(1);

        String message = String.valueOf(mMessagePrivacy.isChecked());
        message = message.substring(0, 1).toUpperCase() + message.substring(1);

        String notification = String.valueOf(mNotifications.isChecked());
        notification = notification.substring(0, 1).toUpperCase() + notification.substring(1);

        String birthday = String.valueOf(mBirthdayPrivacy.isChecked());
        birthday = birthday.substring(0, 1).toUpperCase() + birthday.substring(1);

        if (!uniPost.equals(userInformation.getUniPostPrivacy()))
        {
            return false;
        }
        else if (!location.equals(userInformation.getLocationPrivacy()))
        {
            return false;
        }
        else if (!message.equals(userInformation.getMessagingPrivacy()))
        {
            return false;
        }
        else if (!notification.equals(userInformation.getNotification()))
        {
            return false;
        }
        else if (!birthday.equals(userInformation.getBirthdayPrivacy()))
        {
            return false;
        }

        return true;
    }



}
