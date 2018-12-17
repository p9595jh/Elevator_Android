package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
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

public class GetSubBoardData extends GetRequest {
    Activity activity;
    String boardType;

    public GetSubBoardData(Activity activity, String boardType) {
        super(activity);
        this.activity = activity;
        this.boardType = boardType;
    }

    @Override
    protected void onPreExecute() {
        String serverURLStr = MainActivity.SERVER_ADDRESS;
        try {
            url = new URL(serverURLStr+"/subboard/get-data?type="+boardType);  // http://serverURLStr/get-data
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if (jsonString == null)
            return;
        ArrayList<Free> arrayList = getArrayListFromJSONString(jsonString);

        final FreeAdapter adapter = new FreeAdapter(arrayList, activity, R.layout.freelistview);
        ListView listView = activity.findViewById(R.id.freelist);
        listView.setAdapter(adapter);
        listView.setDividerHeight(10);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {
                Intent intent = new Intent(activity.getApplicationContext(), ContentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("type", boardType);
                intent.putExtra("num", ((Free) adapter.getItem(position)).getNum());
                intent.putExtra("title", ((Free) adapter.getItem(position)).getTitle());
                intent.putExtra("id", ((Free) adapter.getItem(position)).getId());
                intent.putExtra("date", ((Free) adapter.getItem(position)).getDate());
                intent.putExtra("nickname", ((Free) adapter.getItem(position)).getNickname());
                intent.putExtra("content", ((Free) adapter.getItem(position)).getContent());
                intent.putExtra("hit", ((Free) adapter.getItem(position)).getHit());
                intent.putExtra("recommend", ((Free) adapter.getItem(position)).getRecommend());
                intent.putExtra("comment", ((Free) adapter.getItem(position)).getComment());
                activity.startActivity(intent);
            }
        });
    }

    public ArrayList<Free> getArrayListFromJSONString(String jsonString) {
        ArrayList<Free> output = new ArrayList<>();
        try {

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                JSONArray commentArray = (JSONArray) jsonObject.get("comment");
                CommentList commentList = new CommentList();
                for (int j=0; j<commentArray.length(); j++) {
                    JSONObject each = (JSONObject) commentArray.get(j);
                    commentList.add(new Comment(
                            each.getInt("num"),
                            each.getString("id"),
                            each.getString("nickname"),
                            each.getString("writedate"),
                            each.getString("comment"),
                            0
                    ));
                }

                Free free = new Free(
                        jsonObject.getString("title"),
                        jsonObject.getString("id"),
                        jsonObject.getString("writedate"),
                        jsonObject.getInt("num"),
                        jsonObject.getString("nickname"),
                        jsonObject.getString("content"),
                        jsonObject.getInt("hit"),
                        jsonObject.getInt("recommend"),
                        commentList
                );

                output.add(free);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return output;
    }
}
