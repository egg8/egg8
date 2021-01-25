package com.egg8.common.retrofit;

import com.egg8.common.dto.Result;
import com.egg8.model.resrvation.MenuDTO;
import com.egg8.model.resrvation.ResDTO;
import com.egg8.model.resrvation.TimeDTO;
import com.egg8.model.user.UserDTO;
import com.egg8.model.user.UserResDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author  : 김재일
 * @since   : 2021.01.04
 * @pre     : 레트로핏2 http 통신 인터페이스
 * */
public interface RetrofitService {
    @GET("time/getTime")
    Call<ResDTO> getBaseTime(@Query("supp_code") String rs,@Query("in_date") String day);

    @FormUrlEncoded
    @POST("surgery/getSurgery")
    Call<Result> getSurgery(
            @Field("supp_code") String supp_code,
            @Field("user_code") String user_code,
            @Field("res_in_date") String res_in_date,
            @Field("res_name") String res_name,
            @Field("res_in_str_time") String res_in_str_time,
            @Field("res_in_end_time") String res_in_end_time
    );

    @FormUrlEncoded
    @POST("user/loginCheck")
    Call<UserDTO> Login(
            @Field("user_id") String user_id,
            @Field("user_pwd") String user_pwd,
            @Field("user_status") int status
    );

    @FormUrlEncoded
    @POST("join/userJoin")
    Call<Result> Join(
            @Field("user_id") String user_id,
            @Field("user_pwd") String user_pwd,
            @Field("user_tel") String user_tel,
            @Field("user_name") String user_name,
            @Field("user_status") int user_status
    );

    @GET("user/idCheck")
    Call<UserDTO> idCheck(
            @Query("user_id") String user_id
    );

    @GET("menu/getMenu")
    Call<MenuDTO> getMenu(@Query("supp_code") String SUPP_CODE);

    @GET("UserRes/setRes")
    Call<Result>setRes(
            @Query("SUPP_CODE") String SUPP_CODE,
            @Query("USER_CODE") String USER_CODE,
            @Query("RES_IN_DATE") String RES_IN_DATE,
            @Query("RES_IN_NAME") String RES_IN_NAME,
            @Query("STR_TIME") String STR_TIME,
            @Query("END_TIME") String END_TIME
    );







}
