package com.egg8.common.function;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.ui.MainActivity;
import com.egg8.ui.calendar.CalendarActivity;
import com.egg8.ui.user.LoginActivity;


public class InterceptorLogin {
    public static void checkLoginStatus (Context context) {
        String rs = SharedPreferenceManager.getString(context,"user_code");
        Intent intent;
        if (rs.equals("")) {
            intent = new Intent(context, MainActivity.class);
        } else {
            intent = new Intent(context, CalendarActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
