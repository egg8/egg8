package com.egg8.adapter.recyclerview;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.egg8.R;
import com.egg8.model.supp.SuppDTO;
import com.egg8.model.supp.SuppListDTO;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {
    TextView Tv_Shop_Name,Tv_Shop_Address,Tv_Shop_Category,Tv_Start_Time,Tv_End_Time,Tv_Time;
    ArrayList<SuppListDTO> arrayList;

    public ShopListAdapter(ArrayList<SuppListDTO> arrayList) {
        this.arrayList = arrayList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tv_Shop_Name=itemView.findViewById(R.id.Tv_Shop_Name);
            Tv_Shop_Address=itemView.findViewById(R.id.Tv_Shop_Address);
            Tv_Shop_Category=itemView.findViewById(R.id.Tv_Shop_Category);
            Tv_Start_Time=itemView.findViewById(R.id.Tv_Start_Time);
            Tv_Time=itemView.findViewById(R.id.Tv_Time);
            Tv_End_Time=itemView.findViewById(R.id.Tv_End_Time);



        }
        public void setItem(ArrayList<SuppListDTO> arrayList , int i){
            Tv_Shop_Name.setText(arrayList.get(i).getSUPP_NAME());
            Tv_Shop_Address.setText(arrayList.get(i).getADDR_CITY());
            Tv_Shop_Category.setText(arrayList.get(i).getCATEGORY());
            Tv_Start_Time.setText(arrayList.get(i).getOPEN_TIME());
            Tv_End_Time.setText(arrayList.get(i).getCLOSED_TIME());


        }

    }

    @NonNull
    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item,parent,false);
        return new ShopListAdapter.ViewHolder(view);

    }

    //onBindViewHolder : 뷰홀더가 재활용될 때 실행되는 메서드
    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ViewHolder holder, int position) {
        holder.setItem(arrayList,position);



    }

    //getItemCount : 아이템 개수를 조회
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
