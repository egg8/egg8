package com.egg8.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.egg8.R;
import com.egg8.common.dto.Res.ResDTO;

import java.util.ArrayList;

public class Res_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ResDTO> mList;

    public Res_RecyclerViewAdapter(ArrayList<ResDTO> list){
        this.mList = list;
    }

    public class ViewHolderPage extends RecyclerView.ViewHolder {
        private TextView[] tvs = new TextView[4];
        public ViewHolderPage(View itemView) {
            super(itemView);
            for(int i=0; i<tvs.length; i++){
                tvs[i] = itemView.findViewById(R.id.tvs00+i);
            }
        }
        public void itemBind(int pos) {
            tvs[0].setText(mList.get(pos).getSUPP_NAME());
            tvs[1].setText(mList.get(pos).getRES_IN_NAME());
            tvs[2].setText(mList.get(pos).getFULL_ADDRESS());
            tvs[3].setText(mList.get(pos).getPON_NO());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context mCon = parent.getContext();
        View v = LayoutInflater.from(mCon).inflate(R.layout.res_items, parent, false);
        v.setLayoutParams(parent.getLayoutParams());
        return new ViewHolderPage(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderPage viewHolderPage = (ViewHolderPage) holder;
        viewHolderPage.itemBind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
