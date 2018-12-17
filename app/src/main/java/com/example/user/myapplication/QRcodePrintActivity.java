package com.example.user.myapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRcodePrintActivity extends AppCompatActivity {

    ImageView QR_image;
    Button QR_print;
    String QR_value = UserLoginData.getInstance().getLive();
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcodeprintpage);

        AboutSideBar.init(this);

        QR_image = (ImageView) findViewById(R.id.qrcode_print_image);
        QR_print = (Button) findViewById(R.id.qrcode_print_button);

        QR_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !UserLoginData.didLogin() ) {
                    Toast.makeText(getApplicationContext(), "로그인을 해야 합니다", Toast.LENGTH_LONG).show();
                    return;
                }

                QR_value = UserLoginData.getInstance().getLive();
                if ( QR_value.isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "신청한 라이브가 없습니다!", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    UserLoginData userLoginData = UserLoginData.getInstance();
                    bitmap = generateRQCode(userLoginData.getLive());
                    QR_image.setImageBitmap(bitmap);
                }

            }
        });

    }

    public Bitmap generateRQCode(String contents) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            return toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 700, 700));
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
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