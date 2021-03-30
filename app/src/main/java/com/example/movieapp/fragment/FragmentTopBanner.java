package com.example.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import adapter.BottomRecyclerViewAdapter;
import adapter.TopRecyclerViewAdapter;

public class FragmentTopBanner extends Fragment {
    private RecyclerView topRecyclerView;
    private TopRecyclerViewAdapter adapter;
    private Context context;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_top_menu,container,false);

        topRecyclerView = view.findViewById(R.id.topRecyclerView);
        adapterFunc();

        return view;
    }

    private void adapterFunc() {
        adapter = getArguments().getParcelable("adapter");
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        topRecyclerView.setLayoutManager(linearLayoutManager);
        topRecyclerView.setAdapter(adapter);
    }
}
