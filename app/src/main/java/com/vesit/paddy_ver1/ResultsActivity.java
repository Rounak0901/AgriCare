package com.vesit.paddy_ver1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    TextView diseaseType;
    ImageView plantImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        diseaseType = findViewById(R.id.txtDiseaseType);
        plantImage = findViewById(R.id.imgResults);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        String txt = extras.getString("ResultDisease");
        Bitmap img = extras.getParcelable("ImageBitmap");

        plantImage.setImageBitmap(img);
        diseaseType.setText(txt);

    }
}
