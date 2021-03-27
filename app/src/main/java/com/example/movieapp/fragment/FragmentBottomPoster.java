package com.example.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import adapter.MainRecyclerViewAdapter;

public class FragmentBottomPoster extends Fragment {
    private RecyclerView recyclerView;
    private MainRecyclerViewAdapter adapter;
    private Context context;
    private GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_bottom_poster,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);
        adapterFunc();

        return view;
    }

    private void adapterFunc() {
        adapter = getArguments().getParcelable("adapter");
        gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
