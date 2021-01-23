package com.egg8.common.manager;

import android.content.Context;
import android.widget.Toast;


public class ToastManager {

    public static void basicToastMsg(Context context, int msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
