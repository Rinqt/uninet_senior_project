package com.seniorproject.uninet.uninet.Settings;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.R;
import com.seniorproject.uninet.uninet.StoredUserInformation;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileSettingsFragment extends Fragment {

    private static final String TAG = "ProfileSettingsFragment";

    ImageView backArrowButton;
    ImageView saveSettingsButton;
    EditText phoneNumber;
    EditText mailAddress;
    EditText webPage;
    TextView changeProfilePicture;
    Button setRelationship;
    byte[] bigProfilePictureImage;
    byte[] smallProfilePictureImage;
    String imagePath = "";

    StoredUserInformation userInformation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View profileSettingsView = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        Log.d(TAG, "OnCreateView: ProfileSettingsFragment");
        return profileSettingsView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ProfileSettingsFragment");

        userInformation = new StoredUserInformation(Objects.requireNonNull(getContext()));


        backArrowButton = (getActivity()).findViewById(R.id.back_arrow_button);
        saveSettingsButton = (getActivity()).findViewById(R.id.apply_settings_button);
        changeProfilePicture = getActivity().findViewById(R.id.change_profile_photo);
        phoneNumber = getActivity().findViewById(R.id.phone_number);
        mailAddress = getActivity().findViewById(R.id.mail_address);
        webPage = getActivity().findViewById(R.id.web_page);
        setRelationship = getActivity().findViewById(R.id.relationship_status_button);


        // Set saved values:
        phoneNumber.setText(userInformation.getPhoneNumber());
        mailAddress.setText(userInformation.getMailAddress());
        webPage.setText(userInformation.getWebPage());
        setRelationship.setText(userInformation.getRelationshipStatus());


        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigation to Home Activity");
                getActivity().finish();
            }
        });


        setRelationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRelationship();
            }
        });

        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();

            }
        });


        changeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCrop();
            }
        });


    }

    private void performCrop() {
        CropImage.activity().start(getContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                Uri originalUri = result.getOriginalUri();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 72, stream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                smallProfilePictureImage = stream.toByteArray();

                CircleImageView imageView = (CircleImageView) getActivity().findViewById(R.id.profile_photo);

                imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, imageView.getWidth(), imageView.getHeight(), false));

                stream = new ByteArrayOutputStream();
                try {
                    MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), originalUri).compress(Bitmap.CompressFormat.PNG, 72, stream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bigProfilePictureImage = stream.toByteArray();

                int a = 0;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void Save() {
        String bigImageString = "";
        String smallImageString = "";

        for (int i = 0; i < bigProfilePictureImage.length; i++) {
            bigImageString += i < bigProfilePictureImage.length - 1 ? (bigProfilePictureImage[i] & 0xFF) + "," : (bigProfilePictureImage[i] & 0xFF);
        }

        for (int i = 0; i < smallProfilePictureImage.length; i++) {
            smallImageString += i < smallProfilePictureImage.length - 1 ? (smallProfilePictureImage[i] & 0xFF) + "," : (smallProfilePictureImage[i] & 0xFF);
        }
    }

    private void setRelationship() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setTitle(R.string.pref_title_relationshipStatus);
        builder.setTitle(R.string.pref_title_relationshipStatus);

        final String[] relationshipTypes;
        relationshipTypes = getResources().getStringArray(R.array.pref_list_relationship_status);

        builder.setItems(relationshipTypes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                setRelationship.setText(relationshipTypes[item]);
                dialog.dismiss();
            }
        });
        builder.show();


    }
}
