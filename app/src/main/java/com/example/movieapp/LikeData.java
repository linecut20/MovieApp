package com.example.movieapp;

public class LikeData {
    private int no;
    private int mv_id;
    private String detailImage;
    private String detailTitle;

    public LikeData(int no, int mv_id, String detailImage, String detailTitle) {
        this.no = no;
        this.mv_id = mv_id;
        this.detailImage = detailImage;
        this.detailTitle = detailTitle;
    }

    @Override
    public String toString() {
        return "LikeData{" +
                "no=" + no +
                ", mv_id=" + mv_id +
                ", detailImage='" + detailImage + '\'' +
                ", detailTitle='" + detailTitle + '\'' +
                '}';
    }
}
