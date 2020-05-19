package com.chappie.ladang.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chappie.ladang.MenuActivity;
import com.chappie.ladang.R;
import com.chappie.ladang.adapter.DialogSmall;
import com.chappie.ladang.helper.CountDownTimerPausable;
import com.chappie.ladang.model.Level;
import com.chappie.ladang.model.Quest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawingFragment extends Fragment {

    @BindView(R.id.practice_timer)
    TextView tvTimer;
    @BindView(R.id.practice_change)
    TextView change;
    @BindView(R.id.practice_score)
    TextView tvScore;
    @BindView(R.id.practice_question)
    TextView question;
    @BindView(R.id.draw_btnNext)
    ImageButton nextLevel;
    @BindView(R.id.frameDraw)
    FrameLayout frameLayout;
    private List<Level> levels;
    private List<Quest> quests;
    private int changer,score,tempScore =0,position,count=0;
    private long millis;
    private Context context;
    private CountDownTimerPausable countDown;
    private CanvasFragment canvasFragment;
    private FragmentCallBacks fragmentCallBacks;

    public interface FragmentCallBacks{
        void CallBackQuest(List<Level> levels, int changer, int score, long millis);
    }

    public void setFragmentCallBacks(FragmentCallBacks fragmentCallBacks) {
        this.fragmentCallBacks = fragmentCallBacks;
    }

    public DrawingFragment(List<Level> levels, List<Quest> quests, long millis, int changer, int score, int i) {
        this.levels =levels;
        this.quests = quests;
        this.millis = millis;
        this.changer = changer;
        this.score = score;
        this.position = i;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        context= view.getContext();
        canvasFragment = new CanvasFragment();
        createCanvas(canvasFragment);
        tvScore.setText("Skor: "+score);
        question.setText(quests.get(position).getQuestion());
        change.setText("Kesempatan: "+(changer));
        countDown = new CountDownTimerPausable(millis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millis = millisUntilFinished;
                tvTimer.setText(""+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                if(changer==0){
                    DialogSmall dialogSmall = new DialogSmall("Game Over", "Kesempatan kamu habis!");
                    FragmentManager fm = getChildFragmentManager();
                    dialogSmall.show(fm,"Game Ovew");
                    fm.executePendingTransactions();
                    dialogSmall.small_close.setVisibility(View.INVISIBLE);
                    dialogSmall.small_btnOK.setOnClickListener(v ->{
                        Intent intent = new Intent(getActivity(), MenuActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    });
                }else if(changer>0){
                    countDown.pause();
                    changer-=1;
                    change.setText("Kesempatan: " + changer);
                    DialogSmall dialogSmall = new DialogSmall("Waktu Habis", "Kesempatan kamu tersisa "+changer);
                    FragmentManager fm = getChildFragmentManager();
                    dialogSmall.show(fm,"Timer");
                    fm.executePendingTransactions();
                    dialogSmall.small_close.setVisibility(View.INVISIBLE);
                    dialogSmall.small_btnOK.setOnClickListener(v ->{
                        dialogSmall.dismiss();
                        countDown.restart(300000,1000);
                    });
                }
            }
        }.start();
    }

    private void createCanvas(CanvasFragment canvasFragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameDraw, canvasFragment);
        fragmentTransaction.addToBackStack("Canvas");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @OnClick({R.id.drawColor, R.id.drawEmboss, R.id.drawBlur, R.id.drawAtop, R.id.drawErase, R.id.drawSave, R.id.draw_btnNext})
    void onClick(View v){
        switch (v.getId()){
            case R.id.drawColor:
                canvasFragment.pickColor();
                break;
            case R.id.drawEmboss:
                canvasFragment.emboss();
                break;
            case R.id.drawBlur:
                canvasFragment.blurMode();
                break;
            case R.id.drawAtop:
                canvasFragment.scrAtop();
                break;
            case R.id.drawErase:
                canvasFragment.erase();
                break;
            case R.id.drawSave:
                canvasFragment.save();
                break;
            case R.id.draw_btnNext:
                Log.d("Drawing: ","Position: "+position+" "+levels.size());
                if(position<levels.size()-1){
                    tempScore += 10;
                    countDown.pause();
                    score += tempScore;
                    levels.get(position).setCanPlay(false);
                    levels.get(position + 1).setCanPlay(true);
                    levels.get(position).setPlayed(true);
                    fragmentCallBacks.CallBackQuest(levels, changer, score, millis);
                }else {
                    tempScore += 10;
                    countDown.pause();
                    score += tempScore;
                    levels.get(position).setCanPlay(false);
                    levels.get(position).setPlayed(true);
                    DialogSmall dialogSmall = new DialogSmall("Selamat", "kamu lolos disemua tingkat dengan skor "+score);
                    FragmentManager fm = getChildFragmentManager();
                    dialogSmall.show(fm,"Done");
                    fm.executePendingTransactions();
                    dialogSmall.small_close.setVisibility(View.INVISIBLE);
                    dialogSmall.small_btnOK.setOnClickListener(v1 ->{
                        Intent intent = new Intent(getActivity(), MenuActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    });
                }
                break;
        }
    }
}
