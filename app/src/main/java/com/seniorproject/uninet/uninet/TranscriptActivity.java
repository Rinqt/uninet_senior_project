package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.seniorproject.uninet.uninet.Adapters.TranscriptAdapter;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.TranscriptCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaany on 27.02.2018.
 */

public class TranscriptActivity extends AppCompatActivity {

    String whoIsTheUser;
    StoredUserInformation userInformation;

    ArrayList<TranscriptRecords> courses;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcript);

        userInformation = new StoredUserInformation(this);
        whoIsTheUser = userInformation.getUserId();

        courses = new ArrayList<>();

        create2017Semester();
        create2018Semester();

        TranscriptAdapter adapter = new TranscriptAdapter(this, courses);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);
    }


    /**
     * Type 1: Create Course Headers
     * Type 2: Create Courses Information
     * Type 3: Create Semester Summary Headers
     * Type 4: Create Summary Information
     * Type 9: Create Fall Semester Headers
     * Type 8: Create Spring Semester Headers
     */
    private void create2017Semester()
    {
       
        List<TranscriptCourse> transcriptInfo = DatabaseMethods.GetTranscript(whoIsTheUser);

        String semesterPoints;
        String semesterECTS;
        String totalECTS;
        String TotalPoints;

        boolean isThereTitle = false;

        // Get transcript info
        for(int i = 0; i < transcriptInfo.size(); i++)
        {

            if (transcriptInfo.get(i).educationYear.equals("2017"))
            {
                if (transcriptInfo.get(i).semester.equals("False"))
                {

                    if (!isThereTitle)
                    {
                        courses.add(new TranscriptRecords("", "", "", "", "", "2017", "", 9));
                        courses.add(new TranscriptRecords("", "", "", "", "", "", "", 1));

                        isThereTitle = true;
                    }

                    courses.add(new TranscriptRecords(transcriptInfo.get(i).courseUnit,
                            transcriptInfo.get(i).courseUnitTitle,
                            transcriptInfo.get(i).successGrade,
                            transcriptInfo.get(i).ects,
                            transcriptInfo.get(i).point,
                            transcriptInfo.get(i).educationYear,
                            transcriptInfo.get(i).semester,
                            2));

                }
            }
        }

        courses.add(new TranscriptRecords("", "", "", "", "", "", "", 3));
        courses.add(new TranscriptRecords("", "", "", "", "", "", "", 4));
        isThereTitle = false;

        for(int i = 0; i < transcriptInfo.size(); i++)
        {
            if (transcriptInfo.get(i).educationYear.equals("2017"))
            {
                if (transcriptInfo.get(i).semester.equals("True"))
                {
                    // Create Title for Fall Semester
                    if (!isThereTitle)
                    {
                        courses.add(new TranscriptRecords("", "", "", "", "", "2017", "", 8));
                        courses.add(new TranscriptRecords("", "", "", "", "", "", "", 1));

                        isThereTitle = true;
                    }

                courses.add(new TranscriptRecords(transcriptInfo.get(i).courseUnit,
                        transcriptInfo.get(i).courseUnitTitle,
                        transcriptInfo.get(i).successGrade,
                        transcriptInfo.get(i).ects,
                        transcriptInfo.get(i).point,
                        transcriptInfo.get(i).educationYear,
                        transcriptInfo.get(i).semester,
                        2));
                }
            }
        }
        courses.add(new TranscriptRecords("", "", "", "", "", "", "", 3));
        courses.add(new TranscriptRecords("", "", "", "", "", "", "", 4));


    }

    /**
     * Type 1: Create Course Headers
     * Type 2: Create Courses Information
     * Type 3: Create Semester Summary Headers
     * Type 4: Create Summary Information
     * Type 9: Create Fall Semester Headers
     * Type 8: Create Spring Semester Headers
     */
    private void create2018Semester()
    {

        List<TranscriptCourse> transcriptInfo = DatabaseMethods.GetTranscript(whoIsTheUser);
        boolean isThereTitle = false;

        // Get transcript info
        for(int i = 0; i < transcriptInfo.size(); i++)
        {
            if (transcriptInfo.get(i).educationYear.equals("2018") && transcriptInfo.get(i).semester.equals("False"))
            {
                if (!isThereTitle)
                {
                    courses.add(new TranscriptRecords("", "", "", "", "", "2018", "", 9));
                    courses.add(new TranscriptRecords("", "", "", "", "", "", "", 1));

                    isThereTitle = true;
                }

                courses.add(new TranscriptRecords(transcriptInfo.get(i).courseUnit,
                        transcriptInfo.get(i).courseUnitTitle,
                        transcriptInfo.get(i).successGrade,
                        transcriptInfo.get(i).ects,
                        transcriptInfo.get(i).point,
                        transcriptInfo.get(i).educationYear,
                        transcriptInfo.get(i).semester,
                        2));

            }
        }

        courses.add(new TranscriptRecords("", "", "", "", "", "", "", 3));
        courses.add(new TranscriptRecords("", "", "", "", "", "", "", 4));
        isThereTitle = false;

        for(int i = 0; i < transcriptInfo.size(); i++)
        {
            if (transcriptInfo.get(i).educationYear.equals("2018"))
            {
                if (transcriptInfo.get(i).semester.equals("True"))
                {
                    // Create Title for Fall Semester
                    if (!isThereTitle)
                    {
                        courses.add(new TranscriptRecords("", "", "", "", "", "2018", "", 8));
                        courses.add(new TranscriptRecords("", "", "", "", "", "", "", 1));

                        isThereTitle = true;
                    }

                courses.add(new TranscriptRecords(transcriptInfo.get(i).courseUnit,
                        transcriptInfo.get(i).courseUnitTitle,
                        transcriptInfo.get(i).successGrade,
                        transcriptInfo.get(i).ects,
                        transcriptInfo.get(i).point,
                        transcriptInfo.get(i).educationYear,
                        transcriptInfo.get(i).semester,
                        2));
                }
            }
        }
        courses.add(new TranscriptRecords("", "", "", "", "", "", "", 3));
        courses.add(new TranscriptRecords("", "", "", "", "", "", "", 4));

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
}
