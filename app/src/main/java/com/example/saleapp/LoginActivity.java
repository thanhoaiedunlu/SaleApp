package com.example.saleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saleapp.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText edUserName, edpassWord;
    private TextView txtRegister;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserName = findViewById(R.id.ed_username);
        edpassWord = findViewById(R.id.ed_password);
        txtRegister = findViewById(R.id.register_text);
        initControl();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("hello");
//        myRef.setValue("Hi");

//        mData.child("Name").push().setValue("Nguyễn Thanh Hoài", new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                if (error == null){
//                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        User user = new User("1", "Thanhhoai", "Nguyễn Thanh Hoài", "HoaiThanh", "0011", "1", "1", "1", "1", "user");
//        mData.child("Users").setValue(user);
//        Map<String, Integer> map = new HashMap<>();
//        map.put("Nyc",2);
//        mData.child("NYC").setValue(map);

//        mData.child("Name").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                txtRegister.setText(snapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        mData = FirebaseDatabase.getInstance().getReference();
        User user = new User("122", "Thanhhoai", "Nguyễn Thanh Hoài", "HoaiThanh", "0011", "1", "1", "1", "1", "user");
//        mData.child("Users").push().setValue(user);

        mData.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                  User  user2 = snapshot.getValue(User.class);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initControl(){
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


}