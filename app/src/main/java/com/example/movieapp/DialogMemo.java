package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DialogMemo extends AppCompatActivity {

    private Context context;

    public DialogMemo(Context context){
        this.context = context;
    }

    public void callFunction(final TextView tvMemo){
        final Dialog dig = new Dialog(context);

        //다이얼로그 메모의 레이아웃 설정
        dig.setContentView(R.layout.activity_dialog_memo);
        //다이얼로그 열기
        dig.show();

        // 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dig.findViewById(R.id.message);
        final Button okBtn = (Button) dig.findViewById(R.id.okBtn);
        final Button cancelBtn = (Button) dig.findViewById(R.id.cancelBtn);

        okBtn.setOnClickListener((view) -> {
                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 tvMemo에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                tvMemo.setText(message.getText().toString());
                Toast.makeText(context, "\"" +  message.getText().toString() + "\" 을 입력하였습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dig.dismiss();
        });
        cancelBtn.setOnClickListener((view) -> {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dig.dismiss();
        });
    }
}