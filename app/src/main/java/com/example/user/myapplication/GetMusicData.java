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

public class GetMusicData extends GetRequest {

    Activity activity;

    public GetMusicData(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        String serverURLStr = MainActivity.SERVER_ADDRESS;
        try {
            url = new URL(serverURLStr+"/music/get-data");  // http://serverURLStr/get-data
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if (jsonString == null)
            return;
        ArrayList<Music> arrayList = getArrayListFromJSONString(jsonString);

        final MusicAdapter adapter = new MusicAdapter(arrayList, activity, R.layout.freelistview);
        ListView listView = activity.findViewById(R.id.freelist);
        listView.setAdapter(adapter);
        listView.setDividerHeight(10);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {
                Intent intent = new Intent(activity.getApplicationContext(), ContentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("type", "music");
                intent.putExtra("num", ((Music) adapter.getItem(position)).getNum());
                intent.putExtra("title", ((Music) adapter.getItem(position)).getTitle());
                intent.putExtra("id", ((Music) adapter.getItem(position)).getId());
                intent.putExtra("date", ((Music) adapter.getItem(position)).getDate());
                intent.putExtra("nickname", ((Music) adapter.getItem(position)).getNickname());
                intent.putExtra("content", ((Music) adapter.getItem(position)).getContent());
                intent.putExtra("hit", ((Music) adapter.getItem(position)).getHit());
                intent.putExtra("grade", ((Music) adapter.getItem(position)).getGrade());
                intent.putExtra("comment", ((Music) adapter.getItem(position)).getComment());
                intent.putExtra("gradeby", ((Music) adapter.getItem(position)).getGradeby());
                intent.putExtra("audio", ((Music) adapter.getItem(position)).getAudio());
                activity.startActivity(intent);
            }
        });
    }

    public ArrayList<Music> getArrayListFromJSONString(String jsonString) {
        ArrayList<Music> output = new ArrayList<>();
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
                JSONArray gradebyArray = (JSONArray) jsonObject.get("gradeby");
                GradeByList gradeByList = new GradeByList();
                for (int j=0; j<gradebyArray.length(); j++) {
                    gradeByList.add((String) gradebyArray.get(j));
                }

                Music music = new Music(
                        jsonObject.getString("id"),
                        jsonObject.getString("nickname"),
                        jsonObject.getString("title"),
                        jsonObject.getString("content"),
                        jsonObject.getString("tag"),
                        jsonObject.getInt("grade"),
                        jsonObject.getBoolean("boardRequest"),
                        jsonObject.getString("writedate"),
                        jsonObject.getInt("hit"),
                        jsonObject.getInt("num"),
                        jsonObject.getString("audio"),
                        commentList,
                        gradeByList
                );

                output.add(music);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return output;
    }
}
