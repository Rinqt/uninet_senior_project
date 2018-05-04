package com.seniorproject.uninet.uninet;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView universityNumber;
    private EditText userPassword;

    //SharedPrefs
    private SessionChecker sessionChecker;
    private StoredUserInformation userInformation;

    // Keyboard hider
    InputMethodManager keyboardHider;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        universityNumber = findViewById(R.id.user_name);
        userPassword = findViewById(R.id.user_password);

        final Button loginButton = findViewById(R.id.login_button);

        keyboardHider = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        //Session
        sessionChecker = new SessionChecker(this);
        userInformation = new StoredUserInformation(this);

        if (sessionChecker.loggedIn())
        {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
        //Session END

        if(universityNumber.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }

        findViewById(R.id.loginScreenLinearLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                keyboardHider.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        // kullanıcı şifre kısmına girip enter a basarsa diye bi listener
        userPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    Log.i("userPassword", "userPassword Entered.");

                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        // Login butonu için click listener
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("loginButton", "Login Button Pressed.");
                attemptLogin();
            }
        });

    }

    private void saveUserData()
    {
        final ProgressDialog dialog= new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading_message));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();


        Thread getUserData = new Thread() {
            // TODO User ID yi shared pref'e ekle.
            String whoIsTheUser = LoggedInUser.UserId;
            @Override
            public void run() {

                //TODO: Check if user ID belong to student or lecturer
                userInformation.setUserId(whoIsTheUser);
                userInformation.setUserName(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).name);
                userInformation.setDepartment(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).department);
                userInformation.setMailAddress(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).email);
                userInformation.setPhoneNumber(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).phoneNumber);
                userInformation.setRelationshipStatus(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).relationship);
                userInformation.setWebPage(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).webPage);
                userInformation.setFriendsNumber(String.valueOf(DatabaseMethods.GetFriends(whoIsTheUser).size()));
                userInformation.setFollowersNumber(String.valueOf(DatabaseMethods.GetStudentFollowers(whoIsTheUser).size()));
                userInformation.setFollowsNumber(String.valueOf(DatabaseMethods.GetStudentFollowing(whoIsTheUser).size()));
                userInformation.setUniPostsNumber(String.valueOf(DatabaseMethods.GetPosts(whoIsTheUser).size()));
                userInformation.setEducationYear(DatabaseMethods.GetProfileInfoStudent(whoIsTheUser).academicYear);

                userInformation.setUniPostPrivacy(DatabaseMethods.GetPrivacySettings(whoIsTheUser).showPostEveryone);
                userInformation.setLocationPrivacy(DatabaseMethods.GetPrivacySettings(whoIsTheUser).showLocationEveryone);
                userInformation.setMessagingPrivacy(DatabaseMethods.GetPrivacySettings(whoIsTheUser).receiveMessageFromEveryone);
                userInformation.setNotification(DatabaseMethods.GetPrivacySettings(whoIsTheUser).notifications);
                userInformation.setBirthdayPrivacy(DatabaseMethods.GetPrivacySettings(whoIsTheUser).showBirthdayEveryone);
                userInformation.setPhotosNumber(String.valueOf(DatabaseMethods.GetPostPictures(whoIsTheUser).size()));

                dialog.dismiss();
            }
        };
        getUserData.start();
        //TODO: Threadi kapatmak gerekiyor mu ?
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin()
    {
        if (isThereANetwork())
        {


            if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                    == PackageManager.PERMISSION_GRANTED)
            {
                Log.i("PERMISSION", "Internet Permission found.");

                String loginCheck = DatabaseMethods.LoginCheck(universityNumber.getText().toString(), userPassword.getText().toString());
                if (!loginCheck.equals(""))
                {
                    LoggedInUser.UserId = loginCheck.split(",")[0];
                    if(loginCheck.split(",")[1].equals("False"))
                        LoggedInUser.StudentId = DatabaseMethods.GetStudentId(LoggedInUser.UserId);
                    else
                        LoggedInUser.TeacherId = DatabaseMethods.GetTeacherId(LoggedInUser.UserId);

                    Log.i("attemptLogin",  LoggedInUser.UserId + " tries to sneak to app.");

                    // Retrieve user data from database and save locally
                    saveUserData();

                    sessionChecker.setUserLoggedIn(true); // User Session
                    sessionChecker.setLoginInfo(LoggedInUser.UserId + "," + LoggedInUser.TeacherId + "," + LoggedInUser.StudentId);

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);


                    Toast.makeText(getApplicationContext(), R.string.welcome_text, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    keyboardHider.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    Toast.makeText(getApplicationContext(), R.string.wrong_login, Toast.LENGTH_SHORT).show();
                    Log.i("wrongInput", "Wrong Input");
                }
            }
        }
        else
            Toast.makeText(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
    }

    // Check if there is an internet connection
    private boolean isThereANetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        Log.i("isNetworkAvailable", " Network Status: " + activeNetworkInfo);

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }


            // TODO: register the new account here.
            return true;
        }
    }
}

