package com.vesit.paddy_ver1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private EditText regName, regEmail, regPassword;
    String name, email, password;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        regName = findViewById(R.id.etRegName);
        regEmail = findViewById(R.id.etRegEmail);
        regPassword = findViewById(R.id.etRegPass);

        Button btnRegister = findViewById(R.id.btnUserRegister);
        TextView txtLogin = findViewById(R.id.txtOpenLogin);

        btnRegister.setOnClickListener(view -> validateUser());

        txtLogin.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser() !=null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    private void validateUser() {

        name = regName.getText().toString();
        email = regEmail.getText().toString();
        password = regPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            registerUser();
        }


    }

    private void registerUser() {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registration Successful",Toast.LENGTH_SHORT).show();
                        updateUser();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUser(){
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        auth.getCurrentUser().updateProfile(changeRequest);
        auth.signOut();
        openLogin();

    }

    private void openLogin(){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
}