package com.egg8.ui.supp;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.egg8.R;
import com.egg8.common.GlobalApplication;

public class SuppCategory extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private Button btn_enroll_in;
    private CheckBox[] chks = new CheckBox[6];
    private TextView tv_ct;
    private Activity mAc;
    private Context mCon;
    int chked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll3);
        mAc = this;
        mCon = this;
        findId(mAc);
    }

    private void findId(Activity v){
        btn_enroll_in = v.findViewById(R.id.btn_enroll_in);
        tv_ct = v.findViewById(R.id.tv_ct);
        for(int i = 0; i < chks.length; i++) {
            chks[i] = v.findViewById(R.id.ck00+i);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(chked > 0) {
            btn_enroll_in.setEnabled(false);
        } else {
            btn_enroll_in.setEnabled(true);
        }
        if(buttonView.isChecked()){
            GlobalApplication.showToastMsg(mCon, buttonView.getText().toString()+" 선택됨");
            if(chked == 3){

            }
            chked ++;
        } else {
            chked --;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_enroll_in) {
            GlobalApplication.showToastMsg(mCon, "버튼 클릭");
        }
    }

    private void setCategoryUpdate() {

    }
}
