package com.egg8.ui.reservation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.egg8.R;
import com.egg8.common.manager.SharedPreferenceManager;

public class ReservationActivity extends AppCompatActivity {
    private TextView tv_res_cnt, tv_supp_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaiton_view);
        findId(this);
    }

    private void findId(Activity v){
        tv_supp_name = v.findViewById(R.id.tv_supp_name);
        tv_res_cnt = v.findViewById(R.id.tv_res_cnt);
        setTextView(v);
    }

    private void setTextView(Context context){
        String supp_name = SharedPreferenceManager.getString(context,"supp_name");

        String spn = getString(R.string.supp_name);
        String txt1 = String.format(spn, supp_name);

        String cnt = getString(R.string.res_cnt);
        String txt2 = String.format(cnt, 5);

        tv_supp_name.setText(txt1);
        tv_res_cnt.setText(txt2);
    }

}
