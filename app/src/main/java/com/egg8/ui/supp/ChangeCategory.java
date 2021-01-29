package com.egg8.ui.supp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.egg8.R;
import com.egg8.common.GlobalApplication;
import com.egg8.common.dto.Result;
import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeCategory extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Activity mAc;
    private Context mCon;
    private CheckBox[] cBox = new CheckBox[6];
    private Button btn_enroll_in;
    private ImageButton btn_rollback;
    private RetrofitBuilder builder;
    private RetrofitService service;
    int checked = 0;
    String result;
    StringBuffer sb = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll3);
        mAc = this;
        mCon = this;
        findId(mAc);
    }

    private void findId(Activity v) {
        for (int i = 0; i < cBox.length; i++){
            cBox[i] = v.findViewById(R.id.ck00+i);
            cBox[i].setOnCheckedChangeListener(this);
        }
        btn_enroll_in = v.findViewById(R.id.btn_enroll_in);
        btn_enroll_in.setOnClickListener(this);
        btn_rollback = v.findViewById(R.id.btn_rollback);
        btn_rollback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rollback :
                checked = 0;
                sb.delete(0, sb.length());
                btn_enroll_in.setEnabled(false);
                btn_rollback.setVisibility(View.INVISIBLE);
                enabledCheckBox(true);
                break;
            case R.id.btn_enroll_in :
                setSelectedText();
                result = sb.substring(0, sb.length()-1);
                setChangedCategory(v,result);
                break;
        }
    }
    private void enabledCheckBox(Boolean b) {
        if(b == true) {
            for (int i = 0; i < cBox.length; i++){
                checked = 0;
                cBox[i].setChecked(false);
            }
        }
        for (int i = 0; i < cBox.length; i++){
            checked = 0;
            cBox[i].setEnabled(b);
        }
    }

    private void checkedBtnEnabled(){
        if(checked > 0) {
            btn_enroll_in.setEnabled(true);
            if(checked == 3) {
                enabledCheckBox(false);
                btn_rollback.setVisibility(View.VISIBLE);
            }
        } else {
            btn_enroll_in.setEnabled(false);
        }
    }

    private void setSelectedText(){
        for(int i=0; i<cBox.length; i++) {
            if(cBox[i].isChecked()){
                sb.append(cBox[i].getText().toString() + "/");
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.isChecked()){
            if(checked == 0){
                checked += 1;
            } else {
                checked ++;
            }
            checkedBtnEnabled();
        } else {
            if (checked > 0) {
                checked --;
            }
            if(checked == 0){
                btn_enroll_in.setEnabled(false);
            }
        }
    }

    private void setChangedCategory(View v,String ct) {
        builder = RetrofitBuilder.getInstance("http://222.100.239.140:8888/");
        service = RetrofitBuilder.getRetrofitService();
        Call<Result> call = service.changedCategory(SharedPreferenceManager.getString(mCon,"supp_code"), ct);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()) {
                    Result dto = response.body();
                    if(dto.getResult().equals("success")) {
                       GlobalApplication.showSnackBar(v, "카테고리 설정이 완료되었습니다.");
                    } else {
                        GlobalApplication.showSnackBar(v, "카테고리 업데이트에 실패하였습니다.");
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                GlobalApplication.showSnackBar(v, "통신에러");
            }
        });
    }
}
