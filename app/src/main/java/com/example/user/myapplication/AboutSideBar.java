package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AboutSideBar {
    private static Bitmap bitmap;

    public static void init(final Activity activity) {
        int sources[] = { R.id.sidebar_free, R.id.sidebar_music, R.id.sidebar_suggest, R.id.sidebar_sub };
        final Class<?> c[] = { FreeActivity.class, MusicActivity.class, SuggestActivity.class, SubActivity.class };
        TextView textView[] = new TextView[sources.length];
        for (int i=0; i<textView.length; i++) {
            textView[i] = activity.findViewById(sources[i]);
            final int $i = i;
            textView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity.getApplicationContext(), c[$i]);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                }
            });
        }

        final Button login = activity.findViewById(R.id.sidebar_button);
        Button qrCheck = activity.findViewById(R.id.sidebar_qr_check);
        Button qrPrint = activity.findViewById(R.id.sidebar_qr_print);
        Button regi = activity.findViewById(R.id.sidebar_register);
        final TextView nickname = activity.findViewById(R.id.sidebar_nickname);
        final TextView date = activity.findViewById(R.id.sidebar_date);
        ImageView imageView = activity.findViewById(R.id.sidebar_image);



        final UserLoginData userLoginData = UserLoginData.getInstance();
        if ( UserLoginData.didLogin() ) {
            if ( userLoginData.getBoardRequest() != 2 )
                qrCheck.setVisibility(View.GONE);

            qrCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity.getApplicationContext(), QRcodeCheckActivity.class);
                    activity.startActivity(intent);
                }
            });
            qrPrint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity.getApplicationContext(), QRcodePrintActivity.class);
                    activity.startActivity(intent);
                }
            });

            regi.setVisibility(View.GONE);
            nickname.setText(userLoginData.getNickname() + "님, 반갑습니다");
            date.setText("가입일: " + userLoginData.getJoindate());
            login.setText("로그아웃");
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userLoginData.setId(null);
                    userLoginData.setNickname(null);
                    userLoginData.setJoindate(null);
                    userLoginData.setGenre(null);
                    userLoginData.setIntroduction(null);

                    login.setText("로그인");
                    nickname.setText("로그인이 필요합니다.");
                    date.setText("");

                    Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                }
            });

            Thread th = new Thread() {
                public void run() {
                    try {
                        URL url = new URL(MainActivity.SERVER_ADDRESS + "/images/profileimages/" + userLoginData.getId());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                    }
                    catch(MalformedURLException e) {
                        e.printStackTrace();
                    }
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            th.start();
            try {
                th.join();
                imageView.setImageBitmap(bitmap);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            qrCheck.setVisibility(View.GONE);
            qrPrint.setVisibility(View.GONE);
            date.setText("");
            login.setText("로그인");
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                }
            });
            regi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity.getApplicationContext(), RegisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                }
            });
        }
    }

}
