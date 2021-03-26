package com.example.movieapp;

import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class SearchData {
    private String moviePoster, movieTitle, movieInfo;

    public SearchData(String moviePoster, String movieTitle, String movieInfo) {
        this.moviePoster = moviePoster;
        this.movieTitle = movieTitle;
        this.movieInfo = movieInfo;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(String movieInfo) {
        this.movieInfo = movieInfo;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchData that = (SearchData) o;
        return Objects.equals(movieTitle, that.movieTitle);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash( movieTitle );
    }

    @Override
    public String toString() {
        return "SearchData{" +
                "moviePoster='" + moviePoster + '\'' +
                ", movieTitle='" + movieTitle + '\'' +
                ", movieInfo='" + movieInfo + '\'' +
                '}';
    }
}
