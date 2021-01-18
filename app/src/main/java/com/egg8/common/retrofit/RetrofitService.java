package com.egg8.common.retrofit;

import com.egg8.model.resrvation.TimeDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author  : 김재일
 * @since   : 2021.01.04
 * @pre     : 레트로핏2 http 통신 인터페이스
 * */
public interface RetrofitService {
    // @GET("{controller}/{function}")
    // Call<UserData> getUserData(@Path("controller") String uri1, @Path("function") String uri2);
    @GET("time/getBaseTime")
    Call<TimeDTO> getBaseTime(@Query("supp_code") String rs);
}
