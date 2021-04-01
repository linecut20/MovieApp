package com.example.movieapp.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.MainActivity;
import com.example.movieapp.R;

import java.util.ArrayList;

import MovieInfoDAO.TMDBDAO;
import adapter.BottomRecyclerViewAdapter;
import model.MovieInfo;

public class FragmentPopular extends Fragment {
    private RecyclerView rvPopular;
    private BottomRecyclerViewAdapter adapter;
    private Context context;
    private TMDBDAO tmdbdao;
    private int count = 1;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<MovieInfo> list;

    public FragmentPopular(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_bottom_popular,container,false);

        rvPopular = view.findViewById(R.id.rvPopular);
        gridLayoutManager = new GridLayoutManager(context, 3);
        tmdbdao = new TMDBDAO("popular",count);
        tmdbdao.execute();
        list = new ArrayList<>();
        list = tmdbdao.getMovieList();
        adapterFunc();

        //리사이클러뷰가 최하단에 도달할 경우, 다음 페이지의 목록을 로드
        rvPopular.setOnScrollChangeListener(new View.OnScrollChangeListener(){
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(!rvPopular.canScrollVertically(1)) {
                    count++;
                    Log.d("바텀그리드뷰", "last Position...");
                    adapterFunc();
                }
            }
        });
        return view;
    }

    private void adapterFunc() {
        adapter = new BottomRecyclerViewAdapter(context, list);
        rvPopular.setLayoutManager(gridLayoutManager);
        rvPopular.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
