package com.vesit.paddy_ver1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {

    //    ImageView imageView;
    private ImageButton buttonAbout;
    private Button buttonGallery;
    private Button buttonCamera;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri ;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        //imageView = (ImageView) findViewById(R.id.ImageGallery);
        buttonGallery = findViewById(R.id.btnGallery);

        buttonGallery.setOnClickListener(v -> openGallery());

        buttonCamera = findViewById(R.id.btnCamera);

        buttonCamera.setOnClickListener(v -> openCamera());

        buttonAbout = findViewById(R.id.btnInfo);

        buttonAbout.setOnClickListener(v -> openAbout());


    }

    private void openGallery(){

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            //imageView.setImageURI(imageUri);

            openGalleryActivity();
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            openGalleryActivity(imageBitmap);
        }
    }

    public void openGalleryActivity() {
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra("ImageUri", imageUri);
        startActivity(intent);
    }

    public void openGalleryActivity(Bitmap imageBitmap) {
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra("ImageBitmap", imageBitmap);
        startActivity(intent);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "You need a camera app to perform this action",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void openAbout(){
        Intent aboutPage = new Intent(this, AboutActivity.class);
        startActivity(aboutPage);
    }

}
