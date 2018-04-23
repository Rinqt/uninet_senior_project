package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.Adapters.CourseAdapter;
import com.seniorproject.uninet.uninet.DatabaseClasses.CourseContent;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaany on 27.02.2018.
 */

public class TimeTableActivity extends AppCompatActivity {

    // TextViews
    TextView cTime;
    TextView cCode;
    TextView cName;
    TextView cClassroom;
    TextView cLecturer;

    // Table
    TableRow cTable;

    String whoIsTheUser;

    ListView mondayList;
    ListView tuesdayList;
    ListView wednesdayList;
    ListView thursdayList;
    ListView fridayList;

    ArrayList<Courses> courses;

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deneme_layout);

        whoIsTheUser = LoggedInUser.UserId;

        List<CourseContent> courseInformation = DatabaseMethods.GetCourseContents(whoIsTheUser);
        courses = new ArrayList<>();

        mondayLectures();
        tuesdayLectures();
        wednesdayLectures();
        thursdayLectures();
        fridayLectures();

        CourseAdapter adapter = new CourseAdapter(courses);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);




    }


    // Boş constructor ile günü oluştur sonra for döngüsü ile dersleri al.
    // type 1 = Monday
    // type 2 = Tuesday
    // type 3 = Wednesday
    // type 4 = Thursday
    // type 5 = Friday
    // type 0 = Ders ekleme.
    private void mondayLectures()
    {
        // Create the date with empty constructor
        courses.add(new Courses("", "", "", "", "", "MONDAY",1));

        // Get courses
        for(int i = 0; i < 1; i++)
        {
            courses.add(new Courses("", "", "", "", "", "MONDAY",0));
        }
    }

    private void tuesdayLectures()
    {
        // Create the date with empty constructor
        courses.add(new Courses("", "", "", "", "", "TUESDAY",2));
        // Get courses
        for(int i = 0; i < 4; i++)
        {
            courses.add(new Courses("", "", "", "", "", "TUESDAY",0));
        }
    }

    private void wednesdayLectures()
    {
        // Create the date with empty constructor
        courses.add(new Courses("", "", "", "", "", "WEDNESDAY",3));

        // Get courses
        for(int i = 0; i < 7; i++)
        {
            courses.add(new Courses("", "", "", "", "", "WEDNESDAY",0));
        }
    }
    private void thursdayLectures()
    {
        // Create the date with empty constructor
        courses.add(new Courses("", "", "", "", "", "THURSDAY",4));

        // Get courses
        for(int i = 0; i < 1; i++)
        {
            courses.add(new Courses("", "", "", "", "", "THURSDAY",0));
        }
    }

    private void fridayLectures()
    {
        // Create the date with empty constructor
        courses.add(new Courses("", "", "", "", "", "FRIDAY",5));

        // Get courses
        for(int i = 0; i < 2; i++)
        {
            courses.add(new Courses("", "", "", "", "", "FRIDAY",0));
        }
    }


}
