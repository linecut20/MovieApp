package com.example.movieapp;

import java.util.ArrayList;

public class LikeData {
    private int id;
    private String poster;
    private String title;
    private int like;

    private ArrayList<LikeData> searchlist = new ArrayList<>();

    public LikeData(int id, String poster, String title, int like) {
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.like = like;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public ArrayList<LikeData> getSearchlist() {
        return searchlist;
    }

    public void setSearchlist(ArrayList<LikeData> searchlist) {
        this.searchlist = searchlist;
    }

    @Override
    public String toString() {
        return "LikeData{" +
                "id=" + id +
                ", poster='" + poster + '\'' +
                ", title='" + title + '\'' +
                ", like=" + like +
                '}';
    }

    public ArrayList<LikeData> get(int position) {
        return searchlist;
    }
}
