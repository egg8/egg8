package com.egg8.common.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author  : 김재일
 * @since   : 2021.01.04
 * @pre     : 레트로핏2 public 빌더
 * */
public class RetrofitBuilder {

    private static RetrofitBuilder instance = null;
    private static RetrofitService service;

    public RetrofitBuilder(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RetrofitService.class);
    }

    public static RetrofitBuilder getInstance(String url) {
        if (instance == null) {
            instance = new RetrofitBuilder(url);
        }
        return instance;
    }

    public static RetrofitService getRetrofitService() {
        return service;
    }
}
