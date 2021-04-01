package com.example.movieapp;

import android.Manifest;
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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.fragment.FragmentNowPlaying;
import com.example.movieapp.fragment.FragmentPopular;
import com.example.movieapp.fragment.FragmentTopRated;
import com.example.movieapp.fragment.FragmentMiddleMenu;
import com.example.movieapp.fragment.FragmentProfile;
import com.example.movieapp.fragment.FragmentSearch;
import com.example.movieapp.fragment.FragmentTopBanner;
import com.example.movieapp.fragment.FragmentUpcoming;

import java.util.ArrayList;

import MovieInfoDAO.TMDBDAO;
import adapter.BottomRecyclerViewAdapter;
import adapter.MiddleAdapter;
import adapter.TopRecyclerViewAdapter;
import model.MovieInfo;

public class MainActivity extends AppCompatActivity {
    public Object setFragmentSearchView;
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
    private Button btnSearch, btnProfile, btnLikeList, btnProfileLogout;
    private TextView tvProfileName, tvProfileEmail;
    private ImageView crIvProfilePicture, ivbDrawerBack;
    private SearchView searchView;
    private FragmentSearch fragmentSearch;
    private FragmentProfile fragmentProfile;
    private long lastTimeBackPressed;
    //하단부 영화포스터 그리드뷰==========================
    private static final int NOW_PLAYING = 1;
    private static final int POPULAR = 2;
    private static final int UPCOMING = 3;
    private static final int TOP_RATED = 4;
    private int nowPlayingCount = 1;
    private int popularCount = 1;
    private int upcomingCount = 1;
    private int topRatedCount = 1;
    private RecyclerView recyclerView;
    private FrameLayout bottom_frameLayout;
    private BottomRecyclerViewAdapter adapter_nowPlaying;
    private BottomRecyclerViewAdapter adapter_popular;
    private BottomRecyclerViewAdapter adapter_upcoming;
    private BottomRecyclerViewAdapter adapter_topRated;
    private TMDBDAO tmdbdao;
    private ArrayList<MovieInfo> movieList;
    private FragmentNowPlaying fragmentNowPlaying;
    private FragmentPopular fragmentPopular;
    private FragmentTopRated fragmentTopRated;
    private FragmentUpcoming fragmentUpcoming;
    private String[] tagList = {"now_playing", "popular", "upcoming", "top_rated"};
    //중단 장르 멤버변수=================================
    private String[] middleList = {"현재상영작", "인기영화", "개봉예정작", "명예의 전당"};
    private MiddleAdapter middleAdapter;
    private FragmentMiddleMenu fragmentMiddleMenu;


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

        //중단 메뉴버튼 메소드
        middleMenuFunc();

        //메인 하부 그리드뷰 제작메소드(현재상영작으로 시작)
        movieListFunc(NOW_PLAYING);

        //드로어 화면 전환
        setSearchFragment(false);

        //이벤트
        eventHandler();

    }


    private void middleMenuFunc() {
        //중단 메뉴탭 어댑터 제작 및 탑재 후 전송
        middleAdapter = new MiddleAdapter(this, middleList);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle(1);
        fragmentMiddleMenu = new FragmentMiddleMenu();
        bundle.putParcelable("adapter", middleAdapter);
        fragmentMiddleMenu.setArguments(bundle);
        ft.replace(R.id.linearLayout_middle, fragmentMiddleMenu);
        ft.commit();
    }

    //하단 그리드뷰 제작 메서드
    public void movieListFunc(int tagNumber) {
        switch (tagNumber) {
            case NOW_PLAYING:
                tmdbdao = new TMDBDAO(tagList[0], nowPlayingCount);
                nowPlayingCount++;
                executeFunc(NOW_PLAYING);
                showBottomGridViewNowPlaying();
                break;

            case POPULAR:
                tmdbdao = new TMDBDAO(tagList[1], popularCount);
                popularCount++;
                executeFunc(POPULAR);
                showBottomGridViewPopular();
                break;

            case UPCOMING:
                tmdbdao = new TMDBDAO(tagList[2], upcomingCount);
                upcomingCount++;
                executeFunc(UPCOMING);
                showBottomGridViewUpcoming();
                break;

            case TOP_RATED:
                tmdbdao = new TMDBDAO(tagList[3], topRatedCount);
                topRatedCount++;
                executeFunc(TOP_RATED);
                showBottomGridViewTopRated();
                break;
            default:
                break;
        }

    }


    private void getKakaoInform() {
        Intent intent = getIntent();
        kakaoName = intent.getStringExtra("name");
        kakaoEmail = intent.getStringExtra("email");
        kakaoImage = intent.getStringExtra("image");
    }


    private void fragmentFunc() {
        //메인베너에 backdrops 삽입
        tmdbdao = new TMDBDAO(tagList[2], upcomingCount);
        tmdbdao.execute();
        upcomingList = tmdbdao.getMovieList();

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
        btnSearch = findViewById(R.id.btnSearch);
        btnProfile = findViewById(R.id.btnProfile);
        btnLikeList = findViewById(R.id.btnLikeList);
        btnProfileLogout = findViewById(R.id.btnProfileLogout);
        searchView = findViewById(R.id.searchView);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        crIvProfilePicture = findViewById(R.id.crIvProfilePicture);
        ivbDrawerBack = findViewById(R.id.ivbDrawerBack);
    }

    public void eventHandler() {

//        tvNew.setOnClickListener(v-> {
//            showBottomGridViewTransaction();
//        });
//
//        btnProfile.setOnClickListener(v -> {
//
//            Intent intent = new Intent(this, ProfileActivity.class);
//            startActivity(intent);
//        });


        //드로어 메뉴 닫기 이벤트
        ivbDrawerBack.setOnClickListener(v -> {
            drawerLayout.closeDrawer(Gravity.LEFT);
        });


        //서치뷰 클릭시 프레그먼트 이동, 버튼 visible
        searchView.setOnClickListener(v -> {
            setSearchFragment(true);
            searchView.setIconifiedByDefault(false);
            searchView.setBackgroundColor(Color.WHITE);
            btnSearch.setVisibility(View.VISIBLE);

        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        //검색 버튼 클릭시 토스트메세지 띄우기
//        btnSearch.setOnClickListener(v -> {
//
//            String query = String.valueOf(searchView.getQuery());
//            Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
//        });

        //로그아웃하기
        btnProfileLogout.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "세션만료. 로그인을 해주세요", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        middleAdapter.setOnItemClickListener((view, position) -> {
            movieListFunc(position);
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


    //드로어 화면이 열려있을 때, back버튼 이벤트
    @Override
    public void onBackPressed() {
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

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }

    }

    private void requestPermissionsFunc() {
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE},
                MODE_PRIVATE);

    }

    //NowPlaying 트랜잭션
    private void showBottomGridViewNowPlaying() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle(1);
        fragmentNowPlaying = new FragmentNowPlaying();
        bundle.putParcelable("adapter", adapter_nowPlaying);
        fragmentNowPlaying.setArguments(bundle);
        ft.replace(R.id.frameLayout_bottom, fragmentNowPlaying);
        ft.commit();
    }

    //Popular 트랜잭션
    private void showBottomGridViewPopular() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle(1);
        fragmentPopular = new FragmentPopular();
        bundle.putParcelable("adapter", adapter_popular);
        fragmentPopular.setArguments(bundle);
        ft.replace(R.id.frameLayout_bottom, fragmentPopular);
        ft.commit();
    }

    //Upcoming 트랜잭션
    private void showBottomGridViewUpcoming() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle(1);
        fragmentUpcoming = new FragmentUpcoming();
        bundle.putParcelable("adapter", adapter_upcoming);
        fragmentUpcoming.setArguments(bundle);
        ft.replace(R.id.frameLayout_bottom, fragmentUpcoming);
        ft.commit();
    }

    //Top_Rated 트랜잭션
    private void showBottomGridViewTopRated() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle(1);
        fragmentTopRated = new FragmentTopRated();
        bundle.putParcelable("adapter", adapter_topRated);
        fragmentTopRated.setArguments(bundle);
        ft.replace(R.id.frameLayout_bottom, fragmentTopRated);
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

    //어댑터 탑재기능 메소드
    private void executeFunc(int tagNumber) {
        tmdbdao.execute();
        movieList = new ArrayList<>();
        movieList = tmdbdao.getMovieList();

        switch (tagNumber) {
            case NOW_PLAYING:
                adapter_nowPlaying = new BottomRecyclerViewAdapter(MainActivity.this, movieList);
                break;
            case POPULAR:
                adapter_popular = new BottomRecyclerViewAdapter(MainActivity.this, movieList);
                break;
            case UPCOMING:
                adapter_upcoming = new BottomRecyclerViewAdapter(MainActivity.this, movieList);
                break;
            case TOP_RATED:
                adapter_topRated = new BottomRecyclerViewAdapter(MainActivity.this, movieList);
                break;
        }
    }


}