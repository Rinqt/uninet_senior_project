package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class LectureDetailsActivity extends AppCompatActivity {

    String lectureName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_details_activity);

        lectureName = getIntent().getStringExtra("Lecture_Name");
        String title = getString(R.string.lecture_detail_name, lectureName);
        setTitle(title);


    }
}
