package com.example.restaurent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;
import com.google.zxing.Result;
import java.util.ArrayList;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    int REQUEST_CAMERA = 0;
    ZXingScannerView scannerView;
    public static ArrayList<String> product = new ArrayList<>();
    public static int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    public void handleResult(Result result){

        AlertDialog.Builder addDialog = new AlertDialog.Builder(ScanActivity.this);
        addDialog.setTitle("Вы уверены что хотите добавить это блюдо?");
        addDialog.setIcon(R.drawable.bonic);
        addDialog.setCancelable(false);
        addDialog.setPositiveButton("Таки да!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = result.getText();
                String[] sstr = new String[str.length()];
                int i = 0;
                for(String res : str.split(" ")){
                    sstr[i] = res;
                    i++;
                }
                String name = sstr[0] + " " + sstr[1];
                int price = Integer.parseInt(sstr[3]);
                sum += price;
                String info = "ProductName: " + name + "\nPrice: " + String.valueOf(price) + "$";
                product.add(0, info);
                Toast.makeText(ScanActivity.this, "Блюдо успешно добавлено в ваш заказ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ScanActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        addDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onBackPressed();
            }
        });
        addDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ScanActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}