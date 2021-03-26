package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayerView;

public class MovieInfoDetail extends Fragment implements View.OnClickListener {
    //인스타 그램 공유에 필요한 이미지
    private  static final String MEDIA_TYPE_JPEG = "image/*";
    public static final String NOT_INSTALLED = "Not installed";
    public static final String INTERNAL_ERROR = "Data conversion failed";
    public static final String NO_BASE64_IMAGE = "No base64 image";
    public static final String INVALID_PARAMETER = "Invalid parameter";

    private ImageView posterView;
    private YouTubePlayerView youtube;
    private RatingBar ratingBar;
    private TextView tvTitle, tvYear, tvRating, tvStory, reviewList;
    private LinearLayout addLayout, shareLayout, memoLayout, ratingLayout,instaLayout;
    private ImageButton ibMore1, ibMore2, addBtn, shareBtn;
    private View view;

    private boolean ib1flag = true;
    private boolean ib2flag = true;
    private boolean addflag = true;

    @Nullable
    @Override
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ibMore1 :
                if(ib1flag == true)
                {
                    tvStory.setMaxLines(Integer.MAX_VALUE);
                    ibMore1.setImageResource(R.drawable.movie_less);
                    ib1flag = false;
                }
                else
                {
                    tvStory.setMinLines(Integer.MIN_VALUE);
                    ibMore1.setImageResource(R.drawable.movie_more);
                    ib1flag = true;
                }
                break;
            case R.id.ibMore2 :
                if(ib2flag == true)
                {
                    reviewList.setMaxLines(Integer.MAX_VALUE);
                    ibMore2.setImageResource(R.drawable.movie_less);
                    ib2flag = false;
                }
                else
                {
                    reviewList.setMinLines(Integer.MIN_VALUE);
                    ibMore2.setImageResource(R.drawable.movie_more);
                    ib2flag = true;
                }
                break;
            case R.id.addLayout :
                if(addflag == true)
                {
                    addBtn.setImageResource(R.drawable.movie_added);
                    addflag = false;
                    //DB넣는거 해야함!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }
                else
                {
                    addBtn.setImageResource(R.drawable.movie_add);
                    addflag = true;
                }
                break;
            case R.id.shareLayout :

                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.example.movieapp");
                shareIntent.putExtra(Intent.EXTRA_TITLE, "제목");

                shareIntent.setType("text/plain");

                startActivity(Intent.createChooser(shareIntent, "앱을 선택하십시오."));
                break;
            case R.id.memoLayout : break;
            case R.id.ratingLayout :


                break;
//            case R.id.instaLayout :
//                // Define background and sticker asset URIs
//                Uri backgroundAssetUri = Uri.parse(options.getString("backgroundImage"));
//                Uri stickerAssetUri = Uri.parse("your-sticker-image-asset-uri-goes-here");
//                String sourceApplication = "com.my.app";
//
//                // Instantiate implicit intent with ADD_TO_STORY action,
//                // background asset, and sticker asset
//                Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
//                intent.putExtra("source_application", sourceApplication);
//
//                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.setDataAndType(backgroundAssetUri, MEDIA_TYPE_JPEG);
//                intent.putExtra("interactive_asset_uri", stickerAssetUri);
//
//                // Instantiate activity and verify it will resolve implicit intent
//                Activity activity = getActivity();
//                activity.grantUriPermission(
//                        "com.instagram.android", stickerAssetUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                if (activity.getPackageManager().resolveActivity(intent, 0) != null) {
//                    activity.startActivityForResult(intent, 0);
//                }

//                break;
        }//switch

    }
}