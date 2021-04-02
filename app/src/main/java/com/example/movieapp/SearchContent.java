package com.example.movieapp;

import java.util.ArrayList;
import java.util.List;

import model.MovieInfo;

public class SearchContent {
    private ArrayList<MovieInfo> searchList = null;

    public SearchContent(){
        searchList = new ArrayList<>();
    }

    public ArrayList<MovieInfo> getNoticeList() {
        return searchList;
    }
}
