package com.example.user.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);

        Button button = (Button) findViewById(R.id.register_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText id = (EditText) findViewById(R.id.register_id);
                EditText pw = (EditText) findViewById(R.id.register_password);
                EditText email = (EditText) findViewById(R.id.register_email);
                EditText nickname = (EditText) findViewById(R.id.register_nickname);
                EditText genre = (EditText) findViewById(R.id.register_genre);
                EditText introduction = (EditText) findViewById(R.id.register_introduce);

                EditText et[] = { id, pw, email, nickname };
                for (int i=0; i<et.length; i++) {
                    if ( et[i].getText().toString().trim().isEmpty() ) {
                        Toast.makeText(RegisterActivity.this, "You have to fill the fields with *", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", id.getText().toString());
                    jsonObject.put("password", pw.getText().toString());
                    jsonObject.put("email", email.getText().toString());
                    jsonObject.put("nickname", nickname.getText().toString());
                    jsonObject.put("genre", genre.getText().toString());
                    jsonObject.put("intro", introduction.getText().toString());
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
                new InsertRegiData(RegisterActivity.this).execute(jsonObject);
            }
        });
    }

}
