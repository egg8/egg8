package com.egg8.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.egg8.R;
import com.egg8.ui.user.JoinActivity;
import com.egg8.ui.user.LoginActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private MaterialButton login_btn,join_btn;
    private Activity mAc;
    private Context mCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mAc = this;
        mCon = this;
        findId(mAc);
    }

    private void findId(Activity v){
        login_btn = v.findViewById(R.id.login_btn);
        join_btn = v.findViewById(R.id.join_btn);
        btnListener();
    }

    private void btnListener(){
        login_btn.setOnClickListener(clickListener);
        join_btn.setOnClickListener(clickListener);
    }

    private void changeIntent(int i){
        Intent intent;
        if (i==0) {
            intent = new Intent(mCon, LoginActivity.class);
        } else {
            intent = new Intent(mCon, JoinActivity.class);
        }
        startActivity(intent);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn:
                    changeIntent(0);
                    break;
                case  R.id.join_btn:
                    changeIntent(1);
                    break;
            }
        }
    };
}