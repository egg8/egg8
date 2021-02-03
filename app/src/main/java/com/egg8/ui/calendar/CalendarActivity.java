package com.egg8.ui.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egg8.R;
import com.egg8.action.surgery.getTimeAction;
import com.egg8.adapter.recyclerview.OnItemClickListener;
import com.egg8.adapter.recyclerview.TimeAdapter;
import com.egg8.common.GlobalApplication;
import com.egg8.common.function.GetDayFormat;
import com.egg8.common.function.MakeTimeButton;
import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.resrvation.ResDTO;
import com.egg8.model.string.ButtonDTO;
import com.egg8.ui.fragment.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    TextView Tv_Shop_Name;
    RecyclerView RecyclerView_time;
    LinearLayout ll_applist;
    TimeAdapter timeAdapter;
    RetrofitBuilder retrofitBuilder;
    RetrofitService retrofitService;
    ArrayList<ButtonDTO> list;
    Context mCon;
    Intent intent;
    public static String code;
    public static String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        findId(this);
        mCon = this;

    }
    private String getToday(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(now);
    }

    private void findId(Activity v){
        intent = getIntent();
        code = intent.getExtras().getString("supp_code");
        name = intent.getExtras().getString("supp_name");
        Log.d("code",code);
        RecyclerView_time = v.findViewById(R.id.RecyclerView_time);
        ll_applist = v.findViewById(R.id.ll_applist);
        calendarView = v.findViewById(R.id.calendar_view);
        Tv_Shop_Name=findViewById(R.id.Tv_Shop_Name);
        btnListener();
        setLayoutManager();
        getBaseTime(getToday());


    }

    private void btnListener(){
        calendarView.setOnDateChangeListener(dateChangeListener);
    }

    private void setLayoutManager(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mCon, 3);
        RecyclerView_time.setLayoutManager(gridLayoutManager);
    }

    CalendarView.OnDateChangeListener dateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            String day = GetDayFormat.MakeToday(year,month+1,dayOfMonth);
            SharedPreferenceManager.setString(view.getContext(),"tmp_v_date",year+"년 "+(month+1)+"월 "+dayOfMonth+"일");
            SharedPreferenceManager.setString(view.getContext(),"tmp_date",day);
            getBaseTime(day);

        }
    };

    public void createTimeButton(String time) {
        list = new ArrayList<>();
        list = MakeTimeButton.MakeTimeBtn(time);
        timeAdapter = new TimeAdapter(mCon,list);
        RecyclerView_time.setAdapter(timeAdapter);
        timeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos,String a) {
                BottomSheetDialog fragment = new BottomSheetDialog();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragment.show(fragmentManager,"tag");
            }
        });
    }

    public void getBaseTime(String Days) {
        retrofitBuilder = new RetrofitBuilder("http://222.100.239.140:8888/");
        retrofitService = retrofitBuilder.getRetrofitService();
        Call<ResDTO> call = retrofitService.getBaseTime(code,Days);
        call.enqueue(new Callback<ResDTO>() {
            @Override
            public void onResponse(Call<ResDTO> call, Response<ResDTO> response) {
                if(response.isSuccessful()) {
                    ResDTO result = response.body();
                    if(response.body() != null) {
                        createTimeButton(result.getResult());
                    } else {
                        GlobalApplication.showToastMsg(mCon,"에러가 발생하였습니다. 관리자에게 문의하세요.");
                    }
                } else {
                    GlobalApplication.showToastMsg(mCon,"에러가 발생하였습니다. 관리자에게 문의하세요.");
                    Log.d("error!!","onResponse failed");
                }
            }

            @Override
            public void onFailure(Call<ResDTO> call, Throwable t) {
                GlobalApplication.showToastMsg(mCon,"에러가 발생하였습니다. 관리자에게 문의하세요.");
                Log.d("error!!","onFailure" + t.getMessage());
            }
        });
    }

}