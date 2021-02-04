package com.egg8.ui.user;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.egg8.R;
import com.egg8.adapter.recyclerview.Res_RecyclerViewAdapter;
import com.egg8.common.dto.Res.ResDTO;
import com.egg8.common.dto.Res.ResResult;
import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Main extends FragmentActivity {

    private ViewPager2 mPager, mPager2;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 3;
    private int num_page2 = 5;
    private CircleIndicator3 mIndicator, mIndicator2;
    private RetrofitBuilder builder;
    private RetrofitService service;
    float pageMargin,pageOffset;
    TextView User_id, today_res;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        findId();
        viewPagerSettings();
        viewPagerSettings2();
        getResTime();
        getResTime2();
        bindIndicator();
        bindIndicator2();
        setPageOffset();
    }

    private void findId() {
        mPager = findViewById(R.id.viewpager);
        mIndicator = findViewById(R.id.indicator);

        mPager2 = findViewById(R.id.viewpager2);
        mIndicator2 = findViewById(R.id.indicator2);

        User_id = findViewById(R.id.User_id);
        today_res = findViewById(R.id.today_res);
        context = this;
    }

    private void bindRecyclerAdapter(ArrayList<ResDTO> dtos){
        Res_RecyclerViewAdapter adapter = new Res_RecyclerViewAdapter(dtos);
        mPager.setAdapter(adapter);
    }
    private void bindRecyclerAdapter2(ArrayList<ResDTO> dtos2){
        Res_RecyclerViewAdapter adapter2 = new Res_RecyclerViewAdapter(dtos2);
        mPager2.setAdapter(adapter2);
    }

    private void bindIndicator(){
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page, 0);
    }

    private void bindIndicator2(){
        mIndicator2.setViewPager(mPager2);
        mIndicator2.createIndicators(num_page2, 0);
    }

    private void viewPagerSettings(){
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(1000);
        mPager.setOffscreenPageLimit(3);
        mPager.registerOnPageChangeCallback(changeCallback);
        mPager.setPageTransformer(pageTransformer);
    }

    private void viewPagerSettings2(){
        mPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager2.setCurrentItem(1000);
        mPager2.setOffscreenPageLimit(5);
        mPager2.registerOnPageChangeCallback(changeCallback2);
        mPager2.setPageTransformer(pageTransformer2);
    }

    private void setPageOffset(){
        pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
    }

    ViewPager2.OnPageChangeCallback changeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            if (positionOffsetPixels == 0) {
                mPager.setCurrentItem(position);
            }
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            mIndicator.animatePageSelected(position % num_page);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

    ViewPager2.OnPageChangeCallback changeCallback2 = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position2, float positionOffset2, int positionOffsetPixels2) {
            super.onPageScrolled(position2, positionOffset2, positionOffsetPixels2);

            if (positionOffsetPixels2 == 0) {
                mPager2.setCurrentItem(position2);
            }
        }

        @Override
        public void onPageSelected(int position2) {
            super.onPageSelected(position2);
            mIndicator2.animatePageSelected(position2 % num_page2);
        }

        @Override
        public void onPageScrollStateChanged(int state2) {
            super.onPageScrollStateChanged(state2);
        }
    };
    ViewPager2.PageTransformer pageTransformer = new ViewPager2.PageTransformer() {
        @Override
        public void transformPage(@NonNull View page, float position) {
            float myOffset = position * -(2 * pageOffset + pageMargin);
            if (mPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(mPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.setTranslationX(-myOffset);
                } else {
                    page.setTranslationX(myOffset);
                }
            } else {
                page.setTranslationY(myOffset);
            }
        }
    };
    ViewPager2.PageTransformer pageTransformer2 = new ViewPager2.PageTransformer() {
        @Override
        public void transformPage(@NonNull View page2, float position2) {
            float myOffset = position2 * -(2 * pageOffset + pageMargin);
            if (mPager2.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(mPager2) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page2.setTranslationX(-myOffset);
                } else {
                    page2.setTranslationX(myOffset);
                }
            } else {
                page2.setTranslationY(myOffset);
            }
        }
    };

    private void setRetrofitBuilder(){
        builder = RetrofitBuilder.getInstance("http://222.100.239.140:11005/");
        service = RetrofitBuilder.getRetrofitService();
    }

    private void getResTime(){
        setRetrofitBuilder();
        String user_code = SharedPreferenceManager.getString(context,"user_code");

        Call<ResResult> call = service.getResTime(user_code,3);
        call.enqueue(new Callback<ResResult>() {
            @Override
            public void onResponse(Call<ResResult> call, Response<ResResult> response) {
                if(response.isSuccessful()) {
                    ArrayList<ResDTO> dtos = new ArrayList<>();
                    for(int i=0; i<response.body().getResult().size(); i++) {
                        ResDTO dto = new ResDTO();
                        dto = response.body().getResult().get(i);
                        dtos.add(dto);
                    }
                    bindRecyclerAdapter(dtos);
                }
            }

            @Override
            public void onFailure(Call<ResResult> call, Throwable t) {
                Log.d("msg",t.getMessage());
                Toast.makeText(getApplicationContext(),"통신 실패", Toast.LENGTH_LONG).show();
            }
        });
    }

        private void getResTime2(){
            setRetrofitBuilder();
            String user_code = SharedPreferenceManager.getString(context,"user_code");
            Call<ResResult> call2 = service.getResTime2(user_code,5);
            call2.enqueue(new Callback<ResResult>() {
                @Override
                public void onResponse(Call<ResResult> call2, Response<ResResult> response2) {
                    if(response2.isSuccessful()) {
                        ArrayList<ResDTO> dtos2 = new ArrayList<>();
                        for(int i=0; i<response2.body().getResult().size(); i++) {
                            ResDTO dto2 = new ResDTO();
                            dto2 = response2.body().getResult().get(i);
                            dtos2.add(dto2);
                        }
                        bindRecyclerAdapter2(dtos2);
                    }
                }
    
                @Override
                public void onFailure(Call<ResResult> call, Throwable t) {
                    Log.d("msg",t.getMessage());
                    Toast.makeText(getApplicationContext(),"통신 실패", Toast.LENGTH_LONG).show();
                }
            });
        }



}