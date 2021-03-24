package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;

import com.example.movieapp.fragment.FragmentBannerAdapter;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {
    //메인배너 멤버변수==================================
    private ViewPager2 viewPager2;
    private CircleIndicator3 indicator;
    private FragmentStateAdapter pageAdapter;
    private int pageNumber = 5;
    //==================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //접근권한요청
        requestPermissionsFunc();

        findViewByIdFunc();
        fragmentFunc();
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
    }

    private void requestPermissionsFunc() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE},
                MODE_PRIVATE);

    }
}