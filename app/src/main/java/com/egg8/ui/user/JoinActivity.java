package com.egg8.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.egg8.R;

public class JoinActivity extends AppCompatActivity {


    Button nextStep;
    CheckBox check1, check2, check3, check4; //전체동의,서비스,개인정보,광고

    public int TERMS_AGREE_1 = 0; // No Check = 0, Check = 1
    public int TERMS_AGREE_2 = 0; // No Check = 0, Check = 1
    public int TERMS_AGREE_3 = 0; // No Check = 0, Check = 1
    public int TERMS_AGREE_4 = 0; // No Check = 0, Check = 1

    public int Check1=0;
    public int Check2=0;
    public int Check3=0;
    public int Check4=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check1 = findViewById(R.id.checkbox);
        check2 = findViewById(R.id.checkbox2);
        check3 = findViewById(R.id.checkbox3);
        check4 = findViewById(R.id.checkbox4);
        nextStep = findViewById(R.id.nextBtn);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JoinActivity.this, "약관동의 완료 다음 인텐트를 구현 ㄱ", Toast.LENGTH_SHORT).show();
            }
        });
        check1.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    check2.setChecked(true);
                    check3.setChecked(true);
                    check4.setChecked(true);
                    TERMS_AGREE_1 = 1;
                    nextStep.setEnabled(true);
                    nextStep.setTextColor(Color.BLACK);
                } else {
                    check2.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                    TERMS_AGREE_1 = 0;
                    nextStep.setEnabled(false);
                    nextStep.setTextColor(Color.GRAY);
                }
            }
        });
        check2.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TERMS_AGREE_2 = 1;
                    if(TERMS_AGREE_2+TERMS_AGREE_3+TERMS_AGREE_4 == 3) {
                        check1.setChecked(true);
                    }
                } else {
                    check1.setChecked(false);
                    TERMS_AGREE_2 = 0;
                }
            }
        });
        check3.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TERMS_AGREE_3 = 1;
                    if(TERMS_AGREE_2+TERMS_AGREE_3+TERMS_AGREE_4 == 3) {
                        check1.setChecked(true);
                    }
                } else {
                    check1.setChecked(false);
                    TERMS_AGREE_3 = 0;
                }
            }
        });
        check4.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TERMS_AGREE_4 = 1;
                    if(TERMS_AGREE_2+TERMS_AGREE_3+TERMS_AGREE_4 == 3) {
                        check1.setChecked(true);
                    }
                } else {
                    check1.setChecked(false);
                    TERMS_AGREE_4 = 0;
                }
            }
        });
    }//onCreate
}//Main