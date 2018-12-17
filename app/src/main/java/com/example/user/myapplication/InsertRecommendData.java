package com.example.user.myapplication;

import android.app.Activity;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class InsertRecommendData extends PostRequest {
    Activity activity;

    public InsertRecommendData(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    protected void onPreExecute() {
        String serverURLStr = MainActivity.SERVER_ADDRESS;
        try {
            url = new URL(serverURLStr + "/ajax");
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected void onPostExecute(String jsonString) {
        if ( jsonString == null ) {
            Toast.makeText(activity.getApplicationContext(), "오류: jsonString is null", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String msg = jsonObject.getString("msg");
            if ( msg.equals("duplicate") ) {
                Toast.makeText(activity.getApplicationContext(), "이미 추천하셨습니다", Toast.LENGTH_LONG).show();
                return;
            }
            else {
                int recommend = jsonObject.getInt("recommend");
                Button button = activity.findViewById(R.id.content_recommend);
                button.setText("추천 " + recommend);
            }
        }
        catch(JSONException e) {
            Toast.makeText(activity.getApplicationContext(), "오류: jsonException", Toast.LENGTH_LONG).show();
            return;
        }
    }

}
