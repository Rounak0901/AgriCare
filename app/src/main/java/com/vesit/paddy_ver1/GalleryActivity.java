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

import com.vesit.paddy_ver1.ml.ModelUnquant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class GalleryActivity extends AppCompatActivity {

    String results;
    Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        Button processButton = findViewById(R.id.btnProcess);
        ImageView imageView = findViewById(R.id.ImageGallery);
        Intent intent = getIntent();
        String tokens[] = new String[]{"Blast","Scald","Healthy"};

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

        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);

                img = Bitmap.createScaledBitmap(img, 224, 224, true);

                try {
                    ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);

                    TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                    tensorImage.load(img);
                    ByteBuffer byteBuffer = tensorImage.getBuffer();

                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    ModelUnquant.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Releases model resources if no longer used.
                    model.close();

                    float maxPossibility = outputFeature0.getFloatArray()[0];
                    int index=0;
                    int i=0;
                    for(i=0; i<outputFeature0.getFloatArray().length; i++){
                        if(outputFeature0.getFloatArray()[i]>maxPossibility){
                            maxPossibility = outputFeature0.getFloatArray()[i];
                            index = i;
                        }
                    }
                    String allResults = outputFeature0.getFloatArray()[0] + "\n " + outputFeature0.getFloatArray()[1] + "\n " + outputFeature0.getFloatArray()[2];
                    String str = tokens[index] + " " + outputFeature0.getFloatArray()[index] + "\n" + allResults;

                    openResults(str, img);

                } catch (IOException e) {
                    // TODO Handle the exception
                }

            }
        });
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