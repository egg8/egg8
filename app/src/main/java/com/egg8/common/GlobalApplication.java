package com.egg8.common;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * @author  : 김재일
 * @since   : 2021.01.04
 * @pre     : 전역 변수 및 전역 함수 클래스
 * */
public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void showToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
