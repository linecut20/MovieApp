package com.example.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.MainActivity;
import com.example.movieapp.R;

import adapter.MiddleAdapter;

public class FragmentMiddleMenu extends Fragment {
    private RecyclerView middle_recyclerView;
    private MiddleAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_middle_menu,container,false);

        middle_recyclerView = view.findViewById(R.id.middle_recyclerView);
        adapterFunc();

        return view;
    }

    private void adapterFunc() {
        adapter = getArguments().getParcelable("adapter");
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        middle_recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
        middle_recyclerView.setAdapter(adapter);
    }
}
