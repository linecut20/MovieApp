package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.example.movieapp.fragment.FragmentProfile;
import com.example.movieapp.fragment.FragmentSearch;

import java.util.List;
import java.util.Stack;

import com.example.movieapp.fragment.FragmentBottomPoster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Array;
import java.util.ArrayList;

import adapter.FragmentBannerAdapter;

import adapter.MainRecyclerViewAdapter;
import me.relex.circleindicator.CircleIndicator3;
import model.MovieInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    //메인배너 멤버변수==================================
    private ViewPager2 viewPager2;
    private CircleIndicator3 indicator;
    private FragmentStateAdapter pageAdapter;
    private int pageNumber = 5;
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
    private boolean flag = false;
    private long lastTimeBackPressed;
    //하단부 영화포스터 그리드뷰==========================
    private RecyclerView recyclerView;
    private FrameLayout bottom_frameLayout;
    private MainRecyclerViewAdapter adapter;
    private ArrayList<MovieInfo> movieList = new ArrayList<>();
    private FragmentBottomPoster fragmentBottomPoster = new FragmentBottomPoster();
    //중단 장르 멤버변수=================================
    private TextView tvNew;



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

        //이벤트
        eventHandler();

    }

    private void movieListFunc() {
        MyAsyncTask mAsyncTask = new MyAsyncTask();
        mAsyncTask.execute();

        //어댑터 탑재
        adapter = new MainRecyclerViewAdapter(MainActivity.this, movieList);

        showBottomGridViewTransaction();
    }




    private void getKakaoInform() {
        Intent intent = getIntent();
        kakaoName = intent.getStringExtra("name");
        kakaoEmail = intent.getStringExtra("email");
        kakaoImage = intent.getStringExtra("image");
    }


    private void fragmentFunc() {
        //어댑터 및 인디케이터 정의
        pageAdapter = new FragmentBannerAdapter(this, pageNumber);
        viewPager2.setAdapter(pageAdapter);
        indicator.setViewPager(viewPager2);
        indicator.createIndicators(pageNumber,0);

        //뷰페이저 orientation 설정
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        viewPager2.setCurrentItem(100);
        viewPager2.setOffscreenPageLimit(4);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            //메인베너가 스크롤될 경우(좌<->우)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(positionOffset == 0) {
                    viewPager2.setCurrentItem(position);
                }
            }

            //선택될 경우
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicator.animatePageSelected(position % pageNumber);
            }
        });

        final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        viewPager2.setPageTransformer((page, position) -> {
            float myOffset = position * -(2*pageOffset + pageMargin);
            if(viewPager2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                if(ViewCompat.getLayoutDirection(viewPager2) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.setTranslationX(-myOffset);
                } else {
                    page.setTranslationX(-myOffset);
                }
            } else {
                page.setTranslationX(myOffset);
            }
        });
    }

    private void findViewByIdFunc() {
        viewPager2 = findViewById(R.id.viewPager2);
        indicator = findViewById(R.id.indicator);
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

        //드로어 메뉴 인텐트 이벤트
        btnLikeList.setOnClickListener(v->{

            Intent intent = new Intent(this,LikeActivity.class);
            startActivity(intent);
        });

        tvNew.setOnClickListener(v-> {
            showBottomGridViewTransaction();
        });

        btnProfile.setOnClickListener(v -> {

            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });

        ivBack.setOnClickListener(v -> {
            drawerLayout.closeDrawer(Gravity.LEFT);
        });

        searchBar.setOnClickListener(v -> {
            flag = true;

            setSearchFragment();
            searchBar.setIconified(false);
            btnSearch.setVisibility(View.VISIBLE);

        });

        btnSearch.setOnClickListener(v -> {

            String quety = String.valueOf(searchBar.getQuery());
            Toast.makeText(getApplicationContext(), quety, Toast.LENGTH_SHORT).show();
        });


    }


    //드로어 화면 전환하기
    private void setSearchFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentSearch = new FragmentSearch();
        fragmentProfile = new FragmentProfile();

        if (flag ==true ){
            fragmentTransaction.replace(R.id.frmDrawer, fragmentSearch);

        }else{
            fragmentTransaction.replace(R.id.frmDrawer, fragmentProfile);
        }
        fragmentTransaction.commit();
        flag = false;
    }



    //back 버튼 프레그먼트 종료,앱 종료하기
    @Override
    public void onBackPressed() {
        //fragment 종료하기
        /*List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null){
            for (Fragment ft : fragmentList) {
                if (ft instanceof onBackPressedListener) {
                    ((onBackPressedListener) ft).onBackPressed();
                }
            }
        }*/

        //두 번 클릭시 어플 종료
        if (System.currentTimeMillis() - lastTimeBackPressed < 2000) {
            finish();
            return;
        }
        lastTimeBackPressed = System.currentTimeMillis();
        Toast.makeText(this, "버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }

    private void requestPermissionsFunc() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE},
                MODE_PRIVATE);

    }

    //하단 영화포스터 그리드뷰 제작 메서드
    private class MyAsyncTask extends AsyncTask<String, Void, MovieInfo[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("\t영화정보를 가져오는 중입니다");
            //show dialog
            progressDialog.show();
        }

        @Override
        protected MovieInfo[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/upcoming?api_key=3816c409634358e152e19eb237829a50&language=ko-KR&page=1")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                MovieInfo[] posts = gson.fromJson(rootObject, MovieInfo[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(MovieInfo[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //ArrayList에 차례대로 집어 넣는다.
            if (result.length > 0) {
                for (MovieInfo p : result) {
                    movieList.add(p);
                }
            }

        }
    }

    private void showBottomGridViewTransaction() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("adapter", adapter);
        fragmentBottomPoster.setArguments(bundle);
        ft.replace(R.id.frameLayout_bottom, fragmentBottomPoster);
        ft.commit();
    }

}