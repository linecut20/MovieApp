package com.example.movieapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.MainActivity;
import com.example.movieapp.R;
import com.example.movieapp.SearchAdapter;

public class FragmentSearch extends Fragment {
    private RecyclerView drawerRcyclerView;
    private FrameLayout frameDrawer;
    private SearchView searchBar;
    private Button btnSearch;
    private MainActivity mainActivity;
    private SearchAdapter searchAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_drawer,container,false);

        findViewByIdFunc(view);

        setAdapter(container);

        eventHandler();


        return view;
    }

    private void setAdapter(ViewGroup container) {
        searchAdapter = new SearchAdapter(container.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext());

        drawerRcyclerView.setLayoutManager(linearLayoutManager);
        drawerRcyclerView.setAdapter(searchAdapter);

    }

    public void eventHandler() {

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                btnSearch.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

    }

    public void findViewByIdFunc(View view) {

        drawerRcyclerView = view.findViewById(R.id.drawerRcyclerView);
//        frameDrawer = view.findViewById(R.id.frameDrawer);
        searchBar = view.findViewById(R.id.searchBar);
        btnSearch = view.findViewById(R.id.btnSearch);

    }
}
