package com.vesit.paddy_ver1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity  extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText manualCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);


        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        String phoneNumber = getIntent().getStringExtra("phonenumber");

        sendVerificationCode(phoneNumber);

        Button signIn = findViewById(R.id.btnOtpLogin);

        manualCode = findViewById(R.id.etEnterOtp);

        signIn.setOnClickListener(view -> {

            String code = manualCode.getText().toString().trim();

            if(code.isEmpty() || code.length()<6 ){
                manualCode.setError("Please enter the code");
                manualCode.requestFocus();
                return;
            }
            progressBar.setVisibility(View.GONE);

            verifyCode(code);
        });

    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Intent intent = new Intent(VerifyPhoneActivity.this,FirstActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else {
                Toast.makeText(VerifyPhoneActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendVerificationCode(String phoneNumber){
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,120,TimeUnit.SECONDS, this,mCallBack);
        //FirebaseAuth auth = FirebaseAuth.getInstance();
        //PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber(phoneNumber).setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(mCallBack).build();
        //PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                manualCode.setText(code);
                progressBar.setVisibility(View.GONE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this,
                    e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    };
}
