package com.chappie.ladang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.chappie.ladang.adapter.DialogsPlay;
import com.chappie.ladang.helper.StorePreference;
import com.chappie.ladang.model.Game;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity implements Serializable {

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
    private List<Game> gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        gameList = new ArrayList<>();
        StorePreference pref = new StorePreference(getApplicationContext());
        String player;
        int point;

        if (!pref.getBooleanValue("isPlayerNull")) {
            Log.d("Menu: ",""+pref.getStringValue("Player"+1));
            for (int i = 1; i <= 8; i++) {
                if (pref.getStringValue("Player"+i) != null) {
                    Log.d("Menu: ","Add pref");
                    player = pref.getStringValue("Player"+i);
                    point = pref.getIntValue("Point"+i);
                    gameList.add(new Game(player, "Penduduk", "^^", false, false, point));
                } else {
                    break;
                }
            }
        }


    }

    @OnClick(R.id.menu_btnClose)
    void setMenu_btnClose() {
        onBackPressed();
    }

    @OnClick(R.id.menu_btnPlay)
    void setMenu_btnPlay() {
        new DialogsPlay().show(getSupportFragmentManager(), "Game");
    }

    @OnClick(R.id.menu_btnStudy)
    void setMenu_btnStudy() {
        Toast.makeText(getApplicationContext(), "Button Study Clicked", Toast.LENGTH_SHORT).show();
        IntentActivity(StudyActivity.class);

    }

    @OnClick(R.id.menu_btnSetting)
    void setMenu_btnSetting() {
        Toast.makeText(getApplicationContext(), "Button Setting Clicked", Toast.LENGTH_SHORT).show();
        IntentActivity(SettingsActivity.class);
    }

    @OnClick(R.id.menu_btnAturan)
    void setMenu_btnAturan() {
        Toast.makeText(getApplicationContext(), "Button Aturan Clicked", Toast.LENGTH_SHORT).show();
        IntentActivity(RuleActivity.class);
    }

    @OnClick(R.id.menu_btnSkor)
    void setMenu_btnSkor() {
        Toast.makeText(getApplicationContext(), "Button Skor Clicked", Toast.LENGTH_SHORT).show();
        if(gameList.isEmpty()) {
            Log.d("Menu: ","gameList Null");
            IntentActivity(ScoreActivity.class);
        }else{
            Gson gson = new Gson();
            String JsonGame = gson.toJson(gameList);
            Intent intent = new Intent(MenuActivity.this,ScoreActivity.class);
            intent.putExtra(ScoreActivity.EXTRA_SCORE, JsonGame);
            startActivity(intent);
        }
    }

    @OnClick(R.id.menu_btnInfo)
    void setMenu_btnInfo() {
        Toast.makeText(getApplicationContext(), "Button Info Clicked", Toast.LENGTH_SHORT).show();
    }

    private void IntentActivity(Class cls) {
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
