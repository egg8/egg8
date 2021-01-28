package com.egg8.adapter.recyclerview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.egg8.R;
import com.egg8.common.dto.surgery.SurgeryDTO;
import com.egg8.common.dto.surgery.SurgeryResult;
import com.egg8.common.retrofit.RetrofitBuilder;
import com.egg8.common.retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
* since 2021/01/28
* 작성자: 안성엽
* 기능: 다이얼로그를 띄워서 편집/삭제 기능을 한다.
*/

public class SurgeryRecyclerAdapter extends RecyclerView.Adapter<SurgeryRecyclerAdapter.SurgeryViewHolder> {
    private ArrayList<SurgeryDTO> dtos;
    private Context mCon;
    Button btn;
    RetrofitBuilder retrofitBuilder;
    RetrofitService service;
    String name, price, idx;


    public SurgeryRecyclerAdapter(Activity activity, Context context, ArrayList<SurgeryDTO> list) {
        dtos = list;
        mCon = context;
        btn = activity.findViewById(R.id.btn_addData);

    }
    @Override
    public SurgeryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
        SurgeryViewHolder holder = new SurgeryViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(SurgeryViewHolder holder, int i) {
        holder.rc_sug_idx.setText(dtos.get(i).getSug_idx());
        holder.rc_sug_name.setText(dtos.get(i).getSug_name());
        holder.rc_sug_price.setText(dtos.get(i).getSug_price());
    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }

    public class SurgeryViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        protected TextView rc_sug_name;
        protected TextView rc_sug_price;
        protected TextView rc_sug_idx;


        public SurgeryViewHolder(View v) {
            super(v);
            this.rc_sug_idx = (TextView) v.findViewById(R.id.rc_sug_idx);
            this.rc_sug_name = (TextView) v.findViewById(R.id.rc_sug_name);
            this.rc_sug_price = (TextView) v.findViewById(R.id.rc_sug_price);
            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }
        //롱클릭으로 편집/삭제 메뉴를 띄워준다

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1001:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mCon);
                        View view = LayoutInflater.from(mCon)
                                .inflate(R.layout.dialog_put_data, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button) view.findViewById(R.id.btn_put);
                        EditText et_sug_name = (EditText) view.findViewById(R.id.et_sug_name);
                        EditText et_sug_price = (EditText) view.findViewById(R.id.et_sug_price);
                        et_sug_name.setText(dtos.get(getAdapterPosition()).getSug_name());
                        et_sug_price.setText(dtos.get(getAdapterPosition()).getSug_price());

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                idx = rc_sug_idx.getText().toString();
                                name = et_sug_name.getText().toString();
                                price = et_sug_price.getText().toString();
                                retrofitBuilder= RetrofitBuilder.getInstance("http://222.100.239.140:11005/");
                                service = retrofitBuilder.getRetrofitService();
                                Call<SurgeryDTO> call = service.sugEdit(idx, name, price, 1);
                                call.enqueue(new Callback<SurgeryDTO>(){
                                    @Override
                                    public void onResponse(Call<SurgeryDTO> call, Response<SurgeryDTO> response) {
                                        if(response.isSuccessful()){
                                            SurgeryDTO dto = response.body();
                                            if(dto.getResult().equals("success")){
                                                dialog.dismiss();
                                                getMenuList();
                                                Toast.makeText(mCon, "수정완료", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<SurgeryDTO> call, Throwable t) {
                                        Log.d("msg",t.getMessage());
                                        Toast.makeText(mCon, "수정실패", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                        //1001: 서버와 통신을 하며 다이얼로그에 작성한대로 편집을 하고 데이터에 넣는다.

                    case 1002:

                        AlertDialog.Builder builder2 = new AlertDialog.Builder(mCon);
                        View view2 = LayoutInflater.from(mCon)
                                .inflate(R.layout.dialog_del_data, null, false);
                        builder2.setView(view2);
                        final Button ButtonDel = (Button) view2.findViewById(R.id.btn_del);
                        final AlertDialog dialog2 = builder2.create();
                        ButtonDel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                idx = rc_sug_idx.getText().toString();
                                retrofitBuilder= RetrofitBuilder.getInstance("http://222.100.239.140:11005/");
                                service = retrofitBuilder.getRetrofitService();
                                Call<SurgeryDTO> call = service.sugEdit(idx, "", "", 2);
                                call.enqueue(new Callback<SurgeryDTO>() {
                                    @Override
                                    public void onResponse(Call<SurgeryDTO> call, Response<SurgeryDTO> response) {
                                        SurgeryDTO dto = response.body();
                                        Log.d("msg",dto.getResult());

                                        if(response.isSuccessful()){
                                            if(!dto.getResult().equals("")) {
                                                Log.d("msg",response.message());
                                                getMenuList();
                                                dialog2.dismiss();
                                                dtos.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());
                                                Toast.makeText(mCon, dto.getResult(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<SurgeryDTO> call, Throwable t) {
                                        Log.d("msg",t.getMessage());
                                        Toast.makeText(mCon, "삭제실패", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialog2.dismiss();
                            }
                        });
                        dialog2.show();
                        break;
                    //1002: 서버와 통신을 하여 데이터를 삭제한다.
                }
                return true;
            }
        };
    }
    private void getMenuList(){
        retrofitBuilder= RetrofitBuilder.getInstance("http://222.100.239.140:11005/");
        service = retrofitBuilder.getRetrofitService();
        Call<SurgeryResult> call2 = service.getSurgeryList("S0001");
        call2.enqueue(new Callback<SurgeryResult>() {
            @Override
            public void onResponse(Call<SurgeryResult> call, Response<SurgeryResult> response) {
                if(response.isSuccessful()){
                    dtos = new ArrayList<>();
                    SurgeryResult result = response.body();
                    for(int i=0; i < result.getDto().size(); i++){
                        SurgeryDTO dto = new SurgeryDTO();
                        dto.setSug_idx(result.getDto().get(i).getSug_idx());
                        dto.setSug_name(result.getDto().get(i).getSug_name());
                        dto.setSug_price(result.getDto().get(i).getSug_price());
                        dtos.add(dto);
                    }
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SurgeryResult> call, Throwable t) {
                Toast.makeText(mCon, "통신에러1", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //getMenuList 서버와 통신을 하며 실시간으로 리스트를 갱신시켜준다.
}
