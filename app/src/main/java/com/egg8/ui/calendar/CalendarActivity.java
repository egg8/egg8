package com.egg8.ui.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;

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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    RecyclerView RecyclerView_time;
    LinearLayout ll_applist;
    TimeAdapter timeAdapter;
    RetrofitBuilder retrofitBuilder;
    RetrofitService retrofitService;
    ArrayList<ButtonDTO> list;
    Context mCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        findId(this);
        mCon = this;
    }

    private void findId(Activity v){
        RecyclerView_time = v.findViewById(R.id.RecyclerView_time);
        ll_applist = v.findViewById(R.id.ll_applist);
        calendarView = v.findViewById(R.id.calendar_view);
        btnListener();
        bindAdapter();
    }

    private void btnListener(){
        calendarView.setOnDateChangeListener(dateChangeListener);
    }

    private void bindAdapter(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mCon, 3);
        RecyclerView_time.setLayoutManager(gridLayoutManager);
        timeAdapter = new TimeAdapter(mCon,list);
        RecyclerView_time.setAdapter(timeAdapter);
    }

    CalendarView.OnDateChangeListener dateChangeListener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            String chkDay = GetDayFormat.MakeToday(year,month+1,dayOfMonth);
            SharedPreferenceManager.setString(view.getContext(),"tmp_v_date",year+"년 "+month+1+"월 "+dayOfMonth+"일");
            SharedPreferenceManager.setString(view.getContext(),"tmp_date",chkDay);
            getBaseTime(mCon,chkDay);
        }
    };

    public void createTimeButton(String time) {
        list = new ArrayList<>();
        list = MakeTimeButton.MakeTimeBtn(time);
        timeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos,String a) {
                BottomSheetDialog fragment = new BottomSheetDialog();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragment.show(fragmentManager,"tag");
            }
        });
    }

    public void getBaseTime(Context context, String Days) {
        retrofitBuilder = new RetrofitBuilder("http://222.100.239.140:8888/");
        retrofitService = retrofitBuilder.getRetrofitService();
        Call<ResDTO> call = retrofitService.getBaseTime("S0001",Days);

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