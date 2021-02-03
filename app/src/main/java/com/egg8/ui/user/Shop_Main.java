package com.egg8.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.egg8.R;
import com.egg8.common.manager.ToastManager;
import com.egg8.ui.user.common.FragSetting;
import com.egg8.ui.user.common.FragShopList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Shop_Main extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragShopList fragShopList = new FragShopList();
    private FragSetting fragSetting = new FragSetting();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottomnavi);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayouy,fragShopList).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId())
            {
                case R.id.menu_list :
                    transaction.replace(R.id.framelayouy,fragShopList).commitAllowingStateLoss();
                    break;
                case R.id.menu_setting :
                    transaction.replace(R.id.framelayouy,fragSetting).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}