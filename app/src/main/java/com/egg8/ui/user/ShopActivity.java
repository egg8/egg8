package com.egg8.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.egg8.R;
import com.egg8.adapter.recyclerview.OnItemClickListener;
import com.egg8.adapter.recyclerview.ShopMenuAdapter;
import com.egg8.adapter.recyclerview.SurgeryAdapter;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.resrvation.MenuDTO;
import com.egg8.ui.calendar.CalendarActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopActivity extends AppCompatActivity {
    RetrofitBuilder retrofitBuilder;
    RetrofitService retrofitService;
    Context mCon;
    ImageView back_key,Search;
    TextView Tv_Shop_Name,Tv_Shop_Time,Shop_Tel_Num,Tv_Shop_Address,Tv_Notice;
    Button Btn_Res_Go,Btn_Question;
    RecyclerView Shop_Menu_RecyclerView;    // 메뉴 리사이클러
    ShopMenuAdapter shopMenuAdapter;
    ArrayList<MenuDTO> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        findId(this);
        getMenu(mCon);
    }


    public void findId(ShopActivity v){
        back_key=findViewById(R.id.back_key);
        Tv_Shop_Name=findViewById(R.id.Tv_Shop_Name);
        Search=findViewById(R.id.Search);
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
                    Intent intent=new Intent(v.getContext(),CalendarActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    public void ShopMenu() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mCon, RecyclerView.VERTICAL, false);
        Shop_Menu_RecyclerView.setLayoutManager(layoutManager);
        shopMenuAdapter = new ShopMenuAdapter(arrayList);
        Shop_Menu_RecyclerView.setAdapter(shopMenuAdapter);
    }
    public void getMenu(Context context) {

        retrofitBuilder.getInstance("http://222.100.239.140:8888/");
        retrofitService = retrofitBuilder.getRetrofitService();
        Call<MenuDTO> call = retrofitService.getMenu("S0001");

        call.enqueue(new Callback<MenuDTO>() {
            @Override
            public void onResponse(Call<MenuDTO> call, Response<MenuDTO> response) {
                // 통신 성공
                if (response.isSuccessful()) {
                    MenuDTO result = response.body();
                    arrayList = new ArrayList<>();
                    if (response.body() != null) {
                        for (int i = 0; i < result.getResult().size(); i++) {
                            MenuDTO dto = new MenuDTO();
                            dto.setSUG_IDX(result.getResult().get(i).getSUG_IDX());
                            dto.setSUG_NAME(result.getResult().get(i).getSUG_NAME());
                            dto.setSUG_PRICE(result.getResult().get(i).getSUG_PRICE());
                            arrayList.add(dto);
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


}