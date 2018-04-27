package com.seniorproject.uninet.uninet.Settings;

import android.app.AlertDialog;
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

import java.io.ByteArrayOutputStream;
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
    byte[] myImage;

    StoredUserInformation userInformation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
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

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, 9);


            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == 9 && resultCode == RESULT_OK && data != null){
            Uri pickedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContext().getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();

            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            BitmapFactory.decodeFile(imagePath).compress(Bitmap.CompressFormat.PNG, 72, stream);
            myImage = stream.toByteArray();

            Bitmap bitmap = BitmapFactory.decodeByteArray(myImage, 0, myImage.length);
            CircleImageView imageView = (CircleImageView)getActivity().findViewById(R.id.profile_photo);

            imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, imageView.getWidth(), imageView.getHeight(), false));

            cursor.close();
        }
    }

    private void Save()
    {
        String imageString = "";

        for(int i = 0; i < myImage.length; i++){
            imageString += i < myImage.length - 1 ? (myImage[i] & 0xFF) + "," : (myImage[i] & 0xFF);
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
                setRelationship.setText(relationshipTypes[item]);
                dialog.dismiss();
            }
        });
        builder.show();




    }
}
