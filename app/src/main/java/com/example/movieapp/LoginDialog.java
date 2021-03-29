package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LoginDialog extends Dialog {

    private EditText edtName, edtEmail;
    private Button btnCancel, btnLoginDialog;
    private Context context;


    public LoginDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public LoginDialog(LoginActivity loginActivity) {
        super(loginActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dialog);

        // Dialog Background TRANSPARENT로 변환
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 로그인 다이얼로그의 각 위젯들 정의
        findViewByIdFunc();

        //이벤트 처리
        eventHandler();

    }

    private void eventHandler() {
        // 버튼 리스너 설정
        btnLoginDialog.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '확인' 버튼 클릭시
                // 회원가입 이벤트
                Toast.makeText(context, edtName.getText().toString()+"님 가입을 축하합니다.", Toast.LENGTH_SHORT).show();
                // LoginDialog 종료
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '취소' 버튼 클릭시
                // 뒤로가기
                // LoginDialog 종료
                dismiss();
            }
        });

    }

    private void findViewByIdFunc() {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        btnCancel = findViewById(R.id.btnCancel);
        btnLoginDialog = findViewById(R.id.btnLoginDialog);
    }
}