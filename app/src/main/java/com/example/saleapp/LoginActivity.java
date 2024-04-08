package com.example.saleapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private TextView txtRegister, notification;
    private Button btnLogin;
   private FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.ed_lgEmail);
        password = findViewById(R.id.ed_lgpassword);
        txtRegister = findViewById(R.id.register_text);
        btnLogin = findViewById(R.id.btn_login);
        notification = findViewById(R.id.tv_notification);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(){
       String strEmail = email.getText().toString();
       String strPassword = password.getText().toString();
        if (TextUtils.isEmpty(strEmail)) {
            notification.setText("You have not entered a user name");
            return;
        }
        if (TextUtils.isEmpty(strPassword)) {
            notification.setText("You have not entered a password");
            return;
        }
        auth.signInWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                notification.setText("Login success");
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }else
                                notification.setText("You entered the wrong email or password");
                    }
                });
    }


}