package com.seniorproject.uninet.uninet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SendNewPostActivity extends AppCompatActivity{

    InputMethodManager keyboardHider;
    Button shareButton;
    EditText uniPostDescription;
    Location mLastLocation = null;
    FusedLocationProviderClient fusedLocationClient;


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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_new_uni_post_template);

        shareButton = findViewById(R.id.share_post_button);
        uniPostDescription = findViewById(R.id.new_post_area);
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

        // share butonu için click listener
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userLocation = "";
                if (mLastLocation != null) {
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
                if (userLocation.equals("") || userLocation.equals(null))
                    userLocation = "None";
                //TODO: Server kapalıyken post atılırsa uygulama yanıt vermiyor.
                if (haveNetworkConnection()) {
                    DatabaseMethods.SendPost(getApplicationContext(), LoggedInUser.UserId, postText, userLocation, null);
                    Toast.makeText(SendNewPostActivity.this, R.string.successful_post, Toast.LENGTH_SHORT).show();
                    //New post refresh change
                    setResult(RESULT_OK);
                    LoggedInUser.FeedPostRefresh = true;
                    LoggedInUser.ProfilePostRefresh = true;
                    finish();
                } else
                    Toast.makeText(SendNewPostActivity.this, getString(R.string.internet_connection_not_valid), Toast.LENGTH_SHORT).show();


            }
        });
    }
}

