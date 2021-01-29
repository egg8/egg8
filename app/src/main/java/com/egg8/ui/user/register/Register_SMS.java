package com.egg8.ui.user.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.egg8.R;
import com.egg8.common.GlobalApplication;
import com.egg8.common.function.GenerateCertNumber;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register_SMS extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Register_SMS";
    private Activity mAc;
    private Context mCon;
    EditText phoneNum;
    EditText numCheck;
    Button sendBtn;
    Button nextBtn;
    String randomKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sms);
        mAc = this;
        mCon = this;
        findId(mAc);
        phoneNumFormatting();
    }

    private void findId(Activity v) {
        phoneNum = v.findViewById(R.id.td_phonenumber);
        numCheck = v.findViewById(R.id.td_numcheck);
        sendBtn = v.findViewById(R.id.sendBtn);
        nextBtn = v.findViewById(R.id.nextBtn);
        eventListener();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Num = phoneNum.getText().toString();
                //랜덤키 발생
                GenerateCertNumber generateCertNumber = new GenerateCertNumber();
                randomKey = GenerateCertNumber.CreatePhoneKey();
                String msg = "RES 인증번호 [" + randomKey + "] " + "입력 바랍니다.";
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
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(Num,null,msg,null,null);
                    GlobalApplication.showToastMsg(mCon,"전송 완료");
                } catch (Exception e) {
                    GlobalApplication.showToastMsg(mCon,"전송 실패");
                    e.printStackTrace();
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 인증키가 작성되지 않은경우
                if(numCheck.getText().toString().equals("") || numCheck.getText().toString().equals(null)) {
                    GlobalApplication.showToastMsg(mCon,"인증번호를 입력 하세요.");
                }
                // 인증키가 맞는 경우
                if(numCheck.getText().toString().equals(randomKey)){
                    GlobalApplication.showToastMsg(mCon,"인증완료 회원가입을 계속 진행해 주세요.");
                    Intent intent = new Intent(getApplicationContext(), Register_Base.class);
                    startActivity(intent);
                    finish();
                }
                // 인증키가 다를 경우
                if(!numCheck.getText().toString().equals(randomKey)) {
                    GlobalApplication.showToastMsg(mCon,"인증번호가 일치하지 않습니다.");
                }
            }
        });
    }

    private void eventListener() {
        numCheck.addTextChangedListener(textWatcher);
    }

    private void phoneNumFormatting() {
        phoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            if(numCheck.length()>0) {
                nextBtn.setEnabled(true);
                nextBtn.setTextColor(Color.BLACK);
            } else {
                nextBtn.setEnabled(false);
                nextBtn.setTextColor(Color.GRAY);
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };


    @Override
    public void onClick(View v) {

    }
}
