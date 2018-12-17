package com.example.user.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetSubData extends GetRequest {
    Activity activity;

    public GetSubData(Activity activity) {
        super(activity);
        this.activity = activity;
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
        if (jsonString == null)
            return;
        ArrayList<Sub> arrayList = getArrayListFromJSONString(jsonString);

        final SubAdapter adapter = new SubAdapter(arrayList, activity, R.layout.subcontentlistview);
        ListView listView = activity.findViewById(R.id.subcontent_list);
        listView.setAdapter(adapter);
        listView.setDividerHeight(10);

        if ( UserLoginData.didLogin() ) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View vClicked, final int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("구독");
                    builder.setMessage("구독하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("userid", UserLoginData.getInstance().getId());
                                        jsonObject.put("subid", ((Sub) adapter.getItem(position)).getId());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    new InsertSubscribeData(activity).execute(jsonObject);
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    public ArrayList<Sub> getArrayListFromJSONString(String jsonString) {
        ArrayList<Sub> output = new ArrayList<>();
        try {

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                Sub.Subscribes subscribes = new Sub.Subscribes();
                JSONArray subscribeArray = (JSONArray) jsonObject.get("subscribes");
                for (int j=0; j<subscribeArray.length(); j++)
                    subscribes.add((String) subscribeArray.get(j));
                Sub.Stops stops = new Sub.Stops();
                JSONArray stopArray = (JSONArray) jsonObject.get("stops");
                for (int j=0; j<stopArray.length(); j++)
                    stops.add((String) stopArray.get(j));

                Sub sub = new Sub(
                        jsonObject.getString("id"),
                        subscribes,
                        stops
                );

                output.add(sub);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return output;
    }
}
