package com.example.user.myapplication;

import android.app.Activity;

import java.net.MalformedURLException;
import java.net.URL;

public class InsertRegiData extends PostRequest {
    Activity activity;

    public InsertRegiData(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    protected void onPreExecute() {
        String serverURLStr = "http://13.124.210.148:3000/handleRegi";
        try {
            url = new URL(serverURLStr + "/insert");
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
