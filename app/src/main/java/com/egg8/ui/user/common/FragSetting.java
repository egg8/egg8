package com.egg8.ui.user.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egg8.R;
import com.egg8.ui.supp.EnrollActivity;

public class FragSetting extends Fragment {

    TextView btn_password_change,btn_reslist_view,btn_enroll_shop,btn_logout,btn_withdrawal;
    Context mCo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_frag_setting, container, false);
        mCo=container.getContext();
        findId(rootView);
        btn_enroll_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EnrollActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
    public void findId(View v){
        btn_password_change=v.findViewById(R.id.btn_password_change); //비밀번호 변경
        btn_reslist_view=v.findViewById(R.id.btn_reslist_view); //예약 리스트 보기
        btn_enroll_shop=v.findViewById(R.id.btn_enroll_shop); //샵 등록
        btn_logout=v.findViewById(R.id.btn_logout);
        btn_withdrawal=v.findViewById(R.id.btn_withdrawal); //회원탈퇴
    }
}