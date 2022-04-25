package com.vesit.paddy_ver1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class FirstActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Uri imageUri ;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private int CAMERA_PERMISSION_CODE = 1;

    private Toolbar toolbar;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        setupHyperlink();

        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayout);
        toolbar = findViewById(R.id.toolbar);
        setUpToolbar();

        CardView buttonGallery = findViewById(R.id.btnGallery);

        buttonGallery.setOnClickListener(v -> openGallery());

        CardView buttonCamera = findViewById(R.id.btnCamera);

        buttonCamera.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(FirstActivity.this,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                Intent i = new Intent(this, SnapTipsPopUp.class);
                startActivity(i);

            } else {
                requestCameraPermission();
            }
        });

    }

    private void setupHyperlink() {
        TextView linkTextView = findViewById(R.id.tutorialVideoLink);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to capture Images.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(FirstActivity.this,
                                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
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

    public void openCamera() {
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

    private void setUpToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AgriCare");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                logout();
                return true;
            case R.id.aboutApp:
                openAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logout(){
        auth.signOut();
        Intent intent = new Intent(FirstActivity.this, PhoneLoginActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        finish();
    }
}
