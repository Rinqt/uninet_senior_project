package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.seniorproject.uninet.uninet.Adapters.CourseAdapter;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.TimeTableCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaany on 27.02.2018.
 */

public class TimeTableActivity extends AppCompatActivity {


    String whoIsTheUser;
    ArrayList<Courses> courses;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO DENEME LAYOUT UN İSMİNİ DEĞİŞTİR
        setContentView(R.layout.deneme_layout);

        whoIsTheUser = LoggedInUser.UserId;

       // List<CourseContent> courseInformation = DatabaseMethods.GetCourseContents(whoIsTheUser);
        courses = new ArrayList<>();

        mondayLectures();
        tuesdayLectures();
        wednesdayLectures();
        thursdayLectures();
        fridayLectures();

        CourseAdapter adapter = new CourseAdapter(this, courses);

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
        List<TimeTableCourse> courseInfo = DatabaseMethods.GetUserTimeTable(whoIsTheUser);
        // Create the date with empty constructor
        courses.add(new Courses("", "", "", "", "", "", "MONDAY",1));


        // Get courses
        for(int i = 0; i < courseInfo.size(); i++)
        {
            if(courseInfo.get(i).weekDay.equals("1"))
            {
                String startTime = courseInfo.get(i).startTime.split(":")[0] + ":" + courseInfo.get(i).startTime.split(":")[1];
                String endTime = courseInfo.get(i).endTime.split(":")[0] + ":" + courseInfo.get(i).endTime.split(":")[1];

                courses.add(new Courses(startTime + " - " + endTime,
                        courseInfo.get(i).courseCode + " (" + courseInfo.get(i).branchNumber + ")",
                        courseInfo.get(i).courseName,
                        courseInfo.get(i).classroom + " (" + courseInfo.get(i).quota + ")",
                        courseInfo.get(i).name,
                        courseInfo.get(i).theoryClass,
                        courseInfo.get(i).weekDay,
                        0));
            }
        }
    }

    private void tuesdayLectures()
    {
        List<TimeTableCourse> courseInfo = DatabaseMethods.GetUserTimeTable(whoIsTheUser);

        // Create the date with empty constructor
        courses.add(new Courses("", "", "", "", "", "","TUESDAY",2));

        // Get courses
        for(int i = 0; i < courseInfo.size(); i++)
        {
            if(courseInfo.get(i).weekDay.equals("2"))
            {
                String startTime = courseInfo.get(i).startTime.split(":")[0] + ":" + courseInfo.get(i).startTime.split(":")[1];
                String endTime = courseInfo.get(i).endTime.split(":")[0] + ":" + courseInfo.get(i).endTime.split(":")[1];

                courses.add(new Courses(startTime + " - " + endTime,
                        courseInfo.get(i).courseCode + " (" + courseInfo.get(i).branchNumber + ")",
                        courseInfo.get(i).courseName,
                        courseInfo.get(i).classroom + " (" + courseInfo.get(i).quota + ")",
                        courseInfo.get(i).name,
                        courseInfo.get(i).theoryClass,
                        courseInfo.get(i).weekDay,
                        0));
            }
        }
    }

    private void wednesdayLectures()
    {
        List<TimeTableCourse> courseInfo = DatabaseMethods.GetUserTimeTable(whoIsTheUser);

        // Create the date with empty constructor
        courses.add(new Courses("", "", "", "", "", "","WEDNESDAY",3));

        // Get courses
        for(int i = 0; i < courseInfo.size(); i++)
        {
            if(courseInfo.get(i).weekDay.equals("3"))
            {
                String startTime = courseInfo.get(i).startTime.split(":")[0] + ":" + courseInfo.get(i).startTime.split(":")[1];
                String endTime = courseInfo.get(i).endTime.split(":")[0] + ":" + courseInfo.get(i).endTime.split(":")[1];

                courses.add(new Courses(startTime + " - " + endTime,
                        courseInfo.get(i).courseCode + " (" + courseInfo.get(i).branchNumber + ")",
                        courseInfo.get(i).courseName,
                        courseInfo.get(i).classroom + " (" + courseInfo.get(i).quota + ")",
                        courseInfo.get(i).name,
                        courseInfo.get(i).theoryClass,
                        courseInfo.get(i).weekDay,
                        0));
            }
        }
    }
    private void thursdayLectures()
    {
        List<TimeTableCourse> courseInfo = DatabaseMethods.GetUserTimeTable(whoIsTheUser);

        // Create the date with empty constructor
        courses.add(new Courses("", "", "", "", "", "", "THURSDAY",4));

        // Get courses
        for(int i = 0; i < courseInfo.size(); i++)
        {
            if(courseInfo.get(i).weekDay.equals("4"))
            {
                String startTime = courseInfo.get(i).startTime.split(":")[0] + ":" + courseInfo.get(i).startTime.split(":")[1];
                String endTime = courseInfo.get(i).endTime.split(":")[0] + ":" + courseInfo.get(i).endTime.split(":")[1];

                courses.add(new Courses(startTime + " - " + endTime,
                        courseInfo.get(i).courseCode + " (" + courseInfo.get(i).branchNumber + ")",
                        courseInfo.get(i).courseName,
                        courseInfo.get(i).classroom + " (" + courseInfo.get(i).quota + ")",
                        courseInfo.get(i).name,
                        courseInfo.get(i).theoryClass,
                        courseInfo.get(i).weekDay,
                        0));
            }
        }
    }

    private void fridayLectures()
    {
        List<TimeTableCourse> courseInfo = DatabaseMethods.GetUserTimeTable(whoIsTheUser);

        // Create the date with empty constructor
        courses.add(new Courses("", "", "", "", "", "", "FRIDAY",5));

        // Get courses
        for(int i = 0; i < courseInfo.size(); i++)
        {
            if(courseInfo.get(i).weekDay.equals("5"))
            {
                String startTime = courseInfo.get(i).startTime.split(":")[0] + ":" + courseInfo.get(i).startTime.split(":")[1];
                String endTime = courseInfo.get(i).endTime.split(":")[0] + ":" + courseInfo.get(i).endTime.split(":")[1];

                courses.add(new Courses(startTime + " - " + endTime,
                        courseInfo.get(i).courseCode + " (" + courseInfo.get(i).branchNumber + ")",
                        courseInfo.get(i).courseName,
                        courseInfo.get(i).classroom + " (" + courseInfo.get(i).quota + ")",
                        courseInfo.get(i).name,
                        courseInfo.get(i).theoryClass,
                        courseInfo.get(i).weekDay,
                        0));
            }
        }
    }

}
