package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class LikeActivity extends AppCompatActivity {
    private ListView likeListView;
    private Context context;
    private TextView likeListSize;
    private ArrayList<LikeData> likeDataArrayList = new ArrayList<>();
    private Toolbar supportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        likeListView = findViewById(R.id.likeListView);
        likeListSize = findViewById(R.id.likeListSize);

        eventHandler();
    }

    private void eventHandler() {

        //좋아요 리스트 목록 개수 표시하기
        if(likeDataArrayList.size() == 0){
            likeListSize.setText("0");
        }else{
            likeListSize.setText(String.valueOf(likeDataArrayList.size()));
        }
    }

    //액션바 설치
    public void setSupportActionBar(Toolbar supportActionBar) {
        this.supportActionBar = supportActionBar;
    }
}