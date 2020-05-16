package com.chappie.ladang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chappie.ladang.adapter.ViewPagerAdapter;
import com.chappie.ladang.model.Intro;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IntroActivity extends AppCompatActivity {
    @BindView(R.id.intro_tvNext)
    TextView intro_tvNext;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        ViewPager mImageViewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mImageViewPager, true);

        List<Intro> introList = new ArrayList<>();
        introList.add(new Intro(R.drawable.bg_aturan, getResources().getString(R.string.intro1)));
        introList.add(new Intro(R.drawable.bg_aturan, getResources().getString(R.string.intro2)));

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(introList, this);
        mImageViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mImageViewPager);

        mImageViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getApplicationContext(), position+"",Toast.LENGTH_SHORT).show();
                if(position==1){
                    intro_tvNext.setText(R.string.lanjutkan);
                }else intro_tvNext.setText(R.string.skip);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        sharedPreferences = getSharedPreferences("Game_Ladang",0);
        /*Boolean check = sharedPreferences.getBoolean("isOpenBefore", false );
        if(check){

        }*/
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isOpenedBefore",true);
        editor.apply();
    }


    public void setIntro_tvNext(View view) {
        Intent intent = new Intent(this,MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
