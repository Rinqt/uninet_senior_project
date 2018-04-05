package com.seniorproject.uninet.uninet.DatabaseClasses;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatabaseClass extends AsyncTask<String, Void, String> {

    String status = null;

    @Override
    protected String doInBackground(String... connUrl) {
        HttpURLConnection conn = null;
        BufferedReader reader;

        try{
            final URL url = new URL(connUrl[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestMethod(connUrl[1]);
            int result = conn.getResponseCode();
            if(result == 200){
                InputStream input = new BufferedInputStream(conn.getInputStream());
                reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = reader.readLine()) != null){
                    status = line;
                }
            }
        }
        catch (Exception ex){

        }

        return status;
    }
}

