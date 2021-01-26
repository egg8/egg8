package com.egg8.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.egg8.R;
import com.egg8.ui.user.login.LoginActivity;
import com.egg8.ui.user.register.JoinActivity;

public class IntroActivity extends AppCompatActivity {

    Button login_btn,join_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        login_btn=findViewById(R.id.login_btn);
        join_btn=findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, JoinActivity.class);
                startActivity(intent);

            }
        });
    }
}