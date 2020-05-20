package com.chappie.ladang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chappie.ladang.adapter.ProgressAdapter;
import com.chappie.ladang.helper.StorePreference;
import com.chappie.ladang.model.Game;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoreActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "score";
    private List<Game> gameList;
    private ProgressAdapter adapter;
    private StorePreference pref;
    private TextView tvempty;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        recyclerView = findViewById(R.id.rv_score);
        tvempty= findViewById(R.id.tvEmpty);
        pref = new StorePreference(getApplicationContext());

        if(getIntent().getStringExtra(EXTRA_SCORE)!=null){
            tvempty.setVisibility(View.INVISIBLE);
            String jsonGame = getIntent().getStringExtra(EXTRA_SCORE);
            Log.d("Score: ", ""+jsonGame);
            Gson gson = new Gson();
            Type type = new TypeToken<List<Game>>(){}.getType();
            gameList = gson.fromJson(jsonGame, type);
            if (gameList != null) {
                for (Game game : gameList){
                    Log.d("Score: ", ""+game.getName());
                    break;
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
            adapter = new ProgressAdapter(getApplicationContext(),gameList);
            recyclerView.setAdapter(adapter);
        }else{
            tvempty.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.score_btnOK,R.id.score_btnDelete})
    void wasClick(View v){
        switch (v.getId()){
            case R.id.score_btnOK:
                onBackPressed();
                break;
            case R.id.score_btnDelete:
                boolean first = pref.getBooleanValue("HasPlayed");
                boolean sound = pref.getBooleanValue("SwitchSound");
                pref.clearEditor();
                if(first){
                    pref.setBoolean("FirstPlay", true);
                }else if(sound){
                    pref.setBoolean("SwitchSound",true);
                }
                gameList.clear();
                adapter.notifyDataSetChanged();
                tvempty.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ScoreActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
