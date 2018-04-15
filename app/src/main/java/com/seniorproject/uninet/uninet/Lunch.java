package com.seniorproject.uninet.uninet;

public class Lunch {

    String lunchName;

    public Lunch(String lunch) {
        this.lunchName = lunch;
    }

    public String getLunchName() {
        if (lunchName != null)
        {
            return lunchName;
        } else
            return  "No Luch Found.";
    }

    public void setLunchName(String lunchName) {
        this.lunchName = lunchName;
    }
}
