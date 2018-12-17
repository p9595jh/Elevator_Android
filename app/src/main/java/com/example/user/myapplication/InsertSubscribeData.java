package com.example.user.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class InsertSubscribeData extends PostRequest {
    Activity activity;

    public InsertSubscribeData(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    protected void onPreExecute() {
        String serverURLStr = MainActivity.SERVER_ADDRESS;
        try {
            url = new URL(serverURLStr + "/ajax/subscribeboard");
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
            String msg = jsonObject.getString("message");
            if ( msg.equals("duplicate") ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("구독");
                builder.setMessage("이미 구독하셨습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("구독");
                builder.setMessage("구독 완료")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        catch(JSONException e) {
            Toast.makeText(activity.getApplicationContext(), "오류: jsonException", Toast.LENGTH_LONG).show();
            return;
        }
    }
}
