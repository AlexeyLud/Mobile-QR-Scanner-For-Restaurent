package com.example.restaurent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {

    static int progress;
    ProgressBar progressBar;
    int progressStatus = 0;
    int MaxValue = 100;
    int f = 33, s = 50;
    Handler handler = new Handler();
    TextView res;
    TextView pers;
    Button backToMain, genQrCode;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        progress = 0;
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setMax(MaxValue);
        res = (TextView)findViewById(R.id.resInfo);
        pers = (TextView)findViewById(R.id.persent);
        backToMain = (Button) findViewById(R.id.backMain);
        genQrCode = (Button) findViewById(R.id.genQR);
        img = (ImageView) findViewById(R.id.qrGenImage);

        if(progressStatus != 100){
            genQrCode.setVisibility(View.INVISIBLE);
        }

        ArrayList<String> prod = ZakazActivity.products;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus <= MaxValue) {
                    if(progressStatus <= f) {
                        res.setText(R.string.first);
                        progressStatus = doSomeWork();
                        String p = String.valueOf(progressStatus) + "%";
                        pers.setText(p);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(progressStatus);
                            }
                        });
                    }
                    else if(progressStatus > f && progressStatus <= s){
                        res.setText(R.string.second);
                        progressStatus = doSomeWork();
                        String p = String.valueOf(progressStatus) + "%";
                        pers.setText(p);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(progressStatus);
                            }
                        });
                    }
                    else if(progressStatus > s && progressStatus < MaxValue){
                        res.setText(R.string.third);
                        progressStatus = doSomeWork();
                        String p = String.valueOf(progressStatus) + "%";
                        pers.setText(p);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(progressStatus);
                            }
                        });
                    }
                    else if(progressStatus == MaxValue){
                        res.setText(R.string.fourth);
                        runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                if(progressStatus == 100){
                                    genQrCode.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                }

            }

            private int doSomeWork(){
                try{
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                return ++progress;
            }
        }).start();

        genQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itog = "Zakaz #" + String.valueOf(System.currentTimeMillis()) + "\n";
                for(String p : prod){
                    String[] str = new String[p.length()];
                    int i = 0;
                    for(String res : p.split("\n")){
                        str[i] = res;
                        if(i % 2 == 0){
                            itog += str[i] + "\n";
                        }
                        i++;
                    }
                }
                System.out.println("itog = " + itog);
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(itog, BarcodeFormat.QR_CODE, 300, 300);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    img.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}