package com.egg8.adapter.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.egg8.R;
import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.model.resrvation.MenuDTO;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShopMenuAdapter extends RecyclerView.Adapter<ShopMenuAdapter.ViewHolder> {
    ArrayList<MenuDTO> arrayList;
    TextView Tv_Shop_Menu_Price;    //샵매뉴 가격
    TextView Tv_Shop_Menu_Name;     //샵메뉴


    public ShopMenuAdapter(ArrayList<MenuDTO> arrayList) {
        this.arrayList = arrayList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tv_Shop_Menu_Name=itemView.findViewById(R.id.Tv_Shop_Menu_Name);
            Tv_Shop_Menu_Price=itemView.findViewById(R.id.Tv_Shop_Menu_Price);

        }
        public void setItem(ArrayList<MenuDTO> arrayList , int i){
            DecimalFormat myFormatter = new DecimalFormat("###,###");
            String formattedStringPrice = myFormatter.format(Integer.parseInt(arrayList.get(i).getSUG_PRICE()));
            Tv_Shop_Menu_Name.setText(arrayList.get(i).getSUG_NAME());
            Tv_Shop_Menu_Price.setText(formattedStringPrice+"원");
        }

    }
    @NonNull
    @Override
    public ShopMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_menu_itme,parent,false);
        return new ShopMenuAdapter.ViewHolder(view);

    }

    //onBindViewHolder : 뷰홀더가 재활용될 때 실행되는 메서드
    @Override
    public void onBindViewHolder(@NonNull ShopMenuAdapter.ViewHolder holder, int position) {
        if(position > 0) {
            holder.setItem(arrayList,position);
        } else {
            Tv_Shop_Menu_Name.setText("시술 목록이 없습니다.");
        }
    }


    //getItemCount : 아이템 개수를 조회
    @Override
    public int getItemCount() {
        return arrayList.size();
    }



}
