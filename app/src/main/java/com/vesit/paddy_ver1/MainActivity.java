package com.vesit.paddy_ver1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen1);


        (new Handler()).postDelayed((Runnable)(new Runnable() {
            @Override
            public final void run() {
                Intent it = new Intent(MainActivity.this, SplashActivity2.class);
                MainActivity.this.startActivity(it);
                MainActivity.this.finish();
            }
        }), SPLASH_SCREEN_TIME_OUT);
    }
}