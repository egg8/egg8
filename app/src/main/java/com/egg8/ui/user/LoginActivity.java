package com.egg8.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.egg8.R;
import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.common.manager.ToastManager;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.user.UserDTO;
import com.egg8.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText td_email;
    EditText td_pw;

    Button login_btn;
    Button google_join;
    Button kakao_join;

    ImageView back_key;

    TextView join;
    TextView find_pw;



    String id, pwd;
    RetrofitBuilder retrofitBuilder;
    RetrofitService service;
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        td_email = (EditText)findViewById(R.id.td_email);
        td_pw = (EditText)findViewById(R.id.td_pw);
        login_btn = (Button)findViewById(R.id.login_btn);
        google_join = (Button)findViewById(R.id.google_join);
        kakao_join = (Button)findViewById(R.id.kakao_join);
        back_key = (ImageView)findViewById(R.id.back_key);
        join = (TextView)findViewById(R.id.join);
        find_pw = (TextView)findViewById(R.id.find_pw);



        back_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
                finish();
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(context);
            }
        });


    }

    /*@Override
    public void onBackPressed() {
        backPressedManager.onBackPressed();
    }*/


    public void Login(Context context) {
        id = td_email.getText().toString();
        pwd = td_pw.getText().toString();
        retrofitBuilder = RetrofitBuilder.getInstance("http://222.100.239.140:11005/");
        service = retrofitBuilder.getRetrofitService();
        Call<UserDTO> call = service.Login(id,pwd);
        call.enqueue(new Callback<UserDTO>() {

            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.body() != null){
                    UserDTO result = response.body();
                    if(result.getResult().equals("success")){
                        ToastManager.basicToastMsg(context, R.string.login_success_msg);
                        SharedPreferenceManager.setString(context, "user_id", result.getUser_id());
                        SharedPreferenceManager.setString(context, "user_code", result.getUser_code());
                        SharedPreferenceManager.setString(context, "user_name", result.getUser_name());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        ToastManager.basicToastMsg(context, R.string.login_error_msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                ToastManager.basicToastMsg(context, R.string.OnFailer_msg);
            }
        });


    }
}