package com.egg8.common.manager;

import android.app.Activity;

import com.egg8.R;


public class BackPressedManager {
    private long backKeyPressedTime = 0;    // '뒤로' 버튼을 클릭했을 때의 시간
    private long TIME_INTERVAL = 2000;      // 첫번째 버튼 클릭과 두번째 버튼 클릭 사이의 종료를 위한 시간차를 정의
    private Activity activity;              // 종료할 액티비티의 Activity 객체

    public BackPressedManager(Activity _activity) {
        this.activity = _activity;
    }
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + TIME_INTERVAL) {
            backKeyPressedTime = System.currentTimeMillis();
            showMessage();
        }else{
            activity.finish();
        }
    }

    public void showMessage() {
        ToastManager.basicToastMsg(activity, R.string.back_pressed_msg);
    }
}
