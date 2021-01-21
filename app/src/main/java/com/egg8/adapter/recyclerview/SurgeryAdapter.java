package com.egg8.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.egg8.R;
import com.egg8.common.manager.SharedPreferenceManager;

import java.util.ArrayList;

public class SurgeryAdapter extends RecyclerView.Adapter<SurgeryAdapter.ViewHolder> {
    private ArrayList<String> arrayList;
    Context mCon;
    String result="";
    int lastSelectedPosition = -1;
    Button btn;


    public SurgeryAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton cb_surgery;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_surgery=itemView.findViewById(R.id.cb_surgery);

            cb_surgery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cb_surgery.isChecked()){
                        SharedPreferenceManager.setString(v.getContext(),"sug_name",cb_surgery.getText().toString());

                    }
                    lastSelectedPosition =getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
        public void setItem(ArrayList<String> arrayList , int i){
            cb_surgery.setText(arrayList.get(i));
        }

    }
    @NonNull
    @Override
    public SurgeryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_re_surgery,parent,false);
        return new ViewHolder(view);

    }

    //onBindViewHolder : 뷰홀더가 재활용될 때 실행되는 메서드
    @Override
    public void onBindViewHolder(@NonNull SurgeryAdapter.ViewHolder holder, int position) {
        holder.setItem(arrayList,position);
        holder.cb_surgery.setChecked(lastSelectedPosition == position);


    }


    //getItemCount : 아이템 개수를 조회
    @Override
    public int getItemCount() {
        return arrayList.size();
    }





}

