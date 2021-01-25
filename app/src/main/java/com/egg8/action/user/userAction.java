package com.egg8.action.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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

public class userAction extends Activity {
    public static RetrofitBuilder retrofitBuilder;
    public static RetrofitService service;

    public void LoginAction(Context mCon, String id, String pwd, String url) {
        retrofitBuilder = RetrofitBuilder.getInstance(url);
        service = retrofitBuilder.getRetrofitService();
        Call<UserDTO> call = service.Login(id,pwd);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.body() != null){
                    UserDTO result = response.body();
                    if(result.getResult().equals("success")){
                        ToastManager.basicToastMsg(mCon, R.string.login_success_msg);
                        SharedPreferenceManager.setString(mCon, "user_id", result.getUser_id());
                        SharedPreferenceManager.setString(mCon, "user_code", result.getUser_code());
                        SharedPreferenceManager.setString(mCon, "user_name", result.getUser_name());
                        Intent intent = new Intent(mCon, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        ToastManager.basicToastMsg(mCon, R.string.login_error_msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                ToastManager.basicToastMsg(mCon, R.string.OnFailer_msg);
            }
        });
    }
}
