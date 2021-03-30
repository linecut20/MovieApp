package com.example.movieapp.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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

import adapter.BottomRecyclerViewAdapter;

public class FragmentBottomPoster extends Fragment {
    private RecyclerView recyclerView;
    private BottomRecyclerViewAdapter adapter;
    private Context context;
    private GridLayoutManager gridLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_bottom_poster,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);
        adapterFunc();


        recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if(!recyclerView.canScrollVertically(1)) {
                ((MainActivity)getActivity()).movieListFunc();
            }
        });
        return view;
    }

    private void adapterFunc() {
        adapter = getArguments().getParcelable("adapter");
        gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
