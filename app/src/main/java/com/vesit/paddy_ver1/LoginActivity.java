package com.vesit.paddy_ver1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;
    private TextView txtRegister;
    private TextView txtForgotPassword;

    private String email, pass;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);

        auth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnUserLogin);
        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        txtRegister = findViewById(R.id.txtRegister);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);


        txtForgotPassword.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class)));

        txtRegister.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        btnLogin.setOnClickListener(view -> validateUser());



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser() !=null){
            startActivity(new Intent(this, FirstActivity.class));
            finish();
        }

    }

    private void validateUser() {
        email = etEmail.getText().toString();
        pass = etPassword.getText().toString();
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            loginUser();
        }

    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, FirstActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
