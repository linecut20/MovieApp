package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.request.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class SignUpActivity extends AppCompatActivity {

    private EditText edtID,edtPassword,edtName, edtImage, edtPhone, edtEmail, edtBirth, edtGender;
    private Button btnRegister, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findFunc();
        eventFunc();
    }

    private void eventFunc() {




        btnRegister.setOnClickListener(v->{
            String ID = edtID.getText().toString();
            String password = edtPassword.getText().toString();
            String name = edtName.getText().toString();
//            String image = "이미지 파일 임시 주소";
//            String image = edtImage.getText().toString();
            String phone = edtPhone.getText().toString();
            String email = edtEmail.getText().toString();
            String birth = edtBirth.getText().toString();
            String gender = edtGender.getText().toString();


            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jo = new JSONObject(response);
                        boolean success = jo.getBoolean("success");
                        if(success==true){
                            toastMessage(name + "님 회원가입 성공");
                            Intent intent = new Intent(SignUpActivity.this,ProfileActivity.class);
                            startActivity(intent);
                        }else{
                            toastMessage("회원 가입 실패");
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

//            RegisterRequest registerRequest = new RegisterRequest(ID, password, name, image, phone, email, birth, gender, listener);
            RegisterRequest registerRequest = new RegisterRequest(ID, password, name, phone, email, birth, gender, listener);
            RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
            queue.add(registerRequest);
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toastMessage(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    private void findFunc() {
        edtID = findViewById(R.id.edtID);
        edtPassword = findViewById(R.id.edtPassword);
        edtName = findViewById(R.id.edtName);
//        edtImage= findViewById(R.id.edtImage);
        edtPhone= findViewById(R.id.edtPhone);
        edtEmail= findViewById(R.id.edtEmail);
        edtBirth= findViewById(R.id.edtBirth);
        edtGender= findViewById(R.id.edtGender);

        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);
    }

}