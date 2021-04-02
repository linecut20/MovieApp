package com.example.movieapp.util;

import android.provider.BaseColumns;

public final class MoiveAppDB {


    public static final class CreateMemo implements BaseColumns{

        public static final String MEMO_TBL = "memo_tbl";
        public static final String MV_ID = "mv_id";
        public static final String CONTENT = "content";
        public static final String CREATE_MEMO = "create table if not exists "
                +MEMO_TBL+" ("
                +MV_ID+" integer primary key, "
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

