package com.seniorproject.uninet.uninet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.seniorproject.uninet.uninet.Adapters.DiningListAdapter;
import com.seniorproject.uninet.uninet.ConstructorClasses.Lunch;
import com.seniorproject.uninet.uninet.DatabaseClasses.DatabaseMethods;
import com.seniorproject.uninet.uninet.DatabaseClasses.LunchSchedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaany on 27.02.2018.
 */

public class DiningActivity extends AppCompatActivity {

    ListView diningList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);

        // Declaration
        diningList = findViewById(R.id.dining_list);

        List<LunchSchedule> lunchSchedules = DatabaseMethods.GetLunchSchedule();
        ArrayList<Lunch> lunches = new ArrayList<>();


        for (int i = 0; i < lunchSchedules.size(); i++)
        {
            lunches.add(new Lunch(this, lunchSchedules.get(i).food));
        }

        DiningListAdapter diningListAdapter = new DiningListAdapter(this, R.layout.dining_list_template, lunches);
        diningList.setAdapter(diningListAdapter);
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
