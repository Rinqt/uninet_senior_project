package com.seniorproject.uninet.uninet.ConstructorClasses;

import android.content.Context;
import android.widget.Toast;

public class Lunch {

    String lunchName;
    Context mContext;

    public Lunch(Context mContext, String lunch) {
        this.lunchName = lunch;
        this.mContext = mContext;
    }

    public String getLunchName() {
        if (lunchName != null)
        {
            return lunchName;
        } else
        {
            Toast.makeText(mContext, "No Lunch Found.", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    public void setLunchName(String lunchName) {
        this.lunchName = lunchName;
    }
}
