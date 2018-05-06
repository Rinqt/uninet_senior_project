package com.seniorproject.uninet.uninet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.seniorproject.uninet.uninet.ConstructorClasses.LoggedInUser;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.Post;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SendNewPostActivity extends AppCompatActivity {

    InputMethodManager keyboardHider;
    Button shareButton;
    Button photoButton;
    EditText uniPostDescription;
    TextView uploadSuccess;
    Location mLastLocation = null;
    FusedLocationProviderClient fusedLocationClient;
    byte[] imageBytes = null;

    StoredUserInformation userInformation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_new_uni_post_template);

        userInformation = new StoredUserInformation(this);

        shareButton = findViewById(R.id.share_post_button);
        photoButton = findViewById(R.id.upload_post_picture);
        uniPostDescription = findViewById(R.id.new_post_area);
        uploadSuccess = findViewById(R.id.picture_upload_result);
        keyboardHider = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    mLastLocation = location;
                }
            });
        }

        findViewById(R.id.new_uni_post_main_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                keyboardHider.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestStoragePermission();
            }
        });

        // share butonu için click listener
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String userLocation = "";
                if (mLastLocation != null)
                {
                    Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = null;

                    try
                    {
                        addresses = gcd.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    if (addresses.size() > 0)
                        userLocation = addresses.get(0).getAdminArea() + "-" + addresses.get(0).getSubAdminArea();
                }

                Log.i("locationPERMISSION", Manifest.permission.ACCESS_FINE_LOCATION);
                String postText = uniPostDescription.getText().toString();

                if (userLocation.equals("") || userLocation.equals(null))
                    userLocation = "None";
                if (haveNetworkConnection()) {
                    String stringBytes = "";
                    if(imageBytes != null)
                    {
                        for (int i = 0; i < imageBytes.length; i++)
                        {
                            stringBytes += i < imageBytes.length - 1 ? (imageBytes[i] & 0xFF) + "," : (imageBytes[i] & 0xFF);
                        }
                    }

                    if (userInformation.getLocationPrivacy().equals("False"))
                    {
                        DatabaseMethods.SendPost(getApplicationContext(), LoggedInUser.UserId, postText, userLocation, stringBytes);
                        Toast.makeText(SendNewPostActivity.this, R.string.successful_post, Toast.LENGTH_SHORT).show();
                        List<Post> userPosts = DatabaseMethods.GetPosts(userInformation.getUserId());
                        userInformation.setUniPostsNumber(String.valueOf(userPosts.size()));


                    }
                    else
                        {
                            DatabaseMethods.SendPost(getApplicationContext(), LoggedInUser.UserId, postText, userLocation, stringBytes);
                            Toast.makeText(SendNewPostActivity.this, R.string.successful_post, Toast.LENGTH_SHORT).show();
                            List<Post> userPosts = DatabaseMethods.GetPosts(userInformation.getUserId());
                            userInformation.setUniPostsNumber(String.valueOf(userPosts.size()));

                        }


                    //New post refresh change
                    setResult(RESULT_OK);
                    LoggedInUser.FeedPostRefresh = true;
                    LoggedInUser.ProfilePostRefresh = true;
                    finish();

                }
                else
                    Toast.makeText(SendNewPostActivity.this, getString(R.string.internet_connection_not_valid), Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 6) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK && data != null) {

                Uri pickedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                cursor.moveToFirst();

                String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                imageBytes = stream.toByteArray();
                uploadSuccess.setText(R.string.uni_post_picture_upload);
            }
        }
    }


    private boolean haveNetworkConnection() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkStatus = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo status : networkStatus) {
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

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }


    private void requestStoragePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, 6);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // After permission denial
                        if (response.isPermanentlyDenied()) {
                            Toast.makeText(getApplicationContext(), R.string.description_need_permission, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
}

