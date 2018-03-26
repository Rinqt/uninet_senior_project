package com.seniorproject.uninet.uninet;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kaany on 6.03.2018.
 */

public class SessionChecker {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public SessionChecker(Context context) {
        prefs = context.getSharedPreferences("stayLoggedIn", Context.MODE_PRIVATE);
        editor = prefs.edit();
        this.context = context;
    }

    public void setUserLoggedIn(boolean userLoggedIn)
    {
        editor.putBoolean("UserLoggedIn", userLoggedIn);
        editor.commit();
    }

    public boolean loggedIn()
    {
        return prefs.getBoolean("UserLoggedIn", false);
    }
}
