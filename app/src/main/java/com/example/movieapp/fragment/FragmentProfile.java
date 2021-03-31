package com.example.movieapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.movieapp.LikeActivity;
import com.example.movieapp.MainActivity;
import com.example.movieapp.ProfileActivity;
import com.example.movieapp.R;

import java.util.Objects;

public class FragmentProfile extends Fragment {
    private FrameLayout frmProfile;
    private ImageView crIvProfilePicture;
    private TextView tvProfileName, tvProfileEmail;
    private Button btnProfile, btnLikeList, btnProfileLogout;
    private MainActivity mainActivity = new MainActivity();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_main, container, false);

        findViewByIdFunc(view);

        //setAdapter(container);

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
        //드로어 메뉴 인텐트 이벤트
        btnLikeList.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LikeActivity.class);
            startActivity(intent);
        });

        btnProfile.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
        });

        btnMovieReview.setOnClickListener(v -> {


        });
//
//        btnProfileLogout.setOnClickListener(v->{
//
//        });

    }

    public void findViewByIdFunc(View view) {

        frmProfile = view.findViewById(R.id.frmProfile);
        crIvProfilePicture = view.findViewById(R.id.crIvProfilePicture);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        btnProfile = view.findViewById(R.id.btnProfile);
        btnLikeList = view.findViewById(R.id.btnLikeList);
        btnProfileLogout = view.findViewById(R.id.btnProfileLogout);

    }
}
