package model;

import java.io.Serializable;

public class MovieInfo implements Serializable {
    private int id;
    private String title;
    private String original_title;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String release_date;
    private double vote_average;
    private int like;


    public MovieInfo(int id, String title, String original_title, String poster_path, String overview, String backdrop_path, String release_date, double vote_average,int like) {
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.like = like;
    }

    public MovieInfo(int tempMvId, String tempPoster, String tempTitle, int tempLike) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.like = like;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

}
