package com.example.movieapp.util;

import android.provider.BaseColumns;

public final class MoiveAppDB {


    public static final class CreateMemo implements BaseColumns{

        public static final String POSTING_TBL = "posting_tbl";
        public static final String MV_ID = "mv_id";
        public static final String TITLE = "title";
        public static final String MV_POSTER = "mv_poster";
        public static final String MV_DATE = "mv_date"; //영화본날짜
        public static final String POST_DATE = "post_date"; //포스팅한날짜
        public static final String STAR = "star";
        public static final String CONTENT = "content";
        public static final String CREATE_POSTING = "create table if not exists "
                +POSTING_TBL+" ("
                +MV_ID+" integer primary key, "
                +MV_POSTER+" text , "
                +TITLE+" text not null , "
                +MV_DATE+" text not null , "
                +POST_DATE+" text not null , "
                +STAR+" real not null , "
                +CONTENT+" text not null);";
    }

    public static final class CreateLike implements BaseColumns{

        public static final String LIKE_TBL = "like_tbl";
        public static final String TITLE = "title";
        public static final String MV_ID = "mv_id";
        public static final String MV_POSTER = "mv_poster";
        public static final String NO = "no";
        public static final String CREATE_LIKE = "create table if not exists "
                +LIKE_TBL+" ("
                +NO+" integer primary key AUTOINCREMENT, "
                +MV_ID+" integer, "
                +TITLE+" text not null, "
                +MV_POSTER+" text );";
    }
}

