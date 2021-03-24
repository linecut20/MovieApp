package com.example.movieapp.kakao;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.kakao.auth.KakaoSDK;

import java.security.MessageDigest;

public class KakaoSDKInit extends Application {
    private static volatile KakaoSDKInit instance = null;
//
//    private void HashKey() {
//        try {
//            PackageInfo pkinfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : pkinfo.signatures) {
//                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
//                messageDigest.update(signature.toByteArray());
//                String result = new String(Base64.encode(messageDigest.digest(), 0));
//                Log.d("해시", result);
//            }
//        }
//        catch (Exception e) {
//        }
//    }

    public static KakaoSDKInit getGlobalApplicationContext() {
        if(instance == null) {
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
