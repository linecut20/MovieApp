package com.example.movieapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class JoonActivity extends AppCompatActivity {

    private ImageButton imgbtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joon);

        imgbtn3 = findViewById(R.id.imgbtn3);

        imgbtn3.setOnClickListener( v-> {

        DialogMemo dm = new DialogMemo(JoonActivity.this);

        dm.callFunction();
        });

    }
}