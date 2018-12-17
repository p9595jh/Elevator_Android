package com.example.user.myapplication;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    String imagePath = "";
    String musicPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writecontentpage);

        Intent intent = getIntent();
        final String type = intent.getStringExtra("type");

        final EditText title = findViewById(R.id.write_title);
        final EditText content = findViewById(R.id.write_content);
        Button imageButton = findViewById(R.id.write_image);
        Button musicButton = findViewById(R.id.write_music);

        Button write = findViewById(R.id.write_button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                imageIntent.setType("image/*");
                imageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivityForResult(imageIntent.createChooser(imageIntent, "Open"), 1);
                startActivityForResult(imageIntent, 1);
            }
        });

        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri;
                if ( new File(Environment.getExternalStorageDirectory().getPath() + "/Downloads/").isFile() )
                    uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Downloads/");
                else if ( new File(Environment.getExternalStorageDirectory().getPath() + "/Download/").isFile() )
                    uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Downloads/");
                else
                    uri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
                Intent musicIntent = new Intent();
                musicIntent.setDataAndType(uri, "audio/*");
                startActivityForResult(musicIntent, 2);
            }
        });

        final UserLoginData userLoginData = UserLoginData.getInstance();
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(new Date(System.currentTimeMillis()));

                    jsonObject.put("id", userLoginData.getId());
                    jsonObject.put("boardtype", type);
                    jsonObject.put("nickname", userLoginData.getNickname());
                    jsonObject.put("title", title.getText().toString());
                    jsonObject.put("content", content.getText().toString());
                    jsonObject.put("writedate", date);
                    jsonObject.put("image", imagePath);
                    jsonObject.put("audio", musicPath);
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
                new UploadBoard(WriteActivity.this).execute(jsonObject);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ( resultCode != RESULT_OK ) return;
        if ( requestCode == 1 ) {
            // have to get the absolute path of the file
            imagePath = getPath(data.getData());
            EditText content = findViewById(R.id.write_content);
            content.setText("상대경로:\n" + data.getDataString() + "\n절대경로:\n:" + imagePath + "\nPath:\n" + data.getData().getPath());

            TextView imageText = findViewById(R.id.write_image_name);
            imageText.setText(imagePath);

            ImageView imageView = findViewById(R.id.write_image_test);
            imageView.setImageURI(data.getData());
//            imageView.setImageURI(Uri.parse(imagePath));
        }
        else if ( requestCode == 2 ) {
            TextView musicText = findViewById(R.id.write_music_name);
            musicText.setText(data.getDataString());
            musicPath = data.getDataString();
        }
    }

    public String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        cursor.close();
        return path;
    }


}
