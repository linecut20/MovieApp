
package com.example.movieapp;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.fragment.FragmentNowPlaying;
import com.example.movieapp.fragment.FragmentPopular;
import com.example.movieapp.fragment.FragmentTopRated;
import com.example.movieapp.fragment.FragmentProfile;
import com.example.movieapp.fragment.FragmentSearch;
import com.example.movieapp.fragment.FragmentTopBanner;
import com.example.movieapp.fragment.FragmentUpcoming;

import java.util.ArrayList;

import MovieInfoDAO.TMDBDAO;
import adapter.BottomRecyclerViewAdapter;
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
    private RecyclerView recyclerView;
    private FrameLayout bottom_frameLayout;
    private TMDBDAO tmdbdao;
    private ArrayList<MovieInfo> movieList;
    private FragmentNowPlaying fragmentNowPlaying;
    private FragmentPopular fragmentPopular;
    private FragmentTopRated fragmentTopRated;
    private FragmentUpcoming fragmentUpcoming;
    //중단 장르 멤버변수=================================
    private Button btnNowPlaying, btnPopular, btnUpcoming, btnToprated;


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

        //드로어 화면 전환
        setSearchFragment(false);
        //이벤트
        eventHandler();

        //초기화면 지정하기
        btnNowPlaying.performClick();
        topRecyclerViewAdapter.notifyDataSetChanged();



    }

    private void getKakaoInform() {
        Intent intent = getIntent();
        kakaoName = intent.getStringExtra("name");
        kakaoEmail = intent.getStringExtra("email");
        kakaoImage = intent.getStringExtra("image");
    }


    private void fragmentFunc() {
        //메인베너에 backdrops 삽입
        tmdbdao = new TMDBDAO("upcoming", 1);
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
        //중단 메뉴버튼===============================
        btnNowPlaying = findViewById(R.id.btnNowPlaying);
        btnPopular = findViewById(R.id.btnPopular);
        btnUpcoming = findViewById(R.id.btnUpcoming);
        btnToprated = findViewById(R.id.btnToprated);
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

        //중단메뉴버튼 이벤트
        btnNowPlaying.setOnClickListener(v-> {
            showBottomGridViewNowPlaying();
        });

        btnPopular.setOnClickListener(v-> {
            showBottomGridViewPopular();
        });

        btnUpcoming.setOnClickListener(v-> {
            showBottomGridViewUpcoming();
        });

        btnToprated.setOnClickListener(v-> {
            showBottomGridViewTopRated();
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
        fragmentNowPlaying = new FragmentNowPlaying(MainActivity.this);
        ft.replace(R.id.frameLayout_bottom, fragmentNowPlaying);
        ft.commit();
    }

    //Popular 트랜잭션
    private void showBottomGridViewPopular() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragmentPopular = new FragmentPopular(MainActivity.this);
        ft.replace(R.id.frameLayout_bottom, fragmentPopular);
        ft.commit();
    }

    //Upcoming 트랜잭션
    private void showBottomGridViewUpcoming() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragmentUpcoming = new FragmentUpcoming(MainActivity.this);
        ft.replace(R.id.frameLayout_bottom, fragmentUpcoming);
        ft.commit();
    }

    //Top_Rated 트랜잭션
    private void showBottomGridViewTopRated() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragmentTopRated = new FragmentTopRated(MainActivity.this);
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

}