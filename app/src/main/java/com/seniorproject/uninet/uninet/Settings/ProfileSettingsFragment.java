package com.seniorproject.uninet.uninet.Settings;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.R;
import com.seniorproject.uninet.uninet.StoredUserInformation;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileSettingsFragment extends Fragment{

    private static final String TAG = "ProfileSettingsFragment";

    ImageView backArrowButton;
    ImageView saveSettingsButton;
    EditText mPhoneNumber;
    EditText mEmailAddress;
    EditText mWebPage;
    TextView mChangeProfilePicture;
    Button mSetRelationship;
    byte[] bigProfilePictureImage;
    byte[] smallProfilePictureImage;
    String imagePath = "";

    StoredUserInformation userInformation;
    StoredUserInformation storedUserInformation;

    public static boolean isThereAChange;

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
        storedUserInformation = new StoredUserInformation(getContext());
        isThereAChange = false;



        backArrowButton = (getActivity()).findViewById(R.id.back_arrow_button);
        saveSettingsButton = (getActivity()).findViewById(R.id.apply_settings_button);
        mChangeProfilePicture = getActivity().findViewById(R.id.change_profile_photo);
        mPhoneNumber = getActivity().findViewById(R.id.phone_number);
        mEmailAddress = getActivity().findViewById(R.id.mail_address);
        mWebPage = getActivity().findViewById(R.id.web_page);
        mSetRelationship = getActivity().findViewById(R.id.relationship_status_button);


        // Set saved values:
        mPhoneNumber.setText(userInformation.getPhoneNumber());
        mEmailAddress.setText(userInformation.getMailAddress());
        mWebPage.setText(userInformation.getWebPage());
        mSetRelationship.setText(userInformation.getRelationshipStatus());


        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigation to Home Activity");
                getActivity().finish();


            }
        });

        // Text listeners for edit texts
        mPhoneNumber.addTextChangedListener(getTextWatcher(storedUserInformation.getPhoneNumber()));
        mEmailAddress.addTextChangedListener(getTextWatcher(storedUserInformation.getMailAddress()));
        mWebPage.addTextChangedListener(getTextWatcher(storedUserInformation.getWebPage()));
        mSetRelationship.addTextChangedListener(getTextWatcher(storedUserInformation.getRelationshipStatus()));


        mSetRelationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setRelationship();
            }
        });

        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: Save Changes");
                saveChanges();
            }
        });


        mChangeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermission();
            }
        });


    }

    private void saveChanges()
    {
        if (isThereAChange)
        {

            String userID = storedUserInformation.getUserId();
            final String phoneNumber = mPhoneNumber.getText().toString();
            final String mailAddress = mEmailAddress.getText().toString();
            final String webPage = mWebPage.getText().toString();
            final String relationshipStatus = mSetRelationship.getText().toString();

            String newPhoneNumber = "";
            String newMailAddress = "";
            String newWebPage = "";
            String newRelationshipStatus = "";

            if (!userInformation.getPhoneNumber().equals(mPhoneNumber))
            {
                newPhoneNumber = phoneNumber;
            }
            if (!userInformation.getMailAddress().equals(mEmailAddress))
            {
                newMailAddress = mailAddress;
            }
            if (!userInformation.getWebPage().equals(mWebPage))
            {
                newWebPage = webPage;
            }
            if (!userInformation.getRelationshipStatus().equals(mSetRelationship))
            {
                newRelationshipStatus = relationshipStatus;
            }

            DatabaseMethods.UpdateProfileInfo(getContext(),
                    userID,
                    newMailAddress,
                    newWebPage,
                    newPhoneNumber,
                    newRelationshipStatus,
                    "",
                    "");

            userInformation.setMailAddress(newMailAddress);
            userInformation.setWebPage(newWebPage);
            userInformation.setPhoneNumber(newPhoneNumber);
            userInformation.setRelationshipStatus(newRelationshipStatus);
            Toast.makeText(getContext(), getString(R.string.save_settings), Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getContext(),  getString(R.string.no_changes), Toast.LENGTH_SHORT).show();

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


    private void performCrop() {
        CropImage.activity().start(getContext(), this);
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

    private void setRelationship()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setTitle(R.string.pref_title_relationshipStatus);
        builder.setTitle(R.string.pref_title_relationshipStatus);

        final String[] relationshipTypes;
        relationshipTypes = getResources().getStringArray(R.array.pref_list_relationship_status);

        builder.setItems(relationshipTypes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                mSetRelationship.setText(relationshipTypes[item]);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void requestStoragePermission() {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // After user give the permission
                        //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        //startActivityForResult(intent, 9);
                        performCrop();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // After permission denial
                        if (response.isPermanentlyDenied()) {
                            Toast.makeText(getContext(), R.string.description_need_permission, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    private TextWatcher getTextWatcher(final String info) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();

                if (!text.equals(info))
                {
                    saveSettingsButton.setImageResource(R.drawable.ic_apply_settings);
                    isThereAChange = true;
                }
                else
                {
                    saveSettingsButton.setImageResource(R.drawable.ic_unsaved_settings);
                    isThereAChange = false;
                }

            }
        };
    }
}
