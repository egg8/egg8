package com.egg8.ui.supp;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.egg8.R;

public class EnrollActivity2 extends AppCompatActivity {
    TextView btn_open_time, btn_close_time;
    ImageButton btn_add;
    Context mCom;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_open_time = findViewById(R.id.btn_open_time);
        btn_close_time = findViewById(R.id.btn_close_time);
        btn_add = findViewById(R.id.btn_add);
        TimePickerDialog dialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, listener, 15, 24, true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); //배경화면 투명색



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
}