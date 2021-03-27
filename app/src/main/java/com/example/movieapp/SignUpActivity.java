package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUp, btnBack;
    private EditText edtName;

    // 리퀘스트코드
    private static final int PICK_FROM_CAMERA = 2;
    private static final int PICK_FROM_ALBUM = 1;

    String TAG = "파일복사";

    //Shared Preference 키값
    private static final String USER = "User", INIT = "init";

    private Context mContext;
    private View view;

    private ImageView circleImageView;

    private EditText edtDescription;

    private File tempFile;
    private File copyFile;
    private String profileImgPath;



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