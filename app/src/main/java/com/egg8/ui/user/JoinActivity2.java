package com.egg8.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.egg8.R;
import com.egg8.common.function.GenerateCertNumber;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class JoinActivity2 extends AppCompatActivity {
    private int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    EditText phoneNun;
    EditText numcheck;
    Button sendBtn;
    Button nextBtn;
    String randomKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);
        //레이아웃 정의
        phoneNun = findViewById(R.id.td_phonenumber);
        numcheck = findViewById(R.id.td_numcheck);
        sendBtn = findViewById(R.id.sendBtn);
        nextBtn=findViewById(R.id.nextBtn);
        //다음 버튼 위의 텍스트
        nextBtn.setEnabled(false);
        nextBtn.setTextColor(Color.GRAY);
        //자동 하이폰
        phoneNun.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        //인증번호 입력 시 버튼 활성화 (초기 비활성화)
        numcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(numcheck.length()>0) {
                    nextBtn.setEnabled(true);
                    nextBtn.setTextColor(Color.BLACK);
                } else {
                    nextBtn.setEnabled(false);
                    nextBtn.setTextColor(Color.GRAY);
                }
            }
        });
        //인증번호 전송 및 재전송 제한 타이머
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Num = phoneNun.getText().toString();
                String Check = numcheck.getText().toString();
                //랜덤키 발생
                randomKey = GenerateCertNumber.CreatePhoneKey();
                String massge = "RES 인증번호 [" + randomKey + "] " + "입력 바랍니다.";
                //중복전송 제한 카운터.
                new CountDownTimer(60000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        sendBtn.setEnabled(false);
                        sendBtn.setTextColor(Color.GRAY);
                        sendBtn.setText(millisUntilFinished/1000 +"초");
                    }
                    @Override
                    public void onFinish() {
                        randomKey = null;
                        sendBtn.setEnabled(true);
                        sendBtn.setTextColor(Color.BLACK);
                        sendBtn.setText("전송");
                    }
                }.start();
                //중복전송 제한 카운터 끝.
                //문자전송
                try {
                    SmsManager smsManager =SmsManager.getDefault();
                    smsManager.sendTextMessage(Num,null,massge,null,null);
                    Toast.makeText(getApplicationContext(),"전송 완료",Toast.LENGTH_LONG).show();


                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"전송 실패",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }//onClick
        }); //sendBtn.setOnclickListener;
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인증키가 작성되지 않은경우
                if(numcheck.getText().toString().equals("") || numcheck.getText().toString().equals(null)) {
                    Toast.makeText(getApplicationContext(), "인증번호를 입력 하세요.", Toast.LENGTH_LONG).show();
                }
                // 인증키가 맞는 경우
                if(numcheck.getText().toString().equals(randomKey)){
                    Toast.makeText(getApplicationContext(),"인증완료 회원가입을 계속 진행해 주세요.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), JoinActivity3.class);
                    startActivity(intent);
                    finish();
                }
                // 인증키가 다를 경우
                if(!numcheck.getText().toString().equals(randomKey)) {
                    Toast.makeText(getApplicationContext(),"인증번호가 맞지 않습니다. 인증번호를 재확인 해주세요.",Toast.LENGTH_LONG).show();
                }
            }//onClick
        });//nextBtn.setOnClickListener
    }//onCreate
}//Main
