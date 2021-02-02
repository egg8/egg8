package com.egg8.ui.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.egg8.R;
import com.egg8.adapter.recyclerview.ShopMenuAdapter;
import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.resrvation.MenuDTO;
import com.egg8.model.supp.SuppDTO;
import com.egg8.model.supp.SuppListDTO;
import com.egg8.ui.calendar.CalendarActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopActivity extends AppCompatActivity {
    RetrofitBuilder retrofitBuilder;
    RetrofitService retrofitService;
    Context mCon;
    ImageView back_key;
    TextView Tv_Shop_Name,Tv_Shop_Time,Shop_Tel_Num,Tv_Shop_Address,Tv_Notice;
    Button Btn_Res_Go,Btn_Question;
    RecyclerView Shop_Menu_RecyclerView;    // 메뉴 리사이클러
    ShopMenuAdapter shopMenuAdapter;
    ArrayList<MenuDTO> MenuList;
    Intent intent;
    String supp_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        findId(this);
        getMenu(mCon);
        getShopList();

    }


    public void findId(ShopActivity v){
        intent = getIntent();
        supp_code = intent.getExtras().getString("supp_code");
        back_key=findViewById(R.id.back_key);
        Tv_Shop_Name=findViewById(R.id.Tv_Shop_Name);
        Shop_Menu_RecyclerView=findViewById(R.id.Shop_Menu_RecyclerView);
        Tv_Shop_Time=findViewById(R.id.Tv_Shop_Time);
        Shop_Tel_Num=findViewById(R.id.Shop_Tel_Num);
        Tv_Shop_Address=findViewById(R.id.Tv_Shop_Address);
        Btn_Res_Go=findViewById(R.id.Btn_Res_Go);
        Btn_Question=findViewById(R.id.Btn_Question);
        Tv_Notice=findViewById(R.id.Tv_Notice);
        btnListener();
    }


    private void btnListener(){
        Btn_Res_Go.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Btn_Res_Go:
                    Intent et = new Intent(v.getContext(),CalendarActivity.class);
                    et.putExtra("supp_code",supp_code);
                    startActivity(et);
                    break;
            }
        }
    };

    public void ShopMenu() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mCon, RecyclerView.VERTICAL, false);
        Shop_Menu_RecyclerView.setLayoutManager(layoutManager);
        shopMenuAdapter = new ShopMenuAdapter(MenuList);
        Shop_Menu_RecyclerView.setAdapter(shopMenuAdapter);
    }


    public void getMenu(Context context) {

        retrofitBuilder.getInstance("http://222.100.239.140:8888/");
        retrofitService = retrofitBuilder.getRetrofitService();
        Call<MenuDTO> call = retrofitService.getMenu(supp_code);

        call.enqueue(new Callback<MenuDTO>() {
            @Override
            public void onResponse(Call<MenuDTO> call, Response<MenuDTO> response) {
                // 통신 성공
                if (response.isSuccessful()) {
                    MenuDTO result = response.body();
                    MenuList = new ArrayList<>();
                    if (response.body() != null) {
                        for (int i = 0; i < result.getResult().size(); i++) {
                            MenuDTO dto = new MenuDTO();
                            dto.setSUG_IDX(result.getResult().get(i).getSUG_IDX());
                            dto.setSUG_NAME(result.getResult().get(i).getSUG_NAME());
                            dto.setSUG_PRICE(result.getResult().get(i).getSUG_PRICE());
                            MenuList.add(dto);
                        }
                        ShopMenu();
                    } else {
                    }
                } else {
                    Log.d("결과값 : ", "onResponse : 실패");
                }
            }
            @Override
            public void onFailure(Call<MenuDTO> call, Throwable t) {
                Log.d("결과 값 : ", "통신 불가" + t.getMessage());
            }
        });
    }

    public void getShopList() {

        retrofitBuilder.getInstance("http://222.100.239.140:8888/");
        retrofitService = retrofitBuilder.getRetrofitService();
        Call<SuppListDTO> call = retrofitService.getSuppData(supp_code);

        call.enqueue(new Callback<SuppListDTO>() {
            @Override
            public void onResponse(Call<SuppListDTO> call, Response<SuppListDTO> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        SuppListDTO result = response.body();
                        setShopDataBind(result);
                        Log.d("msg",result.getCLOSED_TIME());
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

    private void setShopDataBind(SuppListDTO dto) {
        Tv_Shop_Name.setText(dto.getSUPP_NAME());
        Tv_Shop_Time.setText("영업시간: "+dto.getOPEN_TIME()+"~"+dto.getCLOSED_TIME());
        Tv_Shop_Address.setText("주소: "+dto.getFULL_ADDRESS());
        Shop_Tel_Num.setText("휴대폰:  "+dto.getPON_NO()+"     매장 번호:  "+dto.getTEL_NO());

    }
}