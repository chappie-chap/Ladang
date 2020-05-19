package com.chappie.ladang;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

import butterknife.OnClick;

public class ScoreActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "score";
    private List<Game> gameList;
    ProgressAdapter adapter;
    private StorePreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        RecyclerView recyclerView = findViewById(R.id.rv_score);
        pref = new StorePreference(getApplicationContext());

        if(getIntent().getStringExtra(EXTRA_SCORE)!=null){
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
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        adapter = new ProgressAdapter(getApplicationContext(),gameList);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.btnOK,R.id.btnDelete})
    void Click(View v){
        switch (v.getId()){
            case R.id.btnOK:
                onBackPressed();
                break;
            case R.id.btnDelete:
                boolean first = pref.getBooleanValue("HasPlayed");
                pref.clearEditor();
                if(first){
                    pref.setBoolean("FirstPlay", true);
                }
                pref.setBoolean("SwitchSound",true);
                gameList=null;
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
