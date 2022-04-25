package com.vesit.paddy_ver1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    Bitmap img;
    Classifier classifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        Button processButton = findViewById(R.id.btnProcess);
        ImageView imageView = findViewById(R.id.ImageGallery);
        Intent intent = getIntent();


        if(intent.hasExtra("ImageUri")) {
            Uri imageUri = intent.getParcelableExtra("ImageUri");
            imageView.setImageURI(imageUri);
            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(intent.hasExtra("ImageBitmap")) {
            Bitmap imageBitmap = intent.getParcelableExtra("ImageBitmap");
            imageView.setImageBitmap(imageBitmap);
            img = imageBitmap;
        }

        processButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            img = Bitmap.createScaledBitmap(img, 224, 224, true);
            List<Classifier.Recognition> resultlist = classifier.recognizeImage(img);

            float confidence = resultlist.get(0).confidence;

            if (confidence>0.80f){
                openResults(resultlist.get(0).title.split(" ")[1], img);

            }
            else {
                openClickPictureAgain(img);
            }
            progressBar.setVisibility(View.GONE);

        });

        try {
            classifier = new Classifier(getAssets(), "model_unquant.tflite", "labels.txt", 224);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void openClickPictureAgain(Bitmap img){
        Intent intent = new Intent(this,BlurryImageActivity.class);

        Bundle extras = new Bundle();
        extras.putParcelable("ImageBitmap",img);

        intent.putExtras(extras);
        startActivity(intent);
        finish();
    }

    private void openResults(String result,Bitmap img){
        Intent intent = new Intent(this,ResultsActivity.class);

        Bundle extras = new Bundle();
        extras.putString("ResultDisease",result);
        extras.putParcelable("ImageBitmap",img);

        intent.putExtras(extras);
        startActivity(intent);
        finish();
    }
}