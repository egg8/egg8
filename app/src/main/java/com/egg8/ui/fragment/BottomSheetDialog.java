package com.egg8.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.egg8.R;
import com.egg8.adapter.recyclerview.OnItemClickListener;
import com.egg8.adapter.recyclerview.SurgeryAdapter;
import com.egg8.common.dto.Result;
import com.egg8.common.function.MakeDialogMsg;
import com.egg8.common.function.MakePrice;
import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.common.manager.ToastManager;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.resrvation.MenuDTO;
import com.egg8.model.resrvation.ResDTO;
import com.egg8.model.user.UserResDTO;
import com.egg8.ui.MainActivity;
import com.egg8.ui.calendar.CalendarActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private RecyclerView RecyclerView_surgery;
    private SurgeryAdapter surgeryAdapter;
    private Button btn_app_ok;
    private RetrofitBuilder retrofitBuilder;
    private RetrofitService retrofitService;
    ArrayList<MenuDTO> arrayList;
    Context mCon;
    String chk;
    String price;







    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView_surgery = (RecyclerView) view.findViewById(R.id.RecyclerView_surgery);
        btn_app_ok = view.findViewById(R.id.btn_app_ok);
        getMenu();
        btn_app_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialg();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.recyclerview_surgery, container, false);
        mCon = view.getContext();
        return view;

    }

    public void SurgeryAppointment() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mCon, RecyclerView.VERTICAL, false);
        RecyclerView_surgery.setLayoutManager(layoutManager);
        surgeryAdapter = new SurgeryAdapter(arrayList);
        RecyclerView_surgery.setAdapter(surgeryAdapter);
        surgeryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos, String a) {
                RadioButton cb_surgery = v.findViewById(R.id.cb_surgery);
                chk = cb_surgery.getText().toString();
                //TextView sug_price = v.findViewById(R.id.sug_price);
                //price = sug_price.getText().toString();
                btn_app_ok.setEnabled(true);
            }
        });
    }

    public void getMenu() {
        retrofitBuilder.getInstance("http://222.100.239.140:8888/");
        retrofitService = retrofitBuilder.getRetrofitService();
        String supp_code=CalendarActivity.code;
        Call<MenuDTO> call = retrofitService.getMenu(supp_code);

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
                        SurgeryAppointment();
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

    public void dialg() {
        String supp_name=CalendarActivity.name;
        String msg = MakeDialogMsg.MakeMsg(supp_name, SharedPreferenceManager.getString(mCon, "tmp_v_date"),SharedPreferenceManager.getString(mCon,"tmp_time"), chk);
        AlertDialog.Builder dlg = new AlertDialog.Builder(mCon);

        dlg.setTitle("안내"); //제목
        dlg.setMessage(msg); // 메시지
        dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                setRes();
            }
        });
        dlg.show();
    }

    public void setRes() {
        String supp_code=SharedPreferenceManager.getString(mCon,"supp_code");
        Log.d("코드",SharedPreferenceManager.getString(mCon,"supp_code"));

        String user_code = SharedPreferenceManager.getString(mCon, "user_code");
        Log.d("코드",SharedPreferenceManager.getString(mCon,"user_code"));

        String res_in_date = SharedPreferenceManager.getString(mCon, "tmp_date");
        Log.d("코드",SharedPreferenceManager.getString(mCon,"tmp_date"));

        String res_in_name = chk;
        Log.d("코드",chk);

        String price = SharedPreferenceManager.getString(mCon,"sug_price");
        int res_price = MakePrice.Making(price);
        Log.d("코드",res_price+"");

        String res_in_str_time = SharedPreferenceManager.getString(mCon, "tmp_time");
        Log.d("코드",SharedPreferenceManager.getString(mCon,"tmp_time"));
        String res_in_end_time = SharedPreferenceManager.getString(mCon, "tmp_time");
        Log.d("코드",SharedPreferenceManager.getString(mCon,"tmp_time"));

        retrofitBuilder.getInstance("http://222.100.239.140:8888/");
        retrofitService = retrofitBuilder.getRetrofitService();
        Call<Result> call =retrofitService.setRes(supp_code,user_code,res_in_date,res_in_name,res_price,res_in_str_time,res_in_end_time);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                // 통신 성공
                if (response.isSuccessful()) {
                    Result result = response.body();
                    if (result.getResult().equals("success")) {
                        Log.d("확인","예약되었습니다.");
                    }
                    else {
                        Toast.makeText(mCon,"예약실패",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("결과값 : ", "onResponse : 실패");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("결과 값 : ", "통신 불가" + t.getMessage());
            }
        });
    }

}



