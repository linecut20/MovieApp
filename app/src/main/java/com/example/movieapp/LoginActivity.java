package com.example.movieapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private SessionCallback sessionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //해시키 추출
        getAppKeyHash();

        findViewByIdFunc();

        loginEventHandler();

    }

    private void loginEventHandler() {
        btnLogin.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "아직 기능 준비중!", Toast.LENGTH_SHORT).show();
        });

    }

    private void findViewByIdFunc() {
        btnLogin = findViewById(R.id.btnLogin);
        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
        //토큰이 아직 유효하다면 바로 로그인처리
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    //카카오 로그인 세션 인터페이스 구현
    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                //로그인 시도후 각 상황에 따른 정의
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();
                    if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        kakaoMessage("네트워크가 불안정합니다. 잠시 후 다시 시도하세요");
                        finish();
                    } else {
                        kakaoMessage("로그인 도중 오류가 발생했습니다"+errorResult.getErrorMessage());
                    }
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("name",result.getNickname());
                    intent.putExtra("profile",result.getProfileImagePath());
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    kakaoMessage("세션이 종료되었습니다. 다시 시도하세요.");
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            kakaoMessage("세션을 열지 못했습니다. 잠시 후 다시 시도하세요");
        }
    }

    public void kakaoMessage(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("해시키", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }
}