package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subpage);

        UserLoginData userLoginData = UserLoginData.getInstance();
        if ( !userLoginData.didLogin() ) {
            LinearLayout userLayout = (LinearLayout) findViewById(R.id.subscribe_userlayout);
            userLayout.setVisibility(View.GONE);
        }
        else {
            final UserLoginData.SubscribesAdapter adapter = userLoginData.getSubscribesAdapter(SubActivity.this, R.layout.subscribecontentlistview);
            ListView listView = findViewById(R.id.subscribe_subcontent_list);
            listView.setAdapter(adapter);
            listView.setDividerHeight(10);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), SubBoardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("boardType", (String) adapter.getItem(position));
                    startActivity(intent);
                }
            });
        }

        new GetSubData(SubActivity.this).execute();

        AboutSideBar.init(this);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetSubData(SubActivity.this).execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private long pressedTime = 0;
    @Override
    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            Toast.makeText(this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }
            else {
                super.onBackPressed();
                finishAffinity();
            }
        }
    }
}
