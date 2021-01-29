package com.egg8.ui.supp;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.egg8.R;
import com.egg8.adapter.recyclerview.SurgeryRecyclerAdapter;
import com.egg8.common.dto.surgery.SurgeryDTO;
import com.egg8.common.dto.surgery.SurgeryResult;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollActivity2 extends AppCompatActivity {
    TextView btn_open_time, btn_close_time;
    ImageButton btn_add;
    Context mCon;
    Button btn_put;
    EditText et_sug_name,et_sug_price;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    RecyclerView mRc;
    SurgeryRecyclerAdapter adapter;
    ArrayList<SurgeryDTO> dtos;
    String supp_code, name, price;
    RetrofitBuilder retrofitBuilder;
    RetrofitService service;
    Context context;
    Activity mAc;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll2);

        btn_open_time = findViewById(R.id.btn_open_time);
        btn_close_time = findViewById(R.id.btn_close_time);
        btn_add = findViewById(R.id.btn_add);
        TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, listener, 15, 24, true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); //배경화면 투명색

        mCon = this;
        context = this;
        mAc = this;



        btn_open_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                dialog.show();
            }
        });
        btn_close_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                dialog.show();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findId(mAc);

            }
        });
    }//onCreate
    //설정 버튼이 눌렸을 때
    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener()
    {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (flag == 0) {
                btn_open_time.setText(hourOfDay + ":" + minute);
            }

            if (flag == 1) {
                btn_close_time.setText(hourOfDay + ":" + minute);
            }
        }
    };

    /*시술목록데이터 추가부분*/
    private void findId(Activity v){
        btn_add.setOnClickListener(clickListener);
        mRc = v.findViewById(R.id.rc_dataList);
        getMenuList();
    }
    public void BindRecyclerItem(Activity activity, ArrayList<SurgeryDTO> list){
        LinearLayoutManager llm = new LinearLayoutManager(mCon);
        mRc.setLayoutManager(llm);
        adapter = new SurgeryRecyclerAdapter(activity, mCon, list);
        mRc.setAdapter(adapter);
        DividerItemDecoration(llm,mRc);
    } // 리사이클러 bind [e]

    public void DividerItemDecoration(LinearLayoutManager llm, RecyclerView mRc){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRc.getContext(),
                llm.getOrientation());
        mRc.addItemDecoration(dividerItemDecoration);
    } // divider [e]

    public void DialogBuilder(){
        builder = new AlertDialog.Builder(mCon);
        View view = LayoutInflater.from(mCon).inflate(R.layout.dialog_put_data, null, false);
        builder.setView(view);
        btn_put = view.findViewById(R.id.btn_put);
        et_sug_name = view.findViewById(R.id.et_sug_name);
        et_sug_price = view.findViewById(R.id.et_sug_price);
        dialog = builder.create();
        btn_put.setOnClickListener(clickListener);
        dialog.show();
    }

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_add:
                    DialogBuilder();
                    break;

                case R.id.btn_put:

                    supp_code = "S0001";
                    name = et_sug_name.getText().toString();
                    price = et_sug_price.getText().toString();


                    retrofitBuilder= RetrofitBuilder.getInstance("http://222.100.239.140:11005/");
                    service = retrofitBuilder.getRetrofitService();
                    Call<SurgeryDTO> call = service.Surgery(supp_code, name, price);
                    call.enqueue(new Callback<SurgeryDTO>() {
                        @Override
                        public void onResponse(Call<SurgeryDTO> call, Response<SurgeryDTO> response) {
                            if(response.isSuccessful()){
                                SurgeryDTO dto = response.body();
                                if(dto.getResult().equals("success")){
                                    if(dto.getResult().equals("success"))
                                        retrofitBuilder= RetrofitBuilder.getInstance("http://222.100.239.140:11005/");
                                    service = retrofitBuilder.getRetrofitService();
                                    Call<SurgeryResult> call2 = service.getSurgeryList("S0001");
                                    call2.enqueue(new Callback<SurgeryResult>() {
                                        @Override
                                        public void onResponse(Call<SurgeryResult> call, Response<SurgeryResult> response) {
                                            if(response.isSuccessful()){
                                                dtos = new ArrayList<>();
                                                SurgeryResult result = response.body();
                                                for(int i=0; i < result.getDto().size(); i++){
                                                    SurgeryDTO dto = new SurgeryDTO();
                                                    dto.setSug_idx(result.getDto().get(i).getSug_idx());
                                                    dto.setSug_name(result.getDto().get(i).getSug_name());
                                                    dto.setSug_price(result.getDto().get(i).getSug_price());
                                                    dtos.add(dto);
                                                }
                                                BindRecyclerItem(mAc,dtos);
                                                adapter.notifyDataSetChanged();
                                                dialog.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SurgeryResult> call, Throwable t) {
                                            Toast.makeText(mCon, "통신에러1", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SurgeryDTO> call, Throwable t) {
                            Toast.makeText(context, "통신에러2", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                    break;
            }
        }
    }; // onclickListener [e]
    private void getMenuList(){
        retrofitBuilder= RetrofitBuilder.getInstance("http://222.100.239.140:11005/");
        service = retrofitBuilder.getRetrofitService();
        Call<SurgeryResult> call2 = service.getSurgeryList("S0001");
        call2.enqueue(new Callback<SurgeryResult>() {
            @Override
            public void onResponse(Call<SurgeryResult> call, Response<SurgeryResult> response) {
                if(response.isSuccessful()){
                    dtos = new ArrayList<>();
                    SurgeryResult result = response.body();
                    for(int i=0; i < result.getDto().size(); i++){
                        SurgeryDTO dto = new SurgeryDTO();
                        dto.setSug_idx(result.getDto().get(i).getSug_idx());
                        dto.setSug_name(result.getDto().get(i).getSug_name());
                        dto.setSug_price(result.getDto().get(i).getSug_price());
                        dtos.add(dto);
                    }
                    BindRecyclerItem(mAc,dtos);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SurgeryResult> call, Throwable t) {
                Toast.makeText(mCon, "통신에러1", Toast.LENGTH_SHORT).show();
            }
        });
    }
}