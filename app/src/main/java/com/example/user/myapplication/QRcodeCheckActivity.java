package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRcodeCheckActivity extends AppCompatActivity {
    private static Sub sub;

    Button QR_check;
    String QR_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcodecheckpage);

        new GetOneSubData(this, UserLoginData.getInstance().getId()).execute();

        QR_check = (Button) findViewById(R.id.qrcode_check_button);

        QR_check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new IntentIntegrator(QRcodeCheckActivity.this).initiateScan();
            }
        });

        AboutSideBar.init(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        QR_value = result.getContents();
        Toast.makeText(getApplicationContext(), QR_value, Toast.LENGTH_LONG).show();
        TextView textView = findViewById(R.id.qrcode_result);
        for (int i=0; i<sub.getLiveviwers().size(); i++) {
            if ( sub.getLiveviwers().get(i).getTicket().equals(QR_value) ) {
                textView.setText(sub.getLiveviwers().get(i).getId() + "님, 확인되었습니다.");
                return;
            }
        }
        textView.setText("일치하지 않는 티켓입니다.");
    }

    public static void setSub(Sub s) {
        sub = s;
    }

    public static Sub getSub() {
        return sub;
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
