package com.example.restaurent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class InfoActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        imageView = (ImageView) findViewById(R.id.imageFood);
        Bitmap bp = (Bitmap)this.getIntent().getParcelableExtra("img_food");
        imageView.setImageBitmap(bp);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InfoActivity.this, ZakazActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}