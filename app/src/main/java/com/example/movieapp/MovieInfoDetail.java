package com.example.movieapp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Resources.NaverMovieRepository;
import Resources.OnGetNaverMovieCallback;
import model.MovieInfo;
import model.NaverMovie;
import model.Youtube;

public class MovieInfoDetail extends YouTubeBaseActivity implements View.OnClickListener {

    private static final int REQUEST_EXTERNAL_STORAGE_CODE = 1;
    boolean permissionCheck = false;

    private MovieInfo movieInfo;

    private RecyclerView recyclerReview;
    private ImageView posterView;
    private YouTubePlayerView youtube;
    private RatingBar ratingBar;
    private TextView tvTitle, tvYear, tvRating, tvStory, tvMemo;
    private LinearLayout addLayout, shareLayout, memoLayout, ratingLayout, instaLayout;
    private ImageButton ibMore1, ibMore2, addBtn, shareBtn;
    private View view;
    //더보기를 위해 설정해둔 변수
    private boolean ib1flag = true;
    private boolean ib2flag = true;
    private boolean addflag = true;

    //유튜브 플레이어를 재생하기 위한 변수
    ArrayList<Youtube> youtubeList;
    private YouTubePlayerView youTubeView;
    private String trailer;
    private String m_id;

    private Context context;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_movie_info_detail);

        findViewByIdFunc();

        ibMore1.setOnClickListener(this);
        ibMore2.setOnClickListener(this);
        addLayout.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
        memoLayout.setOnClickListener(this);
        memoLayout.setOnClickListener(this);
        memoLayout.setOnClickListener(this);

        getDataFromMainFunc();
        storyDetail();
        reviewListFunc();

    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_movie_info_detail, container, false);

        //ID 찾아주기

        ibMore1.setOnClickListener(this);
        ibMore2.setOnClickListener(this);
        addLayout.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
        memoLayout.setOnClickListener(this);
        memoLayout.setOnClickListener(this);
        memoLayout.setOnClickListener(this);

        return view;
    }

    private void getDataFromMainFunc() {

        youtubeList = new ArrayList<Youtube>();

        Intent intent = getIntent();
        MovieInfo movieInfo = (MovieInfo) intent.getSerializableExtra("movieInfo");

        String url = "https://image.tmdb.org/t/p/w1280" + movieInfo.getBackdrop_path();
        Glide.with(this)
                .load(url)
                .centerCrop()
                .into(posterView);
        tvTitle.setText(movieInfo.getTitle());
        tvStory.setText(movieInfo.getOverview());
        tvYear.setText(movieInfo.getRelease_date());
        ratingBar.setRating((float) movieInfo.getVote_average() / 2);
        tvRating.setText(String.valueOf(movieInfo.getVote_average()));

        YoutubeAsyncTask mProcessTask = new YoutubeAsyncTask();

    }

    private void findViewByIdFunc() {
        posterView = findViewById(R.id.posterView);
        youtube = findViewById(R.id.youtube);
        ratingBar = findViewById(R.id.ratingBar);
        tvTitle = findViewById(R.id.tvTitle);
        tvYear = findViewById(R.id.tvYear);
        tvRating = findViewById(R.id.tvRating);
        tvStory = findViewById(R.id.tvStory);
        addLayout = (LinearLayout) findViewById(R.id.addLayout);
        shareLayout = (LinearLayout) findViewById(R.id.shareLayout);
        memoLayout = (LinearLayout) findViewById(R.id.memoLayout);
        ratingLayout = findViewById(R.id.ratingLayout);
        instaLayout = (LinearLayout) findViewById(R.id.instaLayout);
        ibMore1 = findViewById(R.id.ibMore1);
        ibMore2 = findViewById(R.id.ibMore2);
        addBtn = findViewById(R.id.addBtn);
        shareBtn = findViewById(R.id.shareBtn);
        tvMemo = findViewById(R.id.tvMemo);
        recyclerReview = findViewById(R.id.recyclerReview);
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
                    ibMore2.setImageResource(R.drawable.movie_less);
                    ib2flag = false;
                } else {
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
                DialogMemo dialog = new DialogMemo(this);
                dialog.show();
                break;
            case R.id.ratingLayout:


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
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        RatingBar ratingBar = new RatingBar(this);
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

    public class YoutubeAsyncTask {

    }

    public void playVideo(final String videoId, YouTubePlayerView youTubeView) {
        Log.d("Youtube", "trailer: " + videoId);
        youTubeView.initialize("AIzaSyBNNlLJGRmxNaNa-0RjsG6jdZrlnTnJjzE",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    private void storyDetail() {
        tvStory.setMaxLines(Integer.MAX_VALUE);
        tvStory.post(() -> {
            int lineCnt = tvStory.getLineCount();
            if (lineCnt < 6) {
                ibMore1.setVisibility(View.GONE);
            } else {
                tvStory.setMaxLines(5);
                ibMore1.setVisibility(View.VISIBLE);
            }
        });
    }

    private void reviewListFunc() {
        final NaverMovieRepository naverMovieRepository = NaverMovieRepository.getInstance();

        //개봉년도 구하기 (연도만 추출)
        String releaseDate = movieInfo.getRelease_date();
        String releaseYear = releaseDate.substring(0, 4);
        //네이버 영화api 검색실행(한국,미국간 개봉일 차이때문에 시작하는 검색 시작하는 년도는 한국 개봉년도에-1을 해줌)
        naverMovieRepository.getMovieResult(context, movieInfo.getTitle(), "" + (Integer.parseInt(releaseYear) - 1), releaseYear
                , new OnGetNaverMovieCallback() {
                    @Override
                    public void onSuccess(NaverMovie.ItemsBean movieItem) {
                        Log.d("네이버", movieItem.getLink());
                        //영화 기본페이지 링크
                        String basicLink = movieItem.getLink();
                        //아이디만 추출
                        String movieId = basicLink.replaceAll("[^0-9]", "");
                        //댓글 프래그먼트 로드
//                        FrgMovieComments dialog = (FrgMovieComments.newInstance(movieId, 1));
//                        dialog.show(((MainActivity) mContext).getSupportFragmentManager(), null);

                    }

                    @Override
                    public void onError(Boolean research) {
                        //개봉년도 포함하지 않고 재검색
                        if (research) {
                            naverMovieRepository.researchWithoutYearParams(context, movieInfo.getTitle()
                                    , new OnGetNaverMovieCallback() {
                                        @Override
                                        public void onSuccess(NaverMovie.ItemsBean movieItem) {
                                            Log.d("네이버", movieItem.getLink());
                                            //영화 기본페이지 링크
                                            String basicLink = movieItem.getLink();
                                            //아이디만 추출
                                            String movieId = basicLink.replaceAll("[^0-9]", "");
                                            //댓글 프래그먼트 로드
//                                            FrgMovieComments dialog = (FrgMovieComments.newInstance(movieId, 1));
//                                            dialog.show(((MainActivity) context).getSupportFragmentManager(), null);
                                        }

                                        @Override
                                        public void onError(Boolean research) {
                                            Toast.makeText(context, "네이버 댓글 로드에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else
                            Toast.makeText(context, "네이버 댓글 로드에 실패하였습니다", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}