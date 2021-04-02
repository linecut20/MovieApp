package com.example.movieapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DialogRating extends Dialog {

    private EditText mesgase;
    private Button okBtn, cancelBtn;
    private Context context;

    public DialogRating(MovieInfoDetail movieInfoDetail) {
        super(movieInfoDetail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_memo);

        findViewByIdFunc();

        //이벤트 처리
        eventHandler();

    }

    private void eventHandler() {
        // 버튼 리스너 설정
        okBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '확인' 버튼 클릭시
                Toast.makeText(context, "성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                // Dialog 종료
                dismiss();
            }
        });
        cancelBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '취소' 버튼 클릭시
                // 뒤로가기
                Toast.makeText(context, "메모가 취소되었습니다.", Toast.LENGTH_SHORT).show();
                // LoginDialog 종료
                dismiss();
            }
        });
    }

    private void findViewByIdFunc() {
        mesgase = findViewById(R.id.mesgase);
        okBtn = findViewById(R.id.okBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
    }
}