package com.example.movieapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movieapp.R;


public class FragmentBanner5 extends Fragment {
    private ImageView iv_main_banner5;
    private int fragNumber;

    //intent로 값을 전달할 수 없음에 번들처리(Activity <-> Fragment)
    //Fragment어댑터에 탑재할 인스턴스 정의
    public static FragmentBanner5 newInstance(int fragNumber) {
        FragmentBanner5 FB5 = new FragmentBanner5();
        Bundle bundle = new Bundle();
        bundle.putInt("fragnumber5",fragNumber);
        FB5.setArguments(bundle);
        return FB5;
    }

    //생성될 때 fragNumber에 값을 저장
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNumber = getArguments().getInt("fragnumber5",0);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_banner5, container, false);

        iv_main_banner5 = view.findViewById(R.id.iv_main_banner5);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
