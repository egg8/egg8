package com.egg8.common.function;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.egg8.R;
import com.egg8.common.manager.ToastManager;
import com.egg8.ui.user.register.Register_SMS;

public class PermissionCheck extends Activity {

    private final int MY_PERMISSIONS_REQUEST_SEND_SMS=1001;
    private Context mCon;
    private Activity mAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCon = this;

    }
    public void RequestPermission(Context mCon,Activity mAc) {

        int permssionCheck = ContextCompat.checkSelfPermission(mCon,Manifest.permission.SEND_SMS);

        // PERMISSION_GRANTED -> 권한이 있으면
        if (permssionCheck != PackageManager.PERMISSION_GRANTED) {

           Toast.makeText(mCon,"권한승인이 필요합니다.",Toast.LENGTH_LONG).show();

           // shouldShowRequestPermissionRationale => 거부 : true : false 승인
            if (ActivityCompat.shouldShowRequestPermissionRationale(mAc,Manifest.permission.SEND_SMS))
            {

               ActivityCompat.requestPermissions(mAc,new String[]{Manifest.permission.SEND_SMS},
                       MY_PERMISSIONS_REQUEST_SEND_SMS);
                Toast.makeText(mCon,"SMS인증 부분 사용을 위해 SMS 권한이 필요합니다.",Toast.LENGTH_LONG).show();

            }
            else
            {
                ActivityCompat.requestPermissions(mAc,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
                Toast.makeText(mCon,"SMS인증부분 사용을 위해 SMS 권한이 필요합니다.",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(mCon,"승인이 허가되어 있습니다.",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(mCon,"아직 승인받지 않았습니다.",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}