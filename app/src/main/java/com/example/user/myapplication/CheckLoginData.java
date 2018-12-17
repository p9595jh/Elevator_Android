package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CheckLoginData extends PostRequest {
    Activity activity;

    public CheckLoginData(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    protected void onPreExecute() {
        String serverURLStr = MainActivity.SERVER_ADDRESS;
        try {
            url = new URL(serverURLStr + "/login/checklogin");
        }
        catch(MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected void onPostExecute(String jsonString) {
        if ( jsonString == null ) {
            Toast.makeText(activity.getApplicationContext(), "로그인 오류", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if ( jsonObject.getString("id").isEmpty() ) {
                Toast.makeText(activity.getApplicationContext(), "로그인 오류", Toast.LENGTH_LONG).show();
                return; // login fail
            }

            UserLoginData userLoginData = UserLoginData.getInstance();
            userLoginData.setId(jsonObject.getString("id"));
            userLoginData.setNickname(jsonObject.getString("nickname"));
            userLoginData.setJoindate(jsonObject.getString("joindate"));
            userLoginData.setGenre(jsonObject.getString("genre"));
            userLoginData.setIntroduction(jsonObject.getString("introduction"));
            userLoginData.setStop(jsonObject.getBoolean("stop"));
            userLoginData.setEmail(jsonObject.getString("email"));
            userLoginData.setPw(jsonObject.getString("pw"));
            userLoginData.setLive(jsonObject.getString("live"));

            ArrayList<String> list = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) jsonObject.get("subscribes");
            for (int i=0; i<jsonArray.length(); i++)
                list.add((String) jsonArray.get(i));

            userLoginData.setSubscribes(list);
            userLoginData.setBoardRequest(jsonObject.getInt("boardRequest"));

            EditText id = activity.findViewById(R.id.login_id);
            EditText pw = activity.findViewById(R.id.login_password);
            Button button = activity.findViewById(R.id.login_button);
            id.setEnabled(false);
            pw.setEnabled(false);
            button.setEnabled(false);

            TextView textView = activity.findViewById(R.id.login_title);
            textView.setText(userLoginData.getId());

            Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
            activity.startActivity(intent);
        }
        catch(JSONException e) {
            Toast.makeText(activity.getApplicationContext(), "로그인 오류", Toast.LENGTH_LONG).show();
            return;
        }
    }
}
