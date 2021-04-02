
package com.example.movieapp;

import android.Manifest;
import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
//import android.widget.SearchView;
import androidx.appcompat.widget.SearchView;

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
import java.util.List;

import MovieInfoDAO.TMDBDAO;
import adapter.BottomRecyclerViewAdapter;
//import adapter.MiddleAdapter;
import adapter.SearchAdapter;
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
    private Button btnSearch, btnProfile, btnLikeList, btnProfileLogout;
    private TextView tvProfileName, tvProfileEmail;
    private ImageView crIvProfilePicture, ivbDrawerBack;
    private SearchView searchView;
    private FragmentSearch fragmentSearch;
    private FragmentProfile fragmentProfile;

    public static ArrayList<MovieInfo> searchDataList = new ArrayList<>();
    public SearchAdapter searchAdapter;
    private SearchContent searchContent;

    private androidx.appcompat.widget.SearchView.OnQueryTextListener queryTextListener;

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
        //btnSearch = findViewById(R.id.btnSearch);
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

        //드로어 메뉴 닫기 이벤트
        ivbDrawerBack.setOnClickListener(v -> {
            drawerLayout.closeDrawer(Gravity.LEFT);
        });

        //검색버튼
        searchView.setSubmitButtonEnabled(true);

        //서치뷰 클릭시 프레그먼트 이동
        searchView.setOnClickListener(v->{
            setSearchFragment(true);
        });




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                findMovieFunc(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return true;
            }
        });


        //로그아웃하기
        btnProfileLogout.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "세션만료. 로그인을 해주세요", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        //중단메뉴버튼 이벤트
        btnNowPlaying.setOnClickListener(v -> {
            showBottomGridViewNowPlaying();
        });

        btnPopular.setOnClickListener(v -> {
            showBottomGridViewPopular();
        });

        btnUpcoming.setOnClickListener(v -> {
            showBottomGridViewUpcoming();
        });

        btnToprated.setOnClickListener(v -> {
            showBottomGridViewTopRated();
        });
    }

    /*

        private String getResult(){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0;i<searchDataList.size();i++){
                String item = String.valueOf(searchDataList.get(i));
                stringBuilder.append(item);
                if (i != searchDataList.size()-1){
                    stringBuilder.append("\n");
                }
            }
            return stringBuilder.toString();
        }

        private String search(String query){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0;i<searchDataList.size();i++){
                String item = String.valueOf(searchDataList.get(i));
                stringBuilder.append(item);
                if (item.toLowerCase().contains(query.toLowerCase())){
                    stringBuilder.append(item);
                    if (i!=searchDataList.size()-1){
                        stringBuilder.append("\n");
                    }
                }
            }
            return stringBuilder.toString();
        }
    */


    //검색시 입력받은 영화가 있는지 찾아주는 함수
    public void findMovieFunc(String s) {

        ArrayList<MovieInfo> movieInfoArrayList = new ArrayList<MovieInfo>();
        movieInfoArrayList = tmdbdao.getMovieList();

        String query = String.valueOf(searchView.getQuery());

        for (MovieInfo mi : movieInfoArrayList) {

            if (mi.getTitle().equals(query)) {

                searchDataList.add(mi);
            }
        }
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
//        searchView.onActionViewExpanded(); //바로 검색 할 수 있도록
//
//
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
//            queryTextListener = new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    Log.i("onQueryTextChange", newText);
//
//                    searchAdapter.setFilter(fragmentSearch.filter(searchContent.getNoticeList(), newText));
//                    return true;
//                }
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    Log.i("onQueryTextSubmit", query);
//
//                    return true;
//                }
//            };
//            searchView.setOnQueryTextListener(queryTextListener);
//        }
//
//        return true;
//    }


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