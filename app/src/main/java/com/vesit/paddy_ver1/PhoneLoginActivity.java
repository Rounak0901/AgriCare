package com.vesit.paddy_ver1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class PhoneLoginActivity extends AppCompatActivity {

    private EditText etPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_phone);

        etPhoneNumber = findViewById(R.id.etLoginPhoneNumber);

        Button login = findViewById(R.id.btnUserLogin);

        login.setOnClickListener(view -> {
            String number = etPhoneNumber.getText().toString();
            if (number.isEmpty() || number.length()<10){
                etPhoneNumber.setError("Valid number is required");
                etPhoneNumber.requestFocus();
                return;
            }

            String phoneNumber = "+91" + number;

            Intent intent = new Intent(PhoneLoginActivity.this,VerifyPhoneActivity.class);
            intent.putExtra("phonenumber",phoneNumber);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            Intent intent = new Intent(PhoneLoginActivity.this,FirstActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
