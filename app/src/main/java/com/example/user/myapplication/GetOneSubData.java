package com.example.user.myapplication;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetOneSubData extends GetRequest {
    Activity activity;
    String id;

    public GetOneSubData(Activity activity, String id) {
        super(activity);
        this.activity = activity;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        String serverURLStr = MainActivity.SERVER_ADDRESS;
        try {
            url = new URL(serverURLStr+"/subs/get-data");  // http://serverURLStr/get-data
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if (jsonString == null) return;
        Sub sub = getSubFromJSONString(jsonString);

        QRcodeCheckActivity.setSub(sub);

        StringBuilder sb = new StringBuilder();
        sb.append("제목: " + sub.getLivetitle());
        sb.append("\n날짜: " + sub.getLivedate());
        sb.append("\n장소: " + sub.getLivelocation());
        sb.append("\n인원: " + sub.getLivetickets().size());
        EditText editText = activity.findViewById(R.id.qrcode_content);
        editText.setText(sb.toString());
        Log.d("aboutSub", sb.toString());
    }

    public Sub getSubFromJSONString(String jsonString) {
        try {

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if ( jsonObject.getString("id").equals(id) ) {

                    // subscribes
                    Sub.Subscribes subscribes = new Sub.Subscribes();
                    JSONArray subscribeArray = (JSONArray) jsonObject.get("subscribes");
                    for (int j = 0; j < subscribeArray.length(); j++)
                        subscribes.add((String) subscribeArray.get(j));

                    // stops
                    Sub.Stops stops = new Sub.Stops();
                    JSONArray stopArray = (JSONArray) jsonObject.get("stops");
                    for (int j = 0; j < stopArray.length(); j++)
                        stops.add((String) stopArray.get(j));

                    // liveviewer
                    Sub.Liveviwers liveviwers = new Sub.Liveviwers();
                    JSONArray liveviewerArray = (JSONArray) jsonObject.get("liveviewer");
                    for (int j = 0; j < liveviewerArray.length(); j++) {
                        JSONObject o = (JSONObject) liveviewerArray.get(j);
                        liveviwers.add(new Sub.LiveviewerInfo(o.getString("id"), o.getString("ticket")));
                    }

                    // liveticket
                    Sub.Livetickets livetickets = new Sub.Livetickets();
                    JSONArray liveticketArray = (JSONArray) jsonObject.get("liveticket");
                    for (int j = 0; j < liveticketArray.length(); j++)
                        livetickets.add((String) liveticketArray.get(j));

                    // liveintro, livelocation, livedate, livetitle
                    String liveintro = jsonObject.getString("liveintro");
                    String livelocation = jsonObject.getString("livelocation");
                    String livedate = jsonObject.getString("livedate");
                    String livetitle = jsonObject.getString("livetitle");

                    Sub sub = new Sub(
                            jsonObject.getString("id"),
                            subscribes,
                            stops
                    );
                    sub.setLivetickets(livetickets);
                    sub.setLiveviwers(liveviwers);
                    sub.setLiveintro(liveintro);
                    sub.setLivelocation(livelocation);
                    sub.setLivedate(livedate);
                    sub.setLivetitle(livetitle);

                    return sub;
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return null;
    }
}
