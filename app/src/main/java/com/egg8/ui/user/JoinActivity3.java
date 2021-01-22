package com.egg8.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.egg8.R;
import com.egg8.common.dto.Result;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.user.UserDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity3 extends AppCompatActivity {

    EditText join_email, join_pw, user_name;
    Button btn_join_comple;
    String id, pwd, name, tel;
    RetrofitBuilder retrofitBuilder;
    RetrofitService service;
    Context context;
    ImageView back_key;
    TextView ID_Check, Pw_Check, email_trg, pwd_trg, name_trg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join3);

        context = this;
        join_email = findViewById(R.id.join_email);
        join_pw = findViewById(R.id.join_pw);
        user_name = findViewById(R.id.user_name);
        btn_join_comple = findViewById(R.id.btn_join_comple);
        back_key = findViewById(R.id.back_key);
        ID_Check = findViewById(R.id.ID_Check);
        Pw_Check = findViewById(R.id.Pw_Check);
        email_trg = findViewById(R.id.email_trg);
        name_trg = findViewById(R.id.name_trg);
        pwd_trg = findViewById(R.id.pwd_trg);

        back_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });//뒤로가기

        btn_join_comple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = join_email.getText().toString();
                pwd = join_pw.getText().toString();
                name = user_name.getText().toString();
                tel = join_email.getText().toString();
                int e_trg = (Integer.parseInt(email_trg.getText().toString()));
                int p_trg = (Integer.parseInt(pwd_trg.getText().toString()));
                int status = e_trg+p_trg;
                if(e_trg == 0 && p_trg == 1) {
                    Toast.makeText(context, "이메일 형식을 확인하세요.", Toast.LENGTH_SHORT).show();
                } else if(e_trg == 1 && p_trg == 0){
                    Toast.makeText(context, "비밀번호 형식을 확인하세요.", Toast.LENGTH_SHORT).show();
                } else if(e_trg == 0 && p_trg == 0) {
                    Toast.makeText(context, "회원가입 형식을 확인하세요.", Toast.LENGTH_SHORT).show();
                }
                else if (status == 2){
                    retrofitBuilder= RetrofitBuilder.getInstance("http://222.100.239.140:11005/");
                    service = retrofitBuilder.getRetrofitService();
                    Call<Result> call = service.Join(id, pwd, name, tel, status);
                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if(response.body() != null){
                                Result result = response.body();
                                if(result.getResult().equals("success")){
                                    Toast.makeText(context, "회원가입 성공",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(context, result.getResult(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Toast.makeText(context, "회원가입 실패",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });//항목통과시 화원가입처리


        join_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                id = join_email.getText().toString();

                retrofitBuilder= RetrofitBuilder.getInstance("http://222.100.239.140:11005/");
                service = retrofitBuilder.getRetrofitService();
                Call<UserDTO> call = service.idCheck(id);
                call.enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        if(response.body() != null){
                            UserDTO result = response.body();
                            if(!isValidEmail(id)) {
                                if(join_email.getText().toString().equals("")){
                                    ID_Check.setText("");
                                } else {
                                    email_trg.setText("0");
                                    ID_Check.setText("올바른 이메일 형식이 아닙니다.");
                                    ID_Check.setTextColor(Color.parseColor("#FB0303"));
                                }
                            } else {
                                if(join_email.getText().toString().equals("")){
                                    ID_Check.setText("");
                                } else {
                                    if(result.getResult().equals("1")){
                                        email_trg.setText("0");
                                        ID_Check.setText("사용할수 없는 아이디입니다.");
                                        ID_Check.setTextColor(Color.parseColor("#FB0303"));
                                    } else if(result.getResult().equals("0")) {
                                        ID_Check.setText("사용할수 있는 아이디입니다.");
                                        ID_Check.setTextColor(Color.parseColor("#0067a3"));
                                        email_trg.setText("1");
                                    } else {
                                        ID_Check.setText("통신 에러");
                                        ID_Check.setTextColor(Color.parseColor("#FF3700B3"));
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        ID_Check.setText("알수없는 오류입니다. 관리자에게 문의하세요");
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });//아이디(이메일)중복확인과 정규식검증

        join_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwd = join_pw.getText().toString();

                if (!isValidPwd(pwd)) {
                    if(pwd.equals("")){
                        Pw_Check.setText("");
                    } else {
                        pwd_trg.setText("0");
                        Pw_Check.setText("올바른 패스워드 형식이 아닙니다.");
                        Pw_Check.setTextColor(Color.parseColor("#FB0303"));
                    }
                } else {
                    if(join_email.getText().toString().equals("")){
                        ID_Check.setText("");
                    } else {
                        Pw_Check.setText("");
                        pwd_trg.setText("1");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });//패스워드 일치와 정규식검증


    }
    /*@Override
    public void onBackPressed() {
        backPressedManager.onBackPressed();
    }*/


    /**
     * 정규식
     **/
    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }
    /**
     1. 영문(대소문자 구분), 숫자, 특수문자 조합
     2. 9~12자리 사이 문자
     3. 같은 문자 4개 이상 사용 불가
     4. 비밀번호에 ID 포함 불가
     5. 공백문자 사용 불가
     **/
    public static boolean isValidPwd(String pwd) {
        boolean err = false;
        String regex = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pwd);
        if(m.matches()) {
            err = true;
        }
        return err;
    }
}