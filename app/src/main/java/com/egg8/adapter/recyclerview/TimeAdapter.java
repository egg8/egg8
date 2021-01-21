package com.egg8.adapter.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.egg8.R;
import com.egg8.model.string.ButtonDTO;
import java.util.ArrayList;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
    private ArrayList<ButtonDTO> mData;
    private Button btn_time;
    private OnItemClickListener mListener = null ;


    //OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public TimeAdapter(ArrayList<ButtonDTO> List) {
        mData = List;
    }

    //홀더: 리스너나 아이템 붙이는곳
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_time = itemView.findViewById(R.id.btn_time);
            btn_time.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition(); //아이템 번호
                    if (pos != RecyclerView.NO_POSITION){
                        if (mListener !=null){
                            mListener.onItemClick(v,pos);
                        }

                    }
                }
            });
        }

        public void setItem(ArrayList<ButtonDTO> arrayList , int i){
                if(arrayList.get(i).getBtnName().equals("휴게시간")){
                    btn_time.setEnabled(false);
                } else if(arrayList.get(i).getBtnName().equals("예약불가")) {
                    btn_time.setEnabled(false);
                }
                btn_time.setText(arrayList.get(i).getBtnName());
        }
    }


    @NonNull
    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.
                    from(parent.getContext()).
                    inflate(R.layout.item_re_time, parent,false);
        return new ViewHolder(view);
    }

    //onBindViewHolder : 뷰홀더가 재활용될 때 실행되는 메서드
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(mData,position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}