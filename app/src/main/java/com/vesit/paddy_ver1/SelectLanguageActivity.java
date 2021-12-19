package com.vesit.paddy_ver1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SelectLanguageActivity extends AppCompatActivity {
    private Button buttonEnglish;
    private Button buttonHindi;
    private Button buttonMarathi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.select_language);

        ActionBar acnBar = getSupportActionBar();
        acnBar.setTitle(getResources().getString(R.string.app_name));

        buttonEnglish = (Button) findViewById(R.id.btnEnglish);
        buttonHindi = (Button) findViewById(R.id.btnHindi);
        buttonMarathi = (Button) findViewById(R.id.btnMarathi);

        buttonEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage(0);
                openLoginActivity();
            }
        });

        buttonHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage(1);
                openLoginActivity();
            }
        });

        buttonMarathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage(2);
                openLoginActivity();
            }
        });
    }

    /*private  void showChangeLanguageDialog(){
        final String[] listItems = {"English", "Hindi", "Marathi"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SelectLanguageActivity.this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    setLocale("en");
                    recreate();
                }
                else if(i==1){
                    setLocale("hi");
                    recreate();
                }
                else if(i==2){
                    setLocale("mr");
                    recreate();
                }

                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }*/
    private void changeLanguage(int selectedLanguage) {
        final String[] listItems = {"English", "Hindi", "Marathi"};

        if(selectedLanguage == 0){
            setLocale("en");
            recreate();
        }
        else if (selectedLanguage == 1){
            setLocale("hi");
            recreate();
        }
        else if (selectedLanguage == 2){
            setLocale("mr");
            recreate();
        }

    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

//  load language saved in shared preferences

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

    public void openFirstActivity() {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
        finish();
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
