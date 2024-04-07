package com.example.saleapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.util.Patterns;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    EditText userName, fullName, email, phoneNumber, passWord, rePassWord;
    Button signUpBtn;
    TextView notification;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        userName = findViewById(R.id.ed_rgUsername);
        fullName = findViewById(R.id.ed_rgFullName);
        email = findViewById(R.id.ed_rgEmail);
        phoneNumber = findViewById(R.id.ed_rgPhoneNumber);
        passWord = findViewById(R.id.ed_rgPassword);
        rePassWord = findViewById(R.id.ed_rgRePassword);
        signUpBtn = findViewById(R.id.btn_rgRegister);
        notification = findViewById(R.id.tv_rgNotification);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void registerUser() {
        String strUserName = userName.getText().toString().trim();
        String strEmail = email.getText().toString().trim();
        String strPhoneNumber = phoneNumber.getText().toString().trim();
        String strFullName = fullName.getText().toString();
        String strPassWord = passWord.getText().toString().trim();
        String strRePassWord = rePassWord.getText().toString().trim();
        if (TextUtils.isEmpty(strUserName) &&
                TextUtils.isEmpty(strEmail) &&
                TextUtils.isEmpty(strPhoneNumber) &&
                TextUtils.isEmpty(strPassWord) &&
                TextUtils.isEmpty(strRePassWord)) {
            notification.setText("Please fill in all required fields");
            return;
        }

        if (TextUtils.isEmpty(strUserName)) {
            notification.setText("You have not entered a user name");
            return;
        }

        if (TextUtils.isEmpty(strEmail)) {
            notification.setText("You have not entered an email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            notification.setText("Please enter a valid email address");
            return;
        }
        if (TextUtils.isEmpty(strPhoneNumber)) {
            notification.setText("You have not entered a phone number");
            return;
        }

        if (TextUtils.isEmpty(strPassWord)) {
            notification.setText("You have not entered a password");
            return;
        }

        if (strPassWord.length() < 6) {
            notification.setText("Password length must be greater than 6 characters");
            return;
        }

        if (!strPassWord.equals(strRePassWord)) {
            notification.setText("Passwords do not match");
            return;
        }
        FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("userName").equalTo(strUserName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            notification.setText("User Name already exists. Please choose a different user name.");
                        } else {
                            FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("email").equalTo(strEmail)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                notification.setText("Email already exists. Please choose a different email.");
                                            } else {
                                                auth.createUserWithEmailAndPassword(strEmail, strPassWord)
                                                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(RegisterActivity.this, "Registration successful.",
                                                                            Toast.LENGTH_SHORT).show();
                                                                    User user = new User(strUserName, strFullName, strEmail, strPhoneNumber, strPassWord, "", "", "2");
                                                                    String id = task.getResult().getUser().getUid();
                                                                    database.getReference().child("Users").child(id).setValue(user);
                                                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                                    startActivity(intent);
                                                                } else {
                                                                    Toast.makeText(RegisterActivity.this, "Registration failed. Please try again later.",
                                                                            Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


}