package com.egg8.ui.user;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.egg8.R;


public class SettingActivity extends AppCompatActivity {


    TextView btn_password_change,btn_reslist_view,btn_enroll_shop,btn_logout,btn_withdrawal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_password_change=findViewById(R.id.btn_password_change); //비밀번호 변경
        btn_reslist_view=findViewById(R.id.btn_reslist_view); //예약 리스트 보기
        btn_enroll_shop=findViewById(R.id.btn_enroll_shop); //샵 등록
        btn_logout=findViewById(R.id.btn_logout);
        btn_withdrawal=findViewById(R.id.btn_withdrawal); //회원탈퇴



        btn_enroll_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,EnrollActivity.class);
                startActivity(intent);
            }
        });



    }
}
