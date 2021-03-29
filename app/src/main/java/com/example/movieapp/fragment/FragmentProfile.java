package com.example.movieapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.movieapp.MainActivity;
import com.example.movieapp.R;

import java.util.Objects;

public class FragmentProfile extends Fragment {
    private FrameLayout frmProfile;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_main,container,false);

        findViewByIdFunc(view);

        setAdapter(container);

        eventHandler();


        return view;
    }

    private void setAdapter(ViewGroup container) {
        /*searchAdapter = new SearchAdapter(container.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext());

        drawerRcyclerView.setLayoutManager(linearLayoutManager);
        drawerRcyclerView.setAdapter(searchAdapter);*/

    }

    public void eventHandler() {


    }

    public void findViewByIdFunc(View view) {

        frmProfile = view.findViewById(R.id.frmProfile);


    }

}
