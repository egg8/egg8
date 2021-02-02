package com.egg8.ui.user.common;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.egg8.R;
import com.egg8.adapter.recyclerview.ShopListAdapter;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;
import com.egg8.model.supp.SuppListDTO;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragShopList extends Fragment{
    RetrofitBuilder retrofitBuilder;
    RetrofitService retrofitService;
    ShopListAdapter shopListAdapter;
    Context mCon;
    Button Btn_Shop_Search;
    RecyclerView Shop_List_RecyclerView;
    ArrayList<SuppListDTO> arrayList;
    EditText et_serch;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_frag_shop_list, container, false);
        mCon=container.getContext();
        findId(rootView);
        getShopList(mCon);
        return rootView;
    }

    public void findId(View v) {
        et_serch =v.findViewById(R.id.et_serch);
        Btn_Shop_Search =v.findViewById(R.id.Btn_Shop_Search);
        Shop_List_RecyclerView =v.findViewById(R.id.Shop_List_RecyclerView);
        listenerEvent();
    }

    private void listenerEvent() {
        et_serch.addTextChangedListener(textWatcher);
        Btn_Shop_Search.setOnClickListener(clickListener);

    }

    public void ShopList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mCon, RecyclerView.VERTICAL, false);
        Shop_List_RecyclerView.setLayoutManager(layoutManager);
        shopListAdapter = new ShopListAdapter(mCon,arrayList);
        Shop_List_RecyclerView.setAdapter(shopListAdapter);
        Shop_List_RecyclerView.setHasFixedSize(true);
    }
    public void getShopList(Context context) {

        retrofitBuilder.getInstance("http://222.100.239.140:8888/");
        retrofitService = retrofitBuilder.getRetrofitService();
        Call<SuppListDTO> call = retrofitService.getSupp();

        call.enqueue(new Callback<SuppListDTO>() {
            @Override
            public void onResponse(Call<SuppListDTO> call, Response<SuppListDTO> response) {
                // 통신 성공
                if (response.isSuccessful()) {
                    SuppListDTO result = response.body();
                    arrayList = new ArrayList<>();
                    if (response.body() != null) {
                        for (int i = 0; i < result.getResult().size(); i++) {
                            SuppListDTO dto = new SuppListDTO();
                            dto.setSUPP_CODE(result.getResult().get(i).getSUPP_CODE());
                            dto.setSUPP_NAME(result.getResult().get(i).getSUPP_NAME());
                            dto.setADDR_CITY(result.getResult().get(i).getADDR_CITY());
                            dto.setCATEGORY(result.getResult().get(i).getCATEGORY());
                            dto.setOPEN_TIME(result.getResult().get(i).getOPEN_TIME());
                            dto.setCLOSED_TIME(result.getResult().get(i).getCLOSED_TIME());
                            arrayList.add(dto);
                            Log.d("msg","성공");

                        }
                        ShopList();
                    } else {
                        Log.d("msg","꽝");
                    }
                } else {
                    Log.d("결과값 : ", "onResponse : 실패");
                }
            }
            @Override
            public void onFailure(Call<SuppListDTO> call, Throwable t) {
                Log.d("결과 값 : ", "통신 불가" + t.getMessage());
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            shopListAdapter.getFilter().filter(s.toString());

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Btn_Shop_Search:
                    et_serch.setVisibility(View.VISIBLE);
                    et_serch.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    break;
            }
        }
    };
}
