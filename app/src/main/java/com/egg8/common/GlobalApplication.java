package com.egg8.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.google.android.material.snackbar.Snackbar;

/**
 * @author  : 김재일
 * @since   : 2021.01.04
 * @pre     : 전역 변수 및 전역 함수 클래스
 * */
public class GlobalApplication extends Application {
    private String supp_code;
    private String user_code;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void showToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    public static void showSnackBar(View v, String msg){
        Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show();
    }

    public String getSuppCode()
    {
        return supp_code;
    }

    public void setSuppCode(String str)
    {
        this.supp_code = str;
    }

    public String getUserCode()
    {
        return user_code;
    }

    public void setUserCode(String str)
    {
        this.user_code = str;
    }
}
