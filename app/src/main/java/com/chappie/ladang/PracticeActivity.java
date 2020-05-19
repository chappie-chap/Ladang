package com.chappie.ladang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.chappie.ladang.adapter.DialogSmall;
import com.chappie.ladang.adapter.LevelAdapter;
import com.chappie.ladang.fragment.DrawingFragment;
import com.chappie.ladang.fragment.PuzzleFragment;
import com.chappie.ladang.fragment.QuestionFragment;
import com.chappie.ladang.model.Level;
import com.chappie.ladang.model.Quest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PracticeActivity extends AppCompatActivity {

    private LevelAdapter adapter;
    private List<Level> levels;
    private List<Quest> quests;
    private long millis = 300000;
    private int changer = 3, score = 0;
    @BindView(R.id.rv_level)
    RecyclerView rv_level;
    @BindView(R.id.bg_level)
    ImageView bg_level;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.practice_back)
    ImageButton imgBack;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        ButterKnife.bind(this);

        initLevel();
        initQuestion();
        adapter = new LevelAdapter(PracticeActivity.this, levels);
        rv_level.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_level.setAdapter(adapter);
        adapter.setOnItemClickCallback(i -> {
            switch (levels.get(i).getType()) {
                case "Question":
                    Log.d("Level: ", "Question: " + i);
                    QuestionFragment fragment = new QuestionFragment(levels, quests, millis, changer, score, i);
                    replaceFragment(fragment, "Question");
                    fragment.setFragmentCallBacks((levels1, changer1, score1, millis1) -> {
                        onBackPressed();
                        levels = levels1;
                        changer = changer1;
                        score = score1;
                        millis = millis1;
                        adapter.notifyDataSetChanged();
                    });
                    Log.d("Level: ", "Count: " + getSupportFragmentManager().getBackStackEntryCount());
                    break;
                case "Puzzle":
                    Log.d("Level: ", "Puzzle: " + i);
                    PuzzleFragment puzzleFragment = new PuzzleFragment(levels, quests,millis,changer,score,i);
                    replaceFragment(puzzleFragment, "Puzzle");
                    puzzleFragment.setFragmentCallBacks((levels1, changer1, score1, millis1) -> {
                        onBackPressed();
                        levels = levels1;
                        changer = changer1;
                        score = score1;
                        millis = millis1;
                        adapter.notifyDataSetChanged();
                    });
                    Log.d("Level: ", "Count: " + getSupportFragmentManager().getBackStackEntryCount());
                    break;
                case "Drawing":
                    Log.d("Level: ", "Drawing: " + i);
                    DrawingFragment drawingFragment = new DrawingFragment(levels, quests,millis,changer,score,i);
                    replaceFragment(drawingFragment,"Drawing");
                    drawingFragment.setFragmentCallBacks((levels1, changer1, score1, millis1) -> {
                        onBackPressed();
                        levels = levels1;
                        changer = changer1;
                        score = score1;
                        millis = millis1;
                        adapter.notifyDataSetChanged();
                    });
                    Log.d("Level: ", "Count: " + getSupportFragmentManager().getBackStackEntryCount());
                    break;
            }
        });
    }

    public void replaceFragment(Fragment fragment, String tag) {
        TAG = tag;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void initLevel() {
        Log.d("Practice: ", "Init");
        levels = new ArrayList<>();
        levels.add(new Level(R.drawable.tingkat_1, "Question", true, false));
        levels.add(new Level(R.drawable.tingkat_2, "Question", false, false));
        levels.add(new Level(R.drawable.tingkat_3, "Question", false, false));
        levels.add(new Level(R.drawable.tingkat_4, "Question", false, false));
        levels.add(new Level(R.drawable.tingkat_5, "Puzzle", false, false));
        levels.add(new Level(R.drawable.tingkat_6, "Puzzle", false, false));
        levels.add(new Level(R.drawable.tingkat_7, "Puzzle", false, false));
        levels.add(new Level(R.drawable.tingkat_8, "Puzzle", false, false));
        levels.add(new Level(R.drawable.tingkat_9, "Drawing", false, false));
        levels.add(new Level(R.drawable.tingkat_10, "Drawing", false, false));
    }

    private void initQuestion() {
        quests = new ArrayList<>();
        quests.add(new Quest("Media apa saja yang digunakan untuk membuat gambar sketsa ? (+4) (kalau benar semua)", R.drawable.kertas, true, R.drawable.pensil, true, R.drawable.rapido, true, R.drawable.tipex, false, R.drawable.printer, false));
        quests.add(new Quest("Media apa saja yang digunakan untuk membuat gambar ilustrasi manual ? (+4) (kalau benar semua)", R.drawable.drawing_pen, true, R.drawable.kamera, false, R.drawable.komputer, false, R.drawable.penggaris, true, R.drawable.pensil, true));
        quests.add(new Quest("Media apa saja yang digunakan untuk membuat gambar ilustrasi digital ? (+4) (kalau benar semua)", R.drawable.drawing_pad, true, R.drawable.fd, false, R.drawable.komputer, true, R.drawable.usb, false, R.drawable.scan, true));
        quests.add(new Quest("Media apa saja yang digunakan untuk membuat gambar ilustrasi digital ? (+4) (kalau benar semua)", R.drawable.acrylic, true, R.drawable.water_color, true, R.drawable.lem, false, R.drawable.pensil, true, R.drawable.scan, true));
        quests.add(new Quest("Susunlah gambar dibawah ini sehingga terlihat seperti gambar sketsa (+10)", R.drawable.puzzle1));
        quests.add(new Quest("Susunlah gambar dibawah ini sehingga terlihat seperti gambar sketsa (+10)", R.drawable.puzzle2));
        quests.add(new Quest("Susunlah gambar dibawah ini sehingga terlihat seperti gambar sketsa (+10)", R.drawable.puzzle3));
        quests.add(new Quest("Susunlah gambar dibawah ini sehingga terlihat seperti gambar sketsa (+10)", R.drawable.puzzle4));
        quests.add(new Quest("Buatlah sebuah gambar Ilustrasi (+10)"));
        quests.add(new Quest("Buatlah sebuah gambar perspektif (+10)"));
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        Log.d("Level: ", "Count: " + count);
        if (count > 0) {
            getSupportFragmentManager().popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            DialogSmall small = new DialogSmall("Keluar", "Yakin inngin kembali ke halaman menu ?");
            FragmentManager fm = getSupportFragmentManager();
            small.show(fm, "Exit");
            fm.executePendingTransactions();
            small.small_btnOK.setOnClickListener(v -> {
                Intent intent = new Intent(PracticeActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

}
