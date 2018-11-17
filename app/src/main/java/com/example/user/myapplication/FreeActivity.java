package com.example.user.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freepage);

        new GetFreeData(FreeActivity.this).execute();
    }

}
