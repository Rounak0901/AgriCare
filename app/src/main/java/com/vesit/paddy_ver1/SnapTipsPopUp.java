package com.vesit.paddy_ver1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class SnapTipsPopUp extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Uri imageUri ;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private int CAMERA_PERMISSION_CODE = 1;

//    private YouTubePlayerView youTubePlayerView;

    ImageButton popUpClose;
    TextView proceedToCam;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_tips_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8), (int)(height*0.65));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 10;

        getWindow().setAttributes(params);

//        youTubePlayerView = findViewById(R.id.activity_main_youtubePlayerView);
//        getLifecycle().addObserver(youTubePlayerView);
//
//        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                String videoId = getString(R.string.tutorial_video_id);
//                youTubePlayer.loadVideo(videoId, 0);
//            }
//        });

        popUpClose = findViewById(R.id.btnPopUpClose);
        proceedToCam = findViewById(R.id.txtProceedToCam);

        popUpClose.setOnClickListener(view -> finish());
        proceedToCam.setOnClickListener(view -> openCamera());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
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
        finish();
    }

    public void openGalleryActivity(Bitmap imageBitmap) {
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra("ImageBitmap", imageBitmap);
        startActivity(intent);
        finish();
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


}