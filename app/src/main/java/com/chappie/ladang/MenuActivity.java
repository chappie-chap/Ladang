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
            for (int i = 0; i < 8; i++) {
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
        IntentActivity(StudyActivity.class);

    }

    @OnClick(R.id.menu_btnSetting)
    void setMenu_btnSetting() {
        IntentActivity(SettingsActivity.class);
    }

    @OnClick(R.id.menu_btnAturan)
    void setMenu_btnAturan() {
        IntentActivity(RuleActivity.class);
    }

    @OnClick(R.id.menu_btnSkor)
    void setMenu_btnSkor() {
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
        IntentActivity(InfoActivity.class);
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
