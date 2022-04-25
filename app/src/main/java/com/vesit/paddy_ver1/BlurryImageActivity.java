package com.vesit.paddy_ver1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class BlurryImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blurry_image);

        ImageView image = findViewById(R.id.imgResults);
        image.setImageBitmap(getIntent().getExtras().getParcelable("ImageBitmap"));
    }
}