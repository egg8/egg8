package com.egg8.ui.user.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.egg8.R;
import com.egg8.adapter.recyclerview.ShopListAdapter;
import com.egg8.adapter.recyclerview.ShopMenuAdapter;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.resrvation.MenuDTO;
import com.egg8.model.supp.SuppListDTO;
import com.egg8.ui.calendar.CalendarActivity;
import com.egg8.ui.shop.ShopActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopListActivity extends AppCompatActivity {
    RetrofitBuilder retrofitBuilder;
    RetrofitService retrofitService;
    ShopListAdapter shopListAdapter;
    Context mCon;
    ImageView Iv_Shop_Search;
    RecyclerView Shop_List_RecyclerView;
    ArrayList<SuppListDTO> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        findId(this);
        getShopList(mCon);

    }

    public void findId(ShopListActivity v){
        Iv_Shop_Search=findViewById(R.id.Iv_Shop_Search);
        Shop_List_RecyclerView=findViewById(R.id.Shop_List_RecyclerView);
    }

    //리스너 넣는곳
    private void btnListener(){

    }
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Iv_Shop_Search:

                    break;
            }
        }
    };
    public void ShopList() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mCon, RecyclerView.VERTICAL, false);
        Shop_List_RecyclerView.setLayoutManager(layoutManager);
        shopListAdapter = new ShopListAdapter(arrayList);
        Shop_List_RecyclerView.setAdapter(shopListAdapter);
    }
    public void getShopList(Context context) {

        retrofitBuilder.getInstance("http://222.100.239.140:8888/");
        retrofitService = retrofitBuilder.getRetrofitService();
        Call<SuppListDTO> call = retrofitService.getSupp();

        call.enqueue(new Callback<SuppListDTO>() {
            @Override
            public void onResponse(Call<SuppListDTO> call, Response<SuppListDTO> response) {
                // 통신 성공
                if (response.isSuccessful()) {
                    SuppListDTO result = response.body();
                    arrayList = new ArrayList<>();
                    if (response.body() != null) {
                        for (int i = 0; i < result.getResult().size(); i++) {
                            SuppListDTO dto = new SuppListDTO();
                            dto.setSUPP_NAME(result.getResult().get(i).getSUPP_NAME());
                            dto.setADDR_CITY(result.getResult().get(i).getADDR_CITY());
                            dto.setCATEGORY(result.getResult().get(i).getCATEGORY());
                            dto.setOPEN_TIME(result.getResult().get(i).getOPEN_TIME());
                            dto.setCLOSED_TIME(result.getResult().get(i).getCLOSED_TIME());
                            arrayList.add(dto);
                            Log.d("msg","성공");

                        }
                        ShopList();
                    } else {
                        Log.d("msg","꽝");
                    }
                } else {
                    Log.d("결과값 : ", "onResponse : 실패");
                }
            }
            @Override
            public void onFailure(Call<SuppListDTO> call, Throwable t) {
                Log.d("결과 값 : ", "통신 불가" + t.getMessage());
            }
        });
    }
}