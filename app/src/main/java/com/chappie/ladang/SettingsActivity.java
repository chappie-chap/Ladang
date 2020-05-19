package com.chappie.ladang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import com.chappie.ladang.helper.StorePreference;

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
        }else {
            Log.d("Settings: ",""+isChecked);
        }
    }

}
