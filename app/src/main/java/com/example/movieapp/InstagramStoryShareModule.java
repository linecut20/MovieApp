//package com.example.movieapp;
//
//import android.app.Activity;
//import android.content.ActivityNotFoundException;
//import android.content.Intent;
//import android.content.pm.ResolveInfo;
//import android.net.Uri;
//import android.support.annotation.Nullable;
//
//import com.facebook.react.bridge.Callback;
//import com.facebook.react.bridge.ReactApplicationContext;
//import com.facebook.react.bridge.ReactContextBaseJavaModule;
//import com.facebook.react.bridge.ReactMethod;
//import com.facebook.react.bridge.ReadableMap;
//
//import java.io.File;
//
//public class RNInstagramStoryShareModule extends ReactContextBaseJavaModule {
//
//    ReactApplicationContext reactContext;
//
//    public static final String NOT_INSTALLED = "Not installed";
//    public static final String INTERNAL_ERROR = "Data conversion failed";
//    public static final String NO_BASE64_IMAGE = "No base64 image";
//    public static final String INVALID_PARAMETER = "Invalid parameter";
//    private static final String MEDIA_TYPE_JPEG = "image/*";
//
//    public RNInstagramStoryShareModule(ReactApplicationContext reactContext) {
//        super(reactContext);
//        this.reactContext = reactContext;
//    }
//
//    @Override
//    public String getName() {
//        return "RNInstagramStoryShare";
//    }
//
//    @ReactMethod
//    public void share(ReadableMap options, @Nullable Callback failureCallback, @Nullable Callback successCallback) {
//        try {
//            // Define image asset URI and attribution link URL
//            File imageFileToShare = new File("/storage/emulated/0/DCIM/Camera/IMG_20180530_212925.jpg");
//            Uri backgroundAssetUri = Uri.fromFile(imageFileToShare);//options.getString("backgroundImage"));
//            String attributionLinkUrl = options.getString("deeplinkUrl");
//
//
//            // Instantiate implicit intent with ADD_TO_STORY action,
//            // background asset, and attribution link
//            Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
//            intent.setDataAndType(backgroundAssetUri, MEDIA_TYPE_PNG);
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.putExtra("content_url", attributionLinkUrl);
//
//            // Instantiate activity and verify it will resolve implicit intent
//            Activity activity = getCurrentActivity();
//            ResolveInfo should = activity.getPackageManager().resolveActivity(intent, 0);
//            if (should != null) {
//                activity.startActivityForResult(intent, 0);
//                successCallback.invoke("OK");
//            } else {
//                successCallback.invoke("OK, but not opened");
//            }
//
//
//
//
//        } catch(ActivityNotFoundException ex) {
//            System.out.println("ERROR");
//            System.out.println(ex.getMessage());
//            failureCallback.invoke(ex);
//        }
//    }
//
//}
