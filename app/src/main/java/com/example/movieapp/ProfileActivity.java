package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvID,tvPassword, tvName, tvImage, tvPhone, tvEmail, tvBirth, tvGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvID=findViewById(R.id.tvID);
        tvPassword=findViewById(R.id.tvPassword);
        tvName=findViewById(R.id.tvName);
//        tvImage=findViewById(R.id.tvImage);
        tvPhone=findViewById(R.id.tvPhone);
        tvEmail=findViewById(R.id.tvEmail);
        tvBirth=findViewById(R.id.tvBirth);
        tvGender=findViewById(R.id.tvGender);

        Intent intent =getIntent();
        String ID = intent.getStringExtra("ID");
        String password = intent.getStringExtra("password");
        String name = intent.getStringExtra("name");
//        String image = intent.getStringExtra("image");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        String birth = intent.getStringExtra("birth");
        String gender = intent.getStringExtra("gender");


        tvID.setText(ID);
        tvPassword.setText(password);
        tvName.setText(name);
//        tvImage.setText(image);
        tvPhone.setText(phone);
        tvEmail.setText(email);
        tvBirth.setText(birth);
        tvGender.setText(gender);

    }
}