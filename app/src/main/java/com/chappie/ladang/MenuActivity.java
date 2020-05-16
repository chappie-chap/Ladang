package com.chappie.ladang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.chappie.ladang.adapter.DialogsPlay;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.menu_bg)
    ImageView menu_bg;
    @BindView(R.id.menu_btnClose)
    ImageButton menu_btnClose;
    @BindView(R.id.menu_btnPlay)
    ImageButton menu_btnPlay;
    @BindView(R.id.menu_btnStudy)
    ImageButton menu_btnStudy;
    @BindView(R.id.menu_btnSetting)
    ImageButton menu_btnSetting;
    @BindView(R.id.menu_btnAturan)
    ImageButton menu_btnAturan;
    @BindView(R.id.menu_btnSkor)
    ImageButton menu_btnSkor;
    @BindView(R.id.menu_btnInfo)
    ImageButton menu_btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.menu_btnClose)
    void setMenu_btnClose(){
        Toast.makeText(getApplicationContext(), "Button Close Clicked",Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @OnClick(R.id.menu_btnPlay)
    void setMenu_btnPlay(){
        Toast.makeText(getApplicationContext(), "Button Play Clicked",Toast.LENGTH_SHORT).show();
        new DialogsPlay().show(getSupportFragmentManager(), "Game");
    }

    @OnClick(R.id.menu_btnStudy)
    void setMenu_btnStudy(){
        Toast.makeText(getApplicationContext(), "Button Study Clicked",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.menu_btnSetting)
    void setMenu_btnSetting(){
        Toast.makeText(getApplicationContext(), "Button Setting Clicked",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.menu_btnAturan)
    void setMenu_btnAturan(){
        Toast.makeText(getApplicationContext(), "Button Aturan Clicked",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.menu_btnSkor)
    void setMenu_btnSkor(){
        Toast.makeText(getApplicationContext(), "Button Skor Clicked",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.menu_btnInfo)
    void setMenu_btnInfo(){
        Toast.makeText(getApplicationContext(), "Button Info Clicked",Toast.LENGTH_SHORT).show();
    }

    private void IntentActivity(Class cls){
        Intent intent = new Intent(MenuActivity.this, cls);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle(getString(R.string.exit_app))
                .setMessage(getString(R.string.message_exit))
                .setNegativeButton(getString(R.string.batal), null)
                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    MenuActivity.this.finishAffinity();
                }).create().show();
    }
}
