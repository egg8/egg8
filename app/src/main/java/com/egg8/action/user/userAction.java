package com.egg8.action.user;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.egg8.R;
import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.common.manager.ToastManager;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.user.UserDTO;
import com.egg8.ui.reservation.ReservationActivity;
import com.egg8.ui.user.User_Main;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userAction {
    public static RetrofitBuilder retrofitBuilder;
    public static RetrofitService service;

    public void LoginAction(Activity activity, String id, String pwd, int status,String url) {
        retrofitBuilder = RetrofitBuilder.getInstance(url);
        service = retrofitBuilder.getRetrofitService();
        Call<UserDTO> call = service.Login(id,pwd,status);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.body() != null){
                    UserDTO result = response.body();
                    if(result.getResult().equals("success") && result.getSupp_code().equals("")){
                        Log.d("btn3",result.getSupp_code());
                        // 일반 회원 로그인
                        SharedPreferenceManager.setString(activity.getApplicationContext(), "user_id", result.getUser_id());
                        SharedPreferenceManager.setString(activity.getApplicationContext(), "user_code", result.getUser_code());
                        SharedPreferenceManager.setString(activity.getApplicationContext(), "user_name", result.getUser_name());
                        Intent intent = new Intent(activity.getApplicationContext(), User_Main.class);
                        activity.startActivity(intent);
                        activity.finish();
                    } else if(result.getResult().equals("success") && !result.getSupp_code().equals("")) {
                        // 협력사 로그인
                        SharedPreferenceManager.setString(activity.getApplicationContext(), "user_id", result.getUser_id());
                        SharedPreferenceManager.setString(activity.getApplicationContext(), "user_code", result.getUser_code());
                        SharedPreferenceManager.setString(activity.getApplicationContext(), "user_name", result.getUser_name());
                        SharedPreferenceManager.setString(activity.getApplicationContext(), "supp_code", result.getSupp_code());
                        SharedPreferenceManager.setString(activity.getApplicationContext(), "supp_name", result.getSupp_name());
                        Intent intent = new Intent(activity.getApplicationContext(), ReservationActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                    else{
                        ToastManager.basicToastMsg(activity.getApplicationContext(), R.string.login_error_msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                ToastManager.basicToastMsg(activity.getApplicationContext(), R.string.OnFailer_msg);
            }
        });
    }
}
