package com.example.user.myapplication;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetFreeData extends GetRequest {

    Activity activity;

    public GetFreeData(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        String serverURLStr = "http://13.124.210.148:3000/free";
        try {
            url = new URL(serverURLStr+"/get-data");  // http://serverURLStr/get-data
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if (jsonString == null)
            return;
        ArrayList<Free> arrayList = getArrayListFromJSONString(jsonString);

        FreeAdapter adapter = new FreeAdapter(arrayList, activity, R.layout.freelistview);
        ListView listView = activity.findViewById(R.id.freelist);
        listView.setAdapter(adapter);
        listView.setDividerHeight(10);
    }

    public ArrayList<Free> getArrayListFromJSONString(String jsonString) {
        ArrayList<Free> output = new ArrayList<>();
        try {

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                Free free = new Free(jsonObject.getString("title"),
                        jsonObject.getString("nickname"),
                        jsonObject.getString("writedate"));

                output.add(free);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return output;
    }
}
