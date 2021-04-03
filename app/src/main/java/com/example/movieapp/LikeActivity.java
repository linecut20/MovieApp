package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.movieapp.util.DbOpenHelper;

import java.util.ArrayList;

import MovieInfoDAO.TMDBDAO;
import adapter.LikeAdapter;
import model.MovieInfo;

public class LikeActivity extends AppCompatActivity {
    private RecyclerView likeRecyclerReview;
    private Context context;
    private TextView likeListSize;
    private Toolbar supportActionBar;

    private ArrayList<MovieInfo> likeDataArrayList = new ArrayList<MovieInfo>();
    //private ArrayList<MovieInfo> allmovieList = new ArrayList<MovieInfo>();

    private DbOpenHelper dbOpenHelper;
    private MovieInfo movieInfo;
    private LikeAdapter likeAdapter;
    private LinearLayoutManager linearLayoutManager;
    private TMDBDAO tmdbdao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        findViewByIdFunc();

        //DB 세팅하기
        //dbOpenHelper = new DbOpenHelper(this);
        /*if (dbOpenHelper == null) {
            dbOpenHelper.createLikeHelper();
            dbOpenHelper.openLike();
        }else{*/

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        likeRecyclerReview.setLayoutManager(linearLayoutManager);

        insertList("like_tbl", "id DES");
        //insertList("like_tbl", "no DES");
        /*if (dbOpenHelper == null) {
        } else {
            dbOpenHelper.createLikeHelper();
            dbOpenHelper.openLike();
        }
*/
        likeAdapter = new LikeAdapter(R.layout.item_likelist, likeDataArrayList);
        likeRecyclerReview.setAdapter(likeAdapter);
        likeAdapter.notifyDataSetChanged();


        /*dbOpenHelper.insertLikeColumn( movieInfo.getId(), movieInfo.getTitle()
                , movieInfo.getPoster_path());
        */

        settingAdapter();

        eventHandler();
    }

    private void settingAdapter() {

        //어댑터

    }

    private void makeAdapter() {
        /*tmdbdao = new TMDBDAO("upcoming", 1);
        tmdbdao.execute();
        allmovieList = tmdbdao.getMovieList();
*/
    }


    private void insertList(String tbl_name, String sort) {

        likeDataArrayList.clear();
            dbOpenHelper = new DbOpenHelper(getApplicationContext());
            dbOpenHelper.openLike();

        //Cursor cursor = dbOpenHelper.sortColumn(tbl_name, sort);
        Cursor cursor = dbOpenHelper.selectLikeColumns();

        while (cursor.moveToNext()) {

            int tempMvId = cursor.getInt(cursor.getColumnIndex("id"));
            String tempPoster = cursor.getString(cursor.getColumnIndex("poster_path"));
            String tempTitle = cursor.getString(cursor.getColumnIndex("title"));
            int tempLike = cursor.getInt(cursor.getColumnIndex("like"));

            likeDataArrayList.add(new MovieInfo(tempMvId, tempPoster, tempTitle, tempLike));
        }

        Log.d("TAG", String.valueOf(likeDataArrayList.size()));

        dbOpenHelper.close();
    }


    private void findViewByIdFunc() {
        likeRecyclerReview = findViewById(R.id.likeRecyclerReview);
        likeListSize = findViewById(R.id.likeListSize);

    }

    //update
    private void eventHandler() {

        //좋아요 리스트 목록 개수 표시하기
        if (likeDataArrayList.size() == 0) {
            likeListSize.setText("0");
        } else {
            likeListSize.setText(String.valueOf(likeDataArrayList.size()));
        }
        likeListSize.setOnClickListener(v -> {
            //Toast.makeText(getApplicationContext(), allmovieList.size() + "", Toast.LENGTH_SHORT).show();
        });


    }

    //액션바 설치
    public void setSupportActionBar(Toolbar supportActionBar) {
        this.supportActionBar = supportActionBar;
    }
}