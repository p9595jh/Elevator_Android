package com.example.user.myapplication;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class CheckLoginData extends PostRequest {
    Activity activity;

    public CheckLoginData(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    protected void onPreExecute() {
        String serverURLStr = "http://13.124.210.148:3000/login";
        try {
            url = new URL(serverURLStr + "/checklogin");
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected void onPostExecute(String jsonString) {
        if ( jsonString == null ) return;

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if ( jsonObject.getString("id").isEmpty() ) return; // login fail

            UserLoginData userLoginData = UserLoginData.getInstance();
            userLoginData.setId(jsonObject.getString("id"));
            userLoginData.setNickname(jsonObject.getString("nickname"));
            userLoginData.setJoindate(jsonObject.getString("joindate"));
            userLoginData.setGenre(jsonObject.getString("genre"));
            userLoginData.setIntroduction(jsonObject.getString("introduction"));
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
