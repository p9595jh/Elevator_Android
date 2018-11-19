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
        String serverURLStr = MainActivity.SERVER_ADDRESS;
        try {
            url = new URL(serverURLStr + "/handleRegi/insert");
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
