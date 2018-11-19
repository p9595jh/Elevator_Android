package com.example.user.myapplication;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String SERVER_ADDRESS = "http://13.125.236.173:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.button1);
        Button register = (Button) findViewById(R.id.button2);
        Button free = (Button) findViewById(R.id.button3);
        Button suggest = (Button) findViewById(R.id.button4);
        Button free_detail = (Button) findViewById(R.id.button5);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        free.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FreeActivity.class);
                startActivity(intent);
            }
        });
        suggest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SuggestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 1111 ) {
            if ( resultCode == 1234 ) {
                TextView textView = (TextView) findViewById(R.id.login_state);
                String idvalue = data.getStringExtra("idvalue");
                textView.setText(idvalue);
            }
        }
    }
}
