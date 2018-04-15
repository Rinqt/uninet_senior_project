package com.seniorproject.uninet.uninet;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SendNewPostActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    InputMethodManager keyboardHider;
    Button shareButton;
    EditText uniPostDescription;
    GoogleApiClient mGoogleApiClient = null;
    Location mLastLocation = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_new_uni_post_template);

        shareButton = findViewById(R.id.share_post_button);
        uniPostDescription = findViewById(R.id.new_post_area);
        keyboardHider = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }

        findViewById(R.id.new_uni_post_main_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                keyboardHider.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        // Login butonu için click listener
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userLocation = "";
                if(mLastLocation != null){
                    Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = gcd.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses.size() > 0)
                        userLocation = addresses.get(0).getAdminArea() + "-" + addresses.get(0).getSubAdminArea();
                }
                Log.i("locationPERMISSION", Manifest.permission.ACCESS_FINE_LOCATION);
                String postText = uniPostDescription.getText().toString();
                if(userLocation.equals("") || userLocation.equals(null))
                    userLocation = "None";
                DatabaseMethods.SendPost(LoggedInUser.UserId, postText, userLocation, null);

            }
        });
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}

