package com.example.user.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        final Button button = (Button) findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText id = findViewById(R.id.login_id);
                EditText pw = findViewById(R.id.login_password);
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("id", id.getText().toString());
                    jsonObject.put("pw", pw.getText().toString());
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
                new CheckLoginData(LoginActivity.this).execute(jsonObject);

                if ( UserLoginData.didLogin() ) {
                    id.setEnabled(false);
                    pw.setEnabled(false);
                    button.setEnabled(false);

                    UserLoginData userLoginData = UserLoginData.getInstance();
                    StringBuilder sb = new StringBuilder();
                    sb.append(userLoginData.getId());
                    sb.append(", ");
                    sb.append(userLoginData.getNickname());
                    TextView textView = (TextView) findViewById(R.id.login_title);
                    textView.setText(sb.toString());
                }
            }
        });
    }

}
