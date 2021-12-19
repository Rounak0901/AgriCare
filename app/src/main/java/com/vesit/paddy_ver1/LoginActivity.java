package com.vesit.paddy_ver1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.vesit.paddy_ver1.Data.UserDao;
import com.vesit.paddy_ver1.Data.UserDataBase;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    private TextView txtRegister;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        etUsername = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        txtRegister = (TextView) findViewById(R.id.txtRegisterYourself);

        userDao = Room.databaseBuilder(this, UserDataBase.class, "mi-database.db").allowMainThreadQueries()
                .build().getUserDao();

        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(userDao.getUser(user,password)!=null){
                openFirstActivity();
            }
            else{
                Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
            }

        });

        txtRegister.setOnClickListener(view -> {
            openRegisterActivity();
        });

    }

    public void openFirstActivity() {
        Intent login = new Intent(this, FirstActivity.class);
        startActivity(login);
        finish();
    }
    public void openRegisterActivity() {
        Intent login = new Intent(this, RegisterActivity.class);
        startActivity(login);
        finish();
    }
}
