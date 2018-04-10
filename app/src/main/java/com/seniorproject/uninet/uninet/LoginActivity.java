package com.seniorproject.uninet.uninet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

    //Session
    private SessionChecker sessionChecker;

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


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED)
        {
            Log.i("PERMISSION", "Internet Permission found.");


            if (DatabaseMethods.LoginCheck(universityNumber.getText().toString(), userPassword.getText().toString()).equals("1")) {
                Log.i("attemptLogin", universityNumber.getText().toString() + userPassword.getText().toString() + "tries to sneak to app.");

                sessionChecker.setUserLoggedIn(true); // User Session

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), R.string.welcome_text, Toast.LENGTH_LONG).show();

            }
            else
            {
                keyboardHider.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                Toast.makeText(getApplicationContext(), R.string.wrong_login, Toast.LENGTH_LONG).show();
                Log.i("wrongInput", "Wrong Input");
            }
        }

        else
            Toast.makeText(getApplicationContext(), R.string.no_internet_permission, Toast.LENGTH_LONG).show();

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

