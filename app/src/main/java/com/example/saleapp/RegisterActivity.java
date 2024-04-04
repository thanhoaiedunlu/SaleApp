package com.example.saleapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saleapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText userName, fullName, email, phoneNumber, passWord, rePassWord;
    Button signInBtn;
    TextView notification;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userName = findViewById(R.id.ed_rgUsername);
        fullName = findViewById(R.id.ed_rgFullName);
        email = findViewById(R.id.ed_rgEmail);
        phoneNumber = findViewById(R.id.ed_rgPhoneNumber);
        passWord = findViewById(R.id.ed_rgPassword);
        rePassWord = findViewById(R.id.ed_rgRePassword);
        signInBtn = findViewById(R.id.btn_rgRegister);
        notification = findViewById(R.id.tv_rgNotification);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    public void register(){
        String strUserName = userName.getText().toString().trim();
        String strFullName = fullName.getText().toString().trim();
        String strEmail = email.getText().toString().trim();
        String strPhoneNumber = phoneNumber.getText().toString().trim();
        String strPassWord = passWord.getText().toString().trim();
        String strRePassWord = rePassWord.getText().toString().trim();
        if (TextUtils.isEmpty(strUserName)){
            notification.setText("You have not entered a user name");
        }
        else if (TextUtils.isEmpty(strFullName)){
            notification.setText("You have not entered a full name");
        }
        else if (TextUtils.isEmpty(strEmail)){
            notification.setText("You have not entered a email");
        }
        else if (TextUtils.isEmpty(strPhoneNumber)){
            notification.setText("You have not entered a phone number");
        } else if (strPassWord.length() < 6) {
            notification.setText("Password length must be greater the 6 letter");
        }
        else if (TextUtils.isEmpty(strPassWord)){
            notification.setText("You have not entered a password");
        }
        else if (TextUtils.isEmpty(strRePassWord)){
            notification.setText("You have not entered a repass word");
        }
        else if (strPassWord.equals(strRePassWord) == false) {
            notification.setText("Passwords do not match");
        }
        firebaseAuth.createUserWithEmailAndPassword(strEmail, strPassWord)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(task.getResult().getUser().getUid(),strUserName, strFullName, strEmail, strPhoneNumber, strPassWord,"", "", "1","user");
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                        }
                    }
                });

    }




}