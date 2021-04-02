package com.example.movieapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {

    private static final String DB_NAME = "Diary.db";
    private static final int VERSION = 1;
    public static SQLiteDatabase mDB;

    private MemoTBLHelper memoHelper;
    private LikeTBLHelper likeHelper;
    private Context mContext;

    public DbOpenHelper(Context context) {
        this.mContext = context;
    }

    public void close() {
        mDB.close();
    }



    public void createMemoHelper() {
        memoHelper.onCreate(mDB);
    }

    public void createLikeHelper() {
        likeHelper.onCreate(mDB);
    }



    public void upgradeMemoHelper() {
        memoHelper.onUpgrade(mDB, VERSION, VERSION);
    }

    public void upgradeLikeHelper() {
        likeHelper.onUpgrade(mDB, VERSION, VERSION);
    }



    public DbOpenHelper openMemo() throws SQLException {
        memoHelper = new MemoTBLHelper(mContext, DB_NAME, null, VERSION);
        mDB = memoHelper.getWritableDatabase();
        return this;
    }

    public DbOpenHelper openLike() throws SQLException {
        likeHelper = new LikeTBLHelper(mContext, DB_NAME, null, VERSION);
        mDB = likeHelper.getWritableDatabase();
        return this;
    }

    public Cursor sortColumn(String tbl_name, String sort) {
        Cursor c = mDB.rawQuery("SELECT * FROM " + tbl_name + " ORDER BY " + sort + ";", null);
        return c;
    }



    ///////////////////////////////////////////////////
    //MEMO 테이블 CRUD 모음

    public long insertMemoColumn(int mv_id, String content) {
        ContentValues values = new ContentValues();
        values.put(MoiveAppDB.CreateMemo.MV_ID, mv_id);
        values.put(MoiveAppDB.CreateMemo.CONTENT, content);
        return mDB.insert(MoiveAppDB.CreateMemo.MEMO_TBL, null, values);
    }

    public Cursor selectMemoColumns() {
        return mDB.query(MoiveAppDB.CreateMemo.MEMO_TBL, null, null, null, null, null, MoiveAppDB.CreateMemo.MV_ID);
    }

    public boolean updateMemoColumn(int mv_id, String title, String poster, String mv_date, String post_date
            , float star, String content) {
        ContentValues values = new ContentValues();
        values.put(MoiveAppDB.CreateMemo.MV_ID, mv_id);
        values.put(MoiveAppDB.CreateMemo.CONTENT, content);
        return mDB.update(MoiveAppDB.CreateMemo.MEMO_TBL, values, "mv_id=" + mv_id, null) > 0;

    }

    public void deleteMemoColumns(int mv_id) {
        mDB.delete(MoiveAppDB.CreateMemo.MEMO_TBL, "mv_id=" + mv_id, null);

    }

    public Cursor searchMemoColumn(String columnName, String str) {
        Cursor c = mDB.rawQuery("SELECT * FROM memo_tbl WHERE " + columnName + " LIKE '%" + str + "%';", null);
        return c;
    }

    public boolean isExistMemoColumn(int mv_id) {
        Cursor c = mDB.rawQuery("SELECT * FROM memo_tbl WHERE mv_id = " + mv_id, null);
        return c.getCount() > 0;
    }


    ///////////////////////////////////////////////////
    //라이크 테이블 CRUD 모음

    public long insertLikeColumn(int mv_id, String title, String mv_poster) {
        ContentValues values = new ContentValues();
        values.put(MoiveAppDB.CreateLike.MV_ID, mv_id);
        values.put(MoiveAppDB.CreateLike.TITLE, title);
        values.put(MoiveAppDB.CreateLike.MV_POSTER, mv_poster);
        return mDB.insert(MoiveAppDB.CreateLike.LIKE_TBL, null, values);
    }

    public Cursor selectLikeColumns() {
        return mDB.query(MoiveAppDB.CreateLike.LIKE_TBL, null, null, null, null, null, null);
    }

    public boolean updateLikeColumn(int mv_id, String title, String mv_poster) {
        ContentValues values = new ContentValues();
        values.put(MoiveAppDB.CreateLike.MV_ID, mv_id);
        values.put(MoiveAppDB.CreateLike.TITLE, title);
        values.put(MoiveAppDB.CreateLike.MV_POSTER, mv_poster);
        return mDB.update(MoiveAppDB.CreateLike.LIKE_TBL, values, "mv_id=" + mv_id, null) > 0;

    }

    public boolean isExistLikeColumn(int mv_id) {
        Cursor c = mDB.rawQuery("SELECT * FROM like_tbl WHERE mv_id = " + mv_id, null);
        return c.getCount() > 0;
    }

    public void deleteLikeColumns(int mv_id) {
        mDB.delete(MoiveAppDB.CreateLike.LIKE_TBL, "mv_id=" + mv_id, null);

    }

    ///////////////////////////////////////////////////



    //Memo테이블
    private class MemoTBLHelper extends SQLiteOpenHelper {

        public MemoTBLHelper(Context context, String dbName, Object o, int version) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MoiveAppDB.CreateMemo.CREATE_MEMO);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + MoiveAppDB.CreateMemo.MEMO_TBL);
            onCreate(db);
        }

    }

    //라이크 테이블
    private class LikeTBLHelper extends SQLiteOpenHelper {

        public LikeTBLHelper(Context context, String dbName, Object o, int version) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MoiveAppDB.CreateLike.CREATE_LIKE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + MoiveAppDB.CreateLike.LIKE_TBL);
            onCreate(db);
        }

    }

}
