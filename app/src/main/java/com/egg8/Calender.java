package com.egg8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.resrvation.TimeDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Calender extends AppCompatActivity {

    private CalendarView calendarView;              //달력
    private RecyclerView RecyclerView_time;         //예약시간
    private LinearLayout ll_apptext;                //날짜 선택전
    private LinearLayout ll_applist;                //날짜 선택후
    private LinearLayout ll_timeAppointment;        //시간정보
    private TimeAdapter timeAdapter;                //시간정보 리사이클러뷰 어댑터
    private RetrofitBuilder retrofitBuilder;
    private RetrofitService retrofitService;
    Context mCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        ll_timeAppointment = findViewById(R.id.ll_timeAppointment);
        RecyclerView_time = findViewById(R.id.RecyclerView_time);
        ll_applist=findViewById(R.id.ll_applist);
        ll_apptext=findViewById(R.id.ll_apptext);
        calendarView = findViewById(R.id.calendar_view);
        mCon = this;

        //달력 선택 리스너
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String chkDay = year + "/" + (month + 1) + "/" + dayOfMonth;
                // tv_Selectday.setText(chkDay);
                getBaseTime(mCon);
                ll_apptext.setVisibility(View.GONE);
                ll_applist.setVisibility(View.VISIBLE);
            }
        });
    }

    //시간예약함수
    public void TimeAppointment(TimeDTO timeDTO) {
        ArrayList<String> list = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        RecyclerView_time.setLayoutManager(gridLayoutManager);
        timeAdapter = new TimeAdapter(list, timeDTO);
    }

    public void getBaseTime(Context context){
        retrofitBuilder = new RetrofitBuilder("http://222.100.239.140:11001/");
        retrofitService = retrofitBuilder.getRetrofitService();
        Call<TimeDTO> call = retrofitService.getBaseTime("S0001");

        call.enqueue(new Callback<TimeDTO>() {
            @Override
            public void onResponse(Call<TimeDTO> call, Response<TimeDTO> response) {
                // 통신 성공
                if(response.isSuccessful()) {
                    TimeDTO result = response.body();
                    if(response.body() != null) {
                        TimeAppointment(result);
                    } else {

                    }
                } else {
                    Log.d("결과값 : ","onResponse : 실패");
                }
            }

            @Override
            public void onFailure(Call<TimeDTO> call, Throwable t) {
                Log.d("결과 값 : ","통신 불가" + t.getMessage());
            }
        });
    }
}