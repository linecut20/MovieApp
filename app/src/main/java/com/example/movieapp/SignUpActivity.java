package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    // 리퀘스트코드
    private static final int PICK_FROM_CAMERA = 2;
    private static final int PICK_FROM_ALBUM = 1;

    String TAG = "파일복사";

    //Shared Preference 키값
    private static final String USER = "User", INIT = "init";

    private Context mContext;
    private View view;

    private ImageView profileImage;
    private EditText edtName;
    private EditText edtDescription;

    private File tempFile;
    private File copyFile;
    private String profileImgPath;






    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( profileImage_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "profileImage_" + timeStamp + "_";

        Log.d(TAG, "이미지저장" + imageFileName);
        // 이미지가 저장될 폴더 이름
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/ProfileImage/");

        // 프로필 이미지 바꿀때 안에 있는 파일 지워줌. 용량 차지하지 못하게 한다.
        if (storageDir.exists()) {
            File[] fileList = storageDir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList.length == 0) {
                    break;
                }
                fileList[i].delete();
            }
            //storageDir.delete();
        } else if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        //이미지경로저장
        profileImgPath = image.getAbsolutePath();
        return image;
    }


    // 각도를 조절해서 다시 만든 비트맵
    private Bitmap rotate(Bitmap bitmap, int exifDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(exifDegree);
        Bitmap tempBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
        return tempBitmap;
    }

    private int exifOrientationToDegree(int exifOrientation) {

        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
        }
        return 0;
    }

}