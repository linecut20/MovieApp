package com.example.movieapp.kakao;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.movieapp.kakao.KakaoSDKInit;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;

public class KakaoSDKAdapter extends KakaoAdapter {

    public ISessionConfig getSessionConfig() {
        return new ISessionConfig() {

            //카카오 로그인 세션을 불러올 때 설정값을 설정하는 파트
            @Override
            public AuthType[] getAuthTypes() {
                return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
            }

            @Override
            public boolean isUsingWebviewTimer() {
                return false;
            }

            @Override
            public boolean isSecureMode() {
                return false;
            }

            @Nullable
            @Override
            public ApprovalType getApprovalType() {
                return ApprovalType.INDIVIDUAL;
            }

            /*Kakao SDK에서 사용되는 WebView에서 이메일 입력폼에 데이터를
            저장할지 안할지 여부 결정
            */
            @Override
            public boolean isSaveFormData() {
                return true;
            }
        };
    }

    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {
            @Override
            public Context getApplicationContext() {
                return KakaoSDKInit.getGlobalApplicationContext();
            }
        };
    }
}
