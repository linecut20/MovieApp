package com.example.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ResourceBundle;

public class FragmentYoutube extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {
    private static final String MOVIE_URL = "MOVIE_URL";

//    public static FragmentYoutube newInstance(String url) {
//        FragmentYoutube fragment = new FragmentYoutube();
//
//        Bundle args = new Bundle();
//        args.putString(MOVIE_URL, url);
//        fragment.setArguments(args);
//        fragment.init();
//        return fragment;
//    }

//    private void setArguments(Bundle args) {
//        //아님 수정해야함
//    }

    private void init() {

        this.initialize(DeveloperKey.YOUTUBE, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//        if (!b)
//            youTubePlayer.cueVideo(getArguments().getString(MOVIE_URL), 0);

    }

//    private ResourceBundle getArguments() {
//        //아님 수정해야함
//        return null;
//    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//        Toast.makeText(getContext(), "영상로드실패", Toast.LENGTH_SHORT).show();

    }

//    private Context getContext() {
//        //아님 수정해야함
//        return null;
//    }
}
