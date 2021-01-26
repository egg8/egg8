package com.egg8.ui.user.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import com.egg8.R;
import com.egg8.common.function.Popup;

public class Register_Terms extends AppCompatActivity {

    private Activity mAc;
    private Context mCon;
    private Button nextStep;
    private  CheckBox[] chks = new CheckBox[4];;
    private  TextView[] tvs = new TextView[3];

    int TERMS_AGREE_1 = 0; // No Check = 0, Check = 1
    int TERMS_AGREE_2 = 0; // No Check = 0, Check = 1
    int TERMS_AGREE_3 = 0; // No Check = 0, Check = 1
    int TERMS_AGREE_4 = 0; // No Check = 0, Check = 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_terms);
        mAc = this;
        mCon = this;
        findId(mAc);
    }

    private void findId(Activity v){
        nextStep = v.findViewById(R.id.nextBtn);
        for(int i=0; i<chks.length; i++) {
            chks[i] = v.findViewById(R.id.checkbox00+i);
            chks[i].setOnCheckedChangeListener(chkListener);
        }
        for(int i=0; i<tvs.length; i++) {
            tvs[i] = v.findViewById(R.id.btn_view00+i);
            tvs[i].setOnClickListener(tvListener);
        }
    }

    public void CheckedBtn(CheckBox btn, Boolean chk){
        btn.setChecked(chk);
    }

    View.OnClickListener tvListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_view00:
                    Popup.callFunction(v.getContext(),"http://222.100.239.140:8888/agree/agree1");
                    break;
                case R.id.btn_view01:
                    Popup.callFunction(v.getContext(),"http://222.100.239.140:8888/agree/agree2");
                    break;
                case R.id.btn_view02:
                    Popup.callFunction(v.getContext(),"http://222.100.239.140:8888/agree/agree3");
                    break;
                case R.id.nextBtn:
                    Toast.makeText(Register_Terms.this, "약관동의 완료 본인인증을 해주세요", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), Register_SMS.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
    CheckBox.OnCheckedChangeListener chkListener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.checkbox00 :
                    if (isChecked) {
                        for(int i=0; i<chks.length; i++) {
                            CheckedBtn(chks[i],true);
                        }
                        TERMS_AGREE_1 = 1;
                        nextStep.setEnabled(true);
                        nextStep.setTextColor(Color.BLACK);
                        nextStep.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        for(int i=0; i<chks.length; i++) {
                            CheckedBtn(chks[i],false);
                        }
                        TERMS_AGREE_1 = 0;
                        nextStep.setEnabled(false);
                        nextStep.setTextColor(Color.GRAY);
                    }
                    break ;
                case R.id.checkbox01 :
                    if (isChecked) {
                        TERMS_AGREE_2 = 1;
                        if(TERMS_AGREE_2+TERMS_AGREE_3+TERMS_AGREE_4 == 3) {
                            CheckedBtn(chks[0],true);
                        }
                    } else {
                        CheckedBtn(chks[0],false);
                        TERMS_AGREE_2 = 0;
                    }
                    break ;
                case R.id.checkbox02 :
                    if (isChecked) {
                        TERMS_AGREE_3 = 1;
                        if(TERMS_AGREE_2+TERMS_AGREE_3+TERMS_AGREE_4 == 3) {
                            CheckedBtn(chks[0],true);
                        }
                    } else {
                        CheckedBtn(chks[0],false);
                        TERMS_AGREE_3 = 0;
                    }
                    break ;
                case R.id.checkbox03 :
                    if (isChecked) {
                        TERMS_AGREE_4 = 1;
                        if(TERMS_AGREE_2+TERMS_AGREE_3+TERMS_AGREE_4 == 3) {
                            CheckedBtn(chks[0],true);
                        }
                    } else {
                        CheckedBtn(chks[0],false);
                        TERMS_AGREE_4 = 0;
                    }
                    break ;
            }
        }
    };
}