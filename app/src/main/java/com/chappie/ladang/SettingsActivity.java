package com.chappie.ladang;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import com.chappie.ladang.helper.StorePreference;
import com.chappie.ladang.service.ServiceSound;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.sw_settings)
    Switch sw_settings;
    private StorePreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        pref = new StorePreference(getApplicationContext());
        if(pref.getBooleanValue("SwitchSound")){
            sw_settings.setChecked(true);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }
    }

    @OnClick(R.id.delete_settings)
    void delete(){
        boolean first = pref.getBooleanValue("HasPlayed");
        pref.clearEditor();
        if(first){
            pref.setBoolean("FirstPlay", true);
        }
        pref.setBoolean("SwitchSound",true);
    }

    @OnCheckedChanged(R.id.sw_settings)
    void switchSetting(Switch sw_settings, boolean isChecked){
        if(isChecked){
            Log.d("Settings: ",""+isChecked);
            if (!isPlay()) {
                startService(new Intent(getApplicationContext(), ServiceSound.class));
            }
        }else {
            if (isPlay()) {
                stopService(new Intent(getApplicationContext(), ServiceSound.class));
            }
            Log.d("Settings: ",""+isChecked);
        }
    }

    private boolean isPlay() {
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ServiceSound.class.getName().equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
