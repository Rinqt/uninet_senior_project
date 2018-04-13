package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class SendNewPostActivity extends AppCompatActivity {

    InputMethodManager keyboardHider;
    Button shareButton;
    EditText uniPostDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_new_uni_post_template);

        shareButton = findViewById(R.id.share_post_button);
        uniPostDescription = findViewById(R.id.new_post_area);
        keyboardHider = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

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
                Log.i("Shared Button", "Shared Button Pressed.");

            }
        });

    }
}

