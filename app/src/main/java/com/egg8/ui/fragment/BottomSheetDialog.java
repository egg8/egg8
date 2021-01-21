package com.egg8.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.egg8.R;
import com.egg8.adapter.recyclerview.OnItemClickListener;
import com.egg8.adapter.recyclerview.SurgeryAdapter;
import com.egg8.common.function.MakeDialogMsg;
import com.egg8.common.manager.SharedPreferenceManager;
import com.egg8.ui.calendar.CalendarActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private RecyclerView RecyclerView_surgery;
    private SurgeryAdapter surgeryAdapter;
    private Button btn_app_ok;
    Context mCon;
    String chk;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCon = view.getContext();
        RecyclerView_surgery = (RecyclerView)view.findViewById(R.id.RecyclerView_surgery);
        btn_app_ok=view.findViewById(R.id.btn_app_ok);
        SurgeryAppointment(mCon);

        btn_app_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialg();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= LayoutInflater.from(inflater.getContext()).inflate(R.layout.recyclerview_surgery,container,false);
        return view;

    }
    public void SurgeryAppointment (Context context) {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("기본 상담");
        list1.add("기본 케어");
        list1.add("여드름 관리");
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        RecyclerView_surgery.setLayoutManager(layoutManager);
        surgeryAdapter = new SurgeryAdapter(list1);
        RecyclerView_surgery.setAdapter(surgeryAdapter);
        surgeryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                RadioButton cb_surgery = v.findViewById(R.id.cb_surgery);
                chk=cb_surgery.getText().toString();
                btn_app_ok.setEnabled(true);
            }
        });
    }
    public void dialg(){
        String msg = MakeDialogMsg.MakeMsg("린다뷰티",SharedPreferenceManager.getString(mCon,"tmp_v_date"),SharedPreferenceManager.getString(mCon,"tmp_time"),chk);
        AlertDialog.Builder dlg = new AlertDialog.Builder(mCon);
        dlg.setTitle("안내"); //제목
        dlg.setMessage(msg); // 메시지
        dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                String supp_code = "S0001";
                String user_code = "U00002";
                String res_in_date = SharedPreferenceManager.getString(mCon,"tmp_date");
                String res_in_name = chk;
                String res_in_str_time = SharedPreferenceManager.getString(mCon,"tmp_time");
                String res_in_end_time = SharedPreferenceManager.getString(mCon,"tmp_time");

            }
        });
        dlg.show();
    }
}

