package com.egg8;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.egg8.adapter.recyclerview.SurgeryAdapter;
import com.egg8.common.manager.SharedPreferenceManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomFragment extends BottomSheetDialogFragment {
    private RecyclerView RecyclerView_surgery;
    private SurgeryAdapter surgeryAdapter;
    private Button btn_app_ok;
    private TextView tv_result;
    Context mCon;
    RadioButton cb_surgery;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCon = view.getContext();
        RecyclerView_surgery = (RecyclerView)view.findViewById(R.id.RecyclerView_surgery);
        btn_app_ok=view.findViewById(R.id.btn_app_ok);
        tv_result=view.findViewById(R.id.tv_result);
        SurgeryAppointment(mCon);

        int s= SharedPreferenceManager.getInt(view.getContext(),"chk");
        if (s==1){
            btn_app_ok.setEnabled(true);

        }else {
            Log.d("msg","섹스");
        }


        btn_app_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_result.setText(SharedPreferenceManager.getString(v.getContext(),"tmp"));
                String a = SharedPreferenceManager.getString(v.getContext(),"tmp");
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
        list1.add("기본케어관리");
        list1.add("고급케어관리");
        list1.add("여드름관리1");
        list1.add("여드름관리2");
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        RecyclerView_surgery.setLayoutManager(layoutManager);
        surgeryAdapter=new SurgeryAdapter(list1);
        RecyclerView_surgery.setAdapter(surgeryAdapter);
        RecyclerView.Adapter a =RecyclerView_surgery.getAdapter();
        cb_surgery = RecyclerView_surgery.findViewById(R.id.cb_surgery);
    }
    public void dialg(){
        String msg = "테스트입니다.\n안녕하세요";
        AlertDialog.Builder dlg = new AlertDialog.Builder(mCon);
        dlg.setTitle("예약내역확인"); //제목


        dlg.setMessage(msg); // 메시지
//                버튼 클릭시 동작
        dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //토스트 메시지
                Toast.makeText(mCon,"확인을 눌르셨습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        dlg.show();
    }
}

