package com.egg8.adapter.recyclerview;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
import com.egg8.R;
import com.egg8.model.supp.SuppListDTO;
import com.egg8.ui.shop.ShopActivity;

import java.util.ArrayList;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder>implements Filterable {
    ArrayList<SuppListDTO> _filterList;
    ArrayList<SuppListDTO> filterList;
    Context mCon;

    public ShopListAdapter(Context context, ArrayList<SuppListDTO> arrayList) {
        this._filterList = arrayList;
        this.filterList= arrayList;
        this.mCon=context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterList = _filterList;
                } else {
                    ArrayList<SuppListDTO> filteredList = new ArrayList<>();
                    for (SuppListDTO item : _filterList) {
                        if (item.getSUPP_NAME().toLowerCase().contains(charString)) {
                            Log.d("msg1",charString);
                            Log.d("msg2",item.getSUPP_NAME());
                            filteredList.add(item);
                        }
                    }
                    filterList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<SuppListDTO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Tv_Shop_Name,Tv_Shop_Address,Tv_Shop_Category,Tv_Start_Time,Tv_End_Time,Tv_Time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tv_Shop_Name=itemView.findViewById(R.id.Tv_Shop_Name);
            Tv_Shop_Address=itemView.findViewById(R.id.Tv_Shop_Address);
            Tv_Shop_Category=itemView.findViewById(R.id.Tv_Shop_Category);
            Tv_Start_Time=itemView.findViewById(R.id.Tv_Start_Time);
            Tv_Time=itemView.findViewById(R.id.Tv_Time);
            Tv_End_Time=itemView.findViewById(R.id.Tv_End_Time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Log.d("msg",filterList.get(pos).getSUPP_CODE());
                        Intent intent = new Intent(mCon, ShopActivity.class);
                        intent.putExtra("supp_code",filterList.get(pos).getSUPP_CODE());
                        intent.putExtra("supp_name",filterList.get(pos).getSUPP_NAME());
                        intent.putExtra("pos",pos);
                        mCon.startActivity(intent);
                    }
                }
            });
        }
        public void setItem(int i){
            Log.d("msg5",filterList.get(i).getSUPP_NAME());
            Tv_Shop_Name.setText(filterList.get(i).getSUPP_NAME());
            Tv_Shop_Address.setText(filterList.get(i).getADDR_CITY());
            Tv_Shop_Category.setText(filterList.get(i).getCATEGORY());
            Tv_Start_Time.setText(filterList.get(i).getOPEN_TIME());
            Tv_End_Time.setText(filterList.get(i).getCLOSED_TIME());
        }
    }

    @Override
    public ShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(mCon).inflate(R.layout.shop_list_item,parent,false);
        return new ShopListAdapter.ViewHolder(view);
    }

    //onBindViewHolder : 뷰홀더가 재활용될 때 실행되는 메서드
    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.ViewHolder holder, int position) {
        holder.setItem(position);

    }
    //getItemCount : 아이템 개수를 조회
    @Override
    public int getItemCount() {
        return filterList.size();
    }

}
