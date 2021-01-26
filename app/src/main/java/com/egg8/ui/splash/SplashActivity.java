package com.egg8.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.egg8.R;
import com.egg8.common.function.InterceptorLogin;
import com.egg8.ui.MainActivity;
import com.egg8.ui.calendar.CalendarActivity;
import com.egg8.ui.user.JoinActivity;
import com.egg8.ui.user.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    private ImageView splash;
    private TextView txt1;
    private TextView txt2;
    private Animation transUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        findId(this);
        startLoading(5000);
    }
    private void loadAnim(Context context){
        transUp = AnimationUtils.loadAnimation(context, R.anim.translate_up);
        animLoading1(500);
    }
    private void findId(Activity v){
        splash = v.findViewById(R.id.splash);
        txt1 = v.findViewById(R.id.txt1);
        txt2 = v.findViewById(R.id.txt2);
        loadAnim(v);
    }
    private void startLoading(long loading){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InterceptorLogin.checkLoginStatus(getBaseContext());
            }
        },loading);
    }
    private void animLoading1(long loading){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splash.setVisibility(View.VISIBLE);
                splash.startAnimation(transUp);
                animLoading2(800);
            }
        },loading);
    }
    private void animLoading2(long loading){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txt1.setVisibility(View.VISIBLE);
                txt1.startAnimation(transUp);
                animLoading3(800);
            }
        },loading);
    }
    private void animLoading3(long loading){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txt2.setVisibility(View.VISIBLE);
                txt2.startAnimation(transUp);
            }
        },loading);
    }
}