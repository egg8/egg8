package com.egg8.common.function;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.ui.user.LoginActivity;


public class UserFunction {
    public static boolean checkLoginStatus (Context context) {
        String rs = SharedPreferenceManager.getString(context,"user_code");
        boolean rt = false;
        if (!rs.equals("")) {
            rt = true;
        } else {
            Toast.makeText(context, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        return rt;
    }
}
