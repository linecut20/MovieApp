package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class LikeActivity extends AppCompatActivity {
    private RecyclerView recyclerLike;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        recyclerLike = findViewById(R.id.recyclerLike);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerLike.setLayoutManager(linearLayoutManager);


    }
}