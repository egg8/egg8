package com.egg8.ui.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import com.egg8.R;
import com.egg8.adapter.recyclerview.OnItemClickListener;
import com.egg8.adapter.recyclerview.TimeAdapter;
import com.egg8.common.function.GetDayFormat;
import com.egg8.common.function.MakeTimeButton;
import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.resrvation.ResDTO;
import com.egg8.model.string.ButtonDTO;
import com.egg8.ui.fragment.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;              //달력
    RecyclerView RecyclerView_time;         //예약시간
    LinearLayout ll_applist;                //날짜 선택후
    TimeAdapter timeAdapter;                //시간정보 리사이클러뷰 어댑터
    RetrofitBuilder retrofitBuilder;
    RetrofitService retrofitService;
    ArrayList<ButtonDTO> list;
    Context mCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        RecyclerView_time = findViewById(R.id.RecyclerView_time);
        ll_applist=findViewById(R.id.ll_applist);
        calendarView = findViewById(R.id.calendar_view);
        mCon = this;
        //달력 선택 리스너
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String chkDay = GetDayFormat.MakeToday(year,month+1,dayOfMonth);
                SharedPreferenceManager.setString(view.getContext(),"tmp_v_date",year+"년 "+month+1+"월 "+dayOfMonth+"일");
                SharedPreferenceManager.setString(view.getContext(),"tmp_date",chkDay);
                getBaseTime(mCon,chkDay);
            }
        });
    }
    //시간예약함수
    public void TimeAppointment(Context context , String time) {
        list = new ArrayList<>();
        list = MakeTimeButton.MakeTimeBtn(time);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        RecyclerView_time.setLayoutManager(gridLayoutManager);
        timeAdapter = new TimeAdapter(list);
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


    public void getBaseTime(Context context, String Days){
        retrofitBuilder = new RetrofitBuilder("http://222.100.239.140:8888/");
        retrofitService = retrofitBuilder.getRetrofitService();
        Call<ResDTO> call = retrofitService.getBaseTime("S0001",Days);

        call.enqueue(new Callback<ResDTO>() {
            @Override
            public void onResponse(Call<ResDTO> call, Response<ResDTO> response) {
                // 통신 성공
                if(response.isSuccessful()) {
                    ResDTO result = response.body();
                    if(response.body() != null) {
                        TimeAppointment(context,result.getResult());
                    } else {

                    }
                } else {
                    Log.d("결과값 : ","onResponse : 실패");
                }
            }

            @Override
            public void onFailure(Call<ResDTO> call, Throwable t) {
                Log.d("결과 값 : ","통신 불가" + t.getMessage());
            }
        });
    }
}