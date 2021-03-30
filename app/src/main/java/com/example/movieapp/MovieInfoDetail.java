package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MovieInfoDetail extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_EXTERNAL_STORAGE_CODE = 1;
    boolean permissionCheck = false;

    private ImageView posterView;
    private YouTubePlayerView youtube;
    private RatingBar ratingBar;
    private TextView tvTitle, tvYear, tvRating, tvStory, reviewList, tvMemo;
    private LinearLayout addLayout, shareLayout, memoLayout, ratingLayout, instaLayout;
    private ImageButton ibMore1, ibMore2, addBtn, shareBtn;
    private View view;

    private boolean ib1flag = true;
    private boolean ib2flag = true;
    private boolean addflag = true;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_movie_info_detail, container, false);

        //ID 찾아주기
        findViewByIdFunc();


        ibMore1.setOnClickListener(this);
        ibMore2.setOnClickListener(this);
        addLayout.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
        memoLayout.setOnClickListener(this);
        memoLayout.setOnClickListener(this);
        memoLayout.setOnClickListener(this);

        return view;
    }

    private void findViewByIdFunc() {
        posterView = view.findViewById(R.id.posterView);
        //youtube = view.findViewById(R.id.youtube);
        ratingBar = view.findViewById(R.id.ratingBar);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvYear = view.findViewById(R.id.tvYear);
        tvRating = view.findViewById(R.id.tvRating);
        tvStory = view.findViewById(R.id.tvStory);
        reviewList = view.findViewById(R.id.reviewList);
        addLayout = (LinearLayout) view.findViewById(R.id.addLayout);
        shareLayout = (LinearLayout) view.findViewById(R.id.shareLayout);
        memoLayout = (LinearLayout) view.findViewById(R.id.memoLayout);
        ratingLayout = view.findViewById(R.id.ratingLayout);
        instaLayout = (LinearLayout) view.findViewById(R.id.instaLayout);
        ibMore1 = view.findViewById(R.id.ibMore1);
        ibMore2 = view.findViewById(R.id.ibMore2);
        addBtn = view.findViewById(R.id.addBtn);
        shareBtn = view.findViewById(R.id.shareBtn);
        tvMemo = view.findViewById(R.id.tvMemo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibMore1:
                if (ib1flag == true) {
                    tvStory.setMaxLines(Integer.MAX_VALUE);
                    ibMore1.setImageResource(R.drawable.movie_less);
                    ib1flag = false;
                } else {
                    tvStory.setMinLines(Integer.MIN_VALUE);
                    ibMore1.setImageResource(R.drawable.movie_more);
                    ib1flag = true;
                }
                break;
            case R.id.ibMore2:
                if (ib2flag == true) {
                    reviewList.setMaxLines(Integer.MAX_VALUE);
                    ibMore2.setImageResource(R.drawable.movie_less);
                    ib2flag = false;
                } else {
                    reviewList.setMinLines(Integer.MIN_VALUE);
                    ibMore2.setImageResource(R.drawable.movie_more);
                    ib2flag = true;
                }
                break;
            case R.id.addLayout:
                if (addflag == true) {
                    addBtn.setImageResource(R.drawable.movie_added);
                    addflag = false;
                    //DB넣는거 해야함!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                } else {
                    addBtn.setImageResource(R.drawable.movie_add);
                    addflag = true;
                }
                break;
            case R.id.shareLayout:

                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.example.movieapp");
                shareIntent.putExtra(Intent.EXTRA_TITLE, "제목");

                shareIntent.setType("text/plain");

                startActivity(Intent.createChooser(shareIntent, "앱을 선택하십시오."));
                break;
            case R.id.memoLayout:
                DialogMemo dm = new DialogMemo(MovieInfoDetail.this);
                dm.callFunction(tvMemo);
                break;
            case R.id.ratingLayout:
                showDialog();

                break;
            case R.id.instaLayout:
                onRequestPermission();

                if (permissionCheck) {
                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.iu);
                    String storage = Environment.getExternalStorageDirectory().getAbsolutePath();
                    String fileName = "이미지명.png";

                    String folderName = "/폴더명/";
                    String fullPath = storage + folderName;
                    File filePath;

                    try {
                        filePath = new File(fullPath);
                        if (!filePath.isDirectory()) {
                            filePath.mkdirs();
                        }
                        FileOutputStream fos = new FileOutputStream(fullPath + fileName);
                        bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        fos.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("image/*");
                    Uri uri = Uri.fromFile(new File(fullPath, fileName));
                    try {
                        share.putExtra(Intent.EXTRA_STREAM, uri);

                        share.putExtra(Intent.EXTRA_TEXT, "텍스트는 지원하지 않음!");
                        share.setPackage("com.instagram.android");
                        startActivity(share);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(this, "인스타그램이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                }//switch
            }
        }

        public void showDialog() {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            final RatingBar ratingBar = new RatingBar(this);
            ratingBar.setMax(5);

            dialog.setTitle("영화 평가");
            dialog.setView(ratingBar);

            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            dialog.create();
            dialog.show();
        }

    public void onRequestPermission() {
        int permissionReadStorage = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteStorage = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionReadStorage == PackageManager.PERMISSION_DENIED
                || permissionWriteStorage == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_CODE);
        } else {
            permissionCheck = true; //이미 허용되어 있으므로 PASS
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "허용했으니 가능합니다.", Toast.LENGTH_LONG).show();
                            permissionCheck = true;
                        } else {
                            Toast.makeText(this, "허용하지 않으면 공유하지 못합니다.", Toast.LENGTH_LONG).show();
                            permissionCheck = false;
                        }
                    }
                }
                break;
        }
    }
}