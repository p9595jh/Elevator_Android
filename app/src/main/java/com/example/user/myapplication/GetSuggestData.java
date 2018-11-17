package com.example.user.myapplication;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetSuggestData extends GetRequest {
    Activity activity;

    public GetSuggestData(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        String serverURLStr = "http://13.124.210.148:3000/suggest";
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
        ArrayList<Suggest> arrayList = getArrayListFromJSONString(jsonString);

//        ArrayAdapter adapter = new ArrayAdapter(activity,
//                android.R.layout.simple_list_item_1,
//                arrayList.toArray());
        SuggestAdapter adapter = new SuggestAdapter(arrayList, activity, R.layout.suggestlistview);
        ListView listView = activity.findViewById(R.id.suggestlist);
        listView.setAdapter(adapter);
        listView.setDividerHeight(10);
    }

    public ArrayList<Suggest> getArrayListFromJSONString(String jsonString) {
        ArrayList<Suggest> output = new ArrayList<>();
        try {

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                Suggest suggest = new Suggest(0, jsonObject.getString("id"),
                        jsonObject.getString("nickname"),
                        jsonObject.getString("comment"),
                        jsonObject.getString("writedate"),
                        jsonObject.getInt("num"));

                output.add(suggest);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return output;
    }
}
