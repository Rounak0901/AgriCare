package com.vesit.paddy_ver1;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    /*CarouselView carouselView;
    int[] carouselImages = {R.drawable.vesit_campus, R.drawable.vesit_campus1, R.drawable.vesathon};
    */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_page);

        /*carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(carouselImages.length);

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(carouselImages[position]);
            }
        };*/

    }
}
