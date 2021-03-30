package com.example.movieapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieapp.fragment.FragmentBottomPoster;
import com.example.movieapp.fragment.FragmentProfile;
import com.example.movieapp.fragment.FragmentSearch;
import com.example.movieapp.fragment.FragmentTopBanner;

import java.util.ArrayList;

import MovieInfoDAO.TopRatedTMDBDAO;
import MovieInfoDAO.UpcomingTMDBDAO;
import adapter.BottomRecyclerViewAdapter;
import adapter.TopRecyclerViewAdapter;
import me.relex.circleindicator.CircleIndicator3;
import model.MovieInfo;

public class MainActivity extends AppCompatActivity {
    //메인배너 멤버변수==================================
    private int pageNumber = 5;
    private ArrayList<MovieInfo> upcomingList;
    private TopRecyclerViewAdapter topRecyclerViewAdapter;
    private FragmentTopBanner fragmentTopBanner = new FragmentTopBanner();
    //==================================================
    private String kakaoName;
    private String kakaoEmail;
    private String kakaoImage;
    //드로어배너 멤버변수=================================
    private FrameLayout frmDrawer, frmProfile, frmSearch;
    private DrawerLayout drawerLayout;
    private LinearLayout linearDrawer;
    private Button btnSearch, btnProfile, btnLikeList;
    private TextView tvProfileName, tvProfileEmail;
    private ImageView ivProfilePicture, ivBack;
    private SearchView searchBar;
    private FragmentSearch fragmentSearch;
    private FragmentProfile fragmentProfile;
    private long lastTimeBackPressed;
    //하단부 영화포스터 그리드뷰==========================
    private RecyclerView recyclerView;
    private FrameLayout bottom_frameLayout;
    private BottomRecyclerViewAdapter adapter;
    private ArrayList<MovieInfo> movieList = new ArrayList<>();
    private FragmentBottomPoster fragmentBottomPoster = new FragmentBottomPoster();
    //중단 장르 멤버변수=================================
    private FrameLayout frameLayout_middle;


    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //접근권한요청
        requestPermissionsFunc();

        //아이디값 배정
        findViewByIdFunc();

        //카카오 로그인 정보수집 메소드
        getKakaoInform();

        //상위 메인배너 메소드
        fragmentFunc();

        //메인 하부 그리드뷰 제작메소드
        movieListFunc();

        //드로어 화면 전환
        setSearchFragment(false);

        //이벤트
        eventHandler();

    }


    private void movieListFunc() {
        //로딩화면
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("\t영화정보를 가져오는 중입니다");
        //show dialog
        progressDialog.show();

        TopRatedTMDBDAO tmdbdao = new TopRatedTMDBDAO();
        tmdbdao.execute();
        movieList = tmdbdao.getMovieList();

        //어댑터 탑재
        adapter = new BottomRecyclerViewAdapter(MainActivity.this, movieList);

        //로딩화면 종료
        progressDialog.dismiss();
        showBottomGridViewTransaction();
    }


    private void getKakaoInform() {
        Intent intent = getIntent();
        kakaoName = intent.getStringExtra("name");
        kakaoEmail = intent.getStringExtra("email");
        kakaoImage = intent.getStringExtra("image");
    }


    private void fragmentFunc() {
        //메인베너에 backdrops 삽입
        UpcomingTMDBDAO upcomingTMDBDAO = new UpcomingTMDBDAO();
        upcomingTMDBDAO.execute();
        upcomingList = upcomingTMDBDAO.getList();

        //어댑터 탑재
        topRecyclerViewAdapter = new TopRecyclerViewAdapter(this, upcomingList);
        showTopLayoutViewTransaction();
    }



    private void findViewByIdFunc() {
        //드로어 멤버변수=============================
        frmDrawer = findViewById(R.id.frmDrawer);
        frmProfile = findViewById(R.id.frmProfile);
        frmSearch = findViewById(R.id.frmSearch);
        drawerLayout = findViewById(R.id.drawerLayout);
        searchBar = findViewById(R.id.searchBar);
        btnSearch = findViewById(R.id.btnSearch);
        btnProfile = findViewById(R.id.btnProfile);
        btnLikeList = findViewById(R.id.btnLikeList);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        ivBack = findViewById(R.id.ivBack);
    }

    public void eventHandler() {
//
//        //드로어 메뉴 인텐트 이벤트
//        btnLikeList.setOnClickListener(v->{
//
//            Intent intent = new Intent(this,LikeActivity.class);
//            startActivity(intent);
//        });
//
//        tvNew.setOnClickListener(v-> {
//            showBottomGridViewTransaction();
//        });
//
//        btnProfile.setOnClickListener(v -> {
//
//            Intent intent = new Intent(this, ProfileActivity.class);
//            startActivity(intent);
//        });

        ivBack.setOnClickListener(v -> {
            drawerLayout.closeDrawer(Gravity.LEFT);
        });

        searchBar.setOnClickListener(v -> {
            setSearchFragment(true);
            searchBar.setIconifiedByDefault(false);
            searchBar.setBackgroundColor(Color.WHITE);
            btnSearch.setVisibility(View.VISIBLE);

        });
        searchBar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setSearchFragment(false);
                searchBar.setIconifiedByDefault(true);
                btnSearch.setVisibility(View.INVISIBLE);
                return true;
            }
        });


        btnSearch.setOnClickListener(v -> {

            String quety = String.valueOf(searchBar.getQuery());
            Toast.makeText(getApplicationContext(), quety, Toast.LENGTH_SHORT).show();
        });


    }


    //드로어 화면 전환하기
    private void setSearchFragment(Boolean flag) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentSearch = new FragmentSearch();
        fragmentProfile = new FragmentProfile();

        if (flag == true) {
            fragmentTransaction.replace(R.id.frmDrawer, fragmentSearch);

        } else {
            fragmentTransaction.replace(R.id.frmDrawer, fragmentProfile);
        }
        fragmentTransaction.commit();
        flag = false;
    }


    //back 버튼 프레그먼트 종료,앱 종료하기
//    @Override
//    public void onBackPressed() {
//        //fragment 종료하기
//        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
//        for(Fragment fragment : fragmentList){
//            if(fragment instanceof onBackPressedListener){
//                ((onBackPressedListener)fragment).onBackPressed();
//                return;
//            }
//        }
//
//        //두 번 클릭시 어플 종료
//        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
//            finish();
//            return;
//        }
//        lastTimeBackPressed = System.currentTimeMillis();
//        Toast.makeText(this,"'뒤로' 버튼을 한 번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
//
//    }

    private void requestPermissionsFunc() {
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE},
                MODE_PRIVATE);

    }

    //bottom 화면전환 트랜잭션 구간
    private void showBottomGridViewTransaction() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("adapter", adapter);
        fragmentBottomPoster.setArguments(bundle);
        ft.replace(R.id.frameLayout_bottom, fragmentBottomPoster);
        ft.commit();
    }

    //top 화면전환 트랙잭션 구간
    private void showTopLayoutViewTransaction() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("adapter", topRecyclerViewAdapter);
        fragmentTopBanner.setArguments(bundle);
        ft.replace(R.id.mainFrameLayout, fragmentTopBanner);
        ft.commit();
    }

}