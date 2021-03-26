package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUp, btnBack;
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        if(intent != null){
            String message = intent.getStringExtra("message");  //가져올 것 지정
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }

        findViewByIdFunc();


        btnSignUp.setOnClickListener(view -> {
            Toast.makeText(this, edtName.getText().toString()+"님 회원 가입을 축하합니다♡", Toast.LENGTH_SHORT).show();
        });

        btnBack.setOnClickListener(view -> {
            finish();
        });

    }

    private void findViewByIdFunc() {
        btnSignUp = findViewById(R.id.btnSignUp);
        btnBack = findViewById(R.id.btnBack);
        edtName = findViewById(R.id.edtName);
    }
}