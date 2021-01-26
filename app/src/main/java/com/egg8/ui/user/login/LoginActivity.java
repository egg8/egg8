package com.egg8.ui.user.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.egg8.R;
import com.egg8.action.user.userAction;
import com.egg8.ui.user.register.JoinActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText td_email;
    private EditText td_pw;
    private Button login_btn;
    private Button google_join;
    private Button kakao_join;
    private ImageView back_key;
    private TextView join;
    private TextView find_id;
    private Context mCon;
    private Switch user_status;
    private int status = 0;
    private Activity mAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mCon = this;
        mAc = this;
        findId(this);
        listenerEvent();
    }

    private void findId(LoginActivity v){
        td_email = v.findViewById(R.id.td_email);
        td_pw = v.findViewById(R.id.td_pw);
        login_btn = v.findViewById(R.id.login_btn);
        google_join = v.findViewById(R.id.google_join);
        kakao_join = v.findViewById(R.id.kakao_join);
        back_key = v.findViewById(R.id.back_key);
        join = v.findViewById(R.id.join);
        find_id = v.findViewById(R.id.find_id);
        user_status = v.findViewById(R.id.user_status);
    }

    private void listenerEvent(){
        back_key.setOnClickListener(clickListener);
        login_btn.setOnClickListener(clickListener);
        join.setOnClickListener(clickListener);
        user_status.setOnCheckedChangeListener(changeListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back_key :
                    finish();
                    break;
                case R.id.login_btn :
                    userAction action = new userAction();
                    action.LoginAction(mAc,td_email.getText().toString(),td_pw.getText().toString(),status,"http://222.100.239.140:8888/");

                    break;
                case R.id.join :
                    Intent intent2 = new Intent(getApplicationContext(), JoinActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
        }
    };

    CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                status = 1;
            } else {
                status = 0;
            }
        }
    };

}