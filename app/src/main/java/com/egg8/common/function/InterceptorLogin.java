package com.egg8.common.function;

import android.content.Context;
import android.content.Intent;

import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.ui.MainActivity;
import com.egg8.ui.calendar.CalendarActivity;
import com.egg8.ui.reservation.ReservationActivity;
import com.egg8.ui.supp.ChangeCategory;
import com.egg8.ui.user.login.LoginActivity;


public class InterceptorLogin {
    public static void checkLoginStatus (Context context) {
        String user_code = SharedPreferenceManager.getString(context,"user_code");
        String supp_code = SharedPreferenceManager.getString(context,"supp_code");
        String supp_name = SharedPreferenceManager.getString(context,"supp_name");
        Intent intent;

        if (user_code.equals("") && supp_code.equals("")) {
            intent = new Intent(context, MainActivity.class);
        } else if (!user_code.equals("") && supp_code.equals("")) {
            intent = new Intent(context, CalendarActivity.class);
        } else {
            intent = new Intent(context, LoginActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
