package com.vesit.paddy_ver1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button btnForget;
    private EditText etEmail;

    String email;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etResetEmail);
        btnForget = findViewById(R.id.btnPasswordReset);

        btnForget.setOnClickListener(view -> validateData());
    }

    private void validateData(){
        email = etEmail.getText().toString();
        if (email.isEmpty()) {
            etEmail.setError("Required");
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        } else {
            forgetPass();
        }
    }

    private void forgetPass(){

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(ForgotPasswordActivity.this, "Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}