package com.example.user.myapplication;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class InsertCommentData extends PostRequest {
    Activity activity;

    public InsertCommentData(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    protected void onPreExecute() {
        String serverURLStr = MainActivity.SERVER_ADDRESS;
        try {
            url = new URL(serverURLStr + "/comment/insert");
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected void onPostExecute(String jsonString) {
        if ( jsonString == null ) {
            Toast.makeText(activity.getApplicationContext(), "댓글 작성 오류: jsonString is null", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if ( jsonObject.getBoolean("success") ) {
                Toast.makeText(activity.getApplicationContext(), "작성 완료", Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(activity.getApplicationContext(), "댓글 작성 오류: fail on web", Toast.LENGTH_LONG).show();

        }
        catch(JSONException e) {
            Toast.makeText(activity.getApplicationContext(), "댓글 작성 오류: jsonException", Toast.LENGTH_LONG).show();
            return;
        }
    }

}
