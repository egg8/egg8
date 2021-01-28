package com.egg8.ui.supp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.egg8.R;

public class ChangeCategory extends AppCompatActivity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Activity mAc;
    private Context mCon;
    private CheckBox[] cBox = new CheckBox[6];
    private Button btn_enroll_in;
    int checked = 0;

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
        }
        btn_enroll_in = v.findViewById(R.id.btn_enroll_in);
    }

    @Override
    public void onClick(View v) {

    }
    private void checkedBtnEnabled(){
        if(checked > 0) {
            btn_enroll_in.setEnabled(true);
            if(checked <= 3) {
                for (int i = 0; i < cBox.length; i++){
                    cBox[i].setEnabled(false);
                }
            } else {
                for (int i = 0; i < cBox.length; i++){
                    cBox[i].setEnabled(true);
                }
            }
        } else {
            btn_enroll_in.setEnabled(false);
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkedBtnEnabled();
        if(buttonView.isChecked()){
            checked ++;
        } else {
            checked --;
        }
    }
}
