package com.egg8.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.egg8.R;
import com.egg8.common.function.UserFunction;
import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.ui.user.LoginActivity;

// test - 용재
// test - 성엽
// test - 진욱
public class MainActivity extends AppCompatActivity {

    Button join_out, log_out;
    Context context;
    UserFunction userFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        join_out = findViewById(R.id.join_out);
        log_out = findViewById(R.id.log_out);
        context = this;
        userFunction = new UserFunction();

        userFunction.checkLoginStatus(context);

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userFunction.checkLoginStatus(context) == true) {
                    Log.d("msg","로그아웃 버튼눌림");
                    SharedPreferenceManager.removeKey(context,"user_id");
                    SharedPreferenceManager.removeKey(context,"user_code");
                    SharedPreferenceManager.removeKey(context,"user_name");
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(context, "로그아웃이 성공적으로 되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}