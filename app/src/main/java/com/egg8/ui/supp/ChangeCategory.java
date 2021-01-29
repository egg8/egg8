package com.egg8.ui.supp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.egg8.R;
import com.egg8.common.GlobalApplication;

import java.util.ArrayList;

public class ChangeCategory extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Activity mAc;
    private Context mCon;
    private CheckBox[] cBox = new CheckBox[6];
    private Button btn_enroll_in;
    private ImageButton btn_rollback;
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
    //for문 돌면서 카테고리 6개를 추가함
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
    //롤백 버튼이 눌려지면 sb에 담긴 값을 다 지우고, 버튼 비활성화, 완료버튼 비활성화,롤백 버튼은 인비지블이 됨
    //엔롤 버튼이 눌리면 체크된 항목이 토스트에 보여지게 됨
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
                GlobalApplication.showToastMsg(mCon,result);
                break;
        }
    }
    //false 시킬 체크박스를 찾는 메서드
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
    //체크된 항목이 0개면 버튼이 비활성화 되는데, 3개가 되는 순간 버튼이 활성화됨 엘쓰 버튼 비활성화
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
    //배열에 체크박스 텍스트가 /가 추가되며 담기게 된다.
    private void setSelectedText(){
        for(int i=0; i<cBox.length; i++) {
            if(cBox[i].isChecked()){
                sb.append(cBox[i].getText().toString() + "/");
            }
        }
    }
    //
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
}
