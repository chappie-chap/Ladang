package com.chappie.ladang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.chappie.ladang.helper.StorePreference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StorePreference pref = new StorePreference(getApplicationContext());
        if(!pref.getBooleanValue("HasPlayed")){
            pref.setBoolean("HasPlayed", true);
            pref.setBoolean("SwitchSound",true);
            ImageView splash = findViewById(R.id.img_anim);
            splash.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_splash));
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.start();
        }else{
            ImageView splash = findViewById(R.id.img_anim);
            splash.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_splash));
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.start();
        }
    }
}
