package com.egg8.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.egg8.ui.MainActivity;
import com.egg8.ui.calendar.CalendarActivity;
import com.egg8.ui.user.JoinActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startActivity(new Intent(this, JoinActivity.class));
        finish();
    }
}