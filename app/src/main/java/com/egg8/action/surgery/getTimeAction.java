package com.egg8.action.surgery;

import android.content.Context;
import android.util.Log;

import com.egg8.common.GlobalApplication;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.resrvation.ResDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getTimeAction {
    public static RetrofitBuilder retrofitBuilder;
    public static RetrofitService service;

    public String getTime(Context context, String supp_code, String Days){
        retrofitBuilder = new RetrofitBuilder("http://222.100.239.140:8888/");
        service = retrofitBuilder.getRetrofitService();
        Call<ResDTO> call = service.getBaseTime(supp_code,Days);

        call.enqueue(new Callback<ResDTO>() {
            @Override
            public void onResponse(Call<ResDTO> call, Response<ResDTO> response) {
                if(response.isSuccessful()) {
                    ResDTO result = response.body();
                    if(response.body() != null) {
                        String rs = returnString(result.getResult());
                    } else {
                        GlobalApplication.showToastMsg(context,"통신 장애, 관리자에게 문의하세요.");
                    }
                } else {
                    Log.d("error!!","onResponse failed");
                }
            }

            @Override
            public void onFailure(Call<ResDTO> call, Throwable t) {
                Log.d("error!!","onFailure" + t.getMessage());
            }
        });
        return supp_code;
    }
    public String returnString(String result){
        return result;
    }
}
