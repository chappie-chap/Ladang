package com.chappie.ladang.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chappie.ladang.MenuActivity;
import com.chappie.ladang.R;
import com.chappie.ladang.adapter.DialogSmall;
import com.chappie.ladang.helper.CountDownTimerPausable;
import com.chappie.ladang.model.Level;
import com.chappie.ladang.model.Quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    @BindView(R.id.practice_timer)
    TextView tvTimer;
    @BindView(R.id.practice_change)
    TextView change;
    @BindView(R.id.practice_score)
    TextView tvScore;
    @BindView(R.id.practice_question)
    TextView question;
    @BindView(R.id.bg_answer)
    ImageView bg_answer;
    @BindView(R.id.imgCheck1)
    ImageView imgCheck1;
    @BindView(R.id.imgCheck2)
    ImageView imgCheck2;
    @BindView(R.id.imgCheck3)
    ImageView imgCheck3;
    @BindView(R.id.imgCheck4)
    ImageView imgCheck4;
    @BindView(R.id.imgCheck5)
    ImageView imgCheck5;
    @BindView(R.id.answer1)
    ImageButton answer1;
    @BindView(R.id.answer2)
    ImageButton answer2;
    @BindView(R.id.answer3)
    ImageButton answer3;
    @BindView(R.id.answer4)
    ImageButton answer4;
    @BindView(R.id.answer5)
    ImageButton answer5;
    @BindView(R.id.quest_btnNext)
    ImageButton nextLevel;
    private List<Level> levels;
    private List<Quest> quests;
    private List<List<Object>> arrayLists = new ArrayList<>();
    private List<ImageButton> list1 = new ArrayList<>();
    private int changer,score,tempScore =0,position,count=0;
    private long millis;
    private Context context;
    private CountDownTimerPausable countDown;
    private FragmentCallBacks fragmentCallBacks;

    public interface FragmentCallBacks{
        void CallBackQuest(List<Level> levels, int changer, int score, long millis);
    }

    public void setFragmentCallBacks(FragmentCallBacks fragmentCallBacks) {
        this.fragmentCallBacks = fragmentCallBacks;
    }

    public QuestionFragment() {
        // Required empty public constructor
    }

    public QuestionFragment(List<Level> levels, List<Quest> quests, long millis, int changer, int score, int position){
        this.levels =levels;
        this.quests = quests;
        this.millis = millis;
        this.changer = changer;
        this.score = score;
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        context= view.getContext();
        imgCheck1.setVisibility(View.INVISIBLE);
        imgCheck2.setVisibility(View.INVISIBLE);
        imgCheck3.setVisibility(View.INVISIBLE);
        imgCheck4.setVisibility(View.INVISIBLE);
        imgCheck5.setVisibility(View.INVISIBLE);
        list1.add(answer1);
        list1.add(answer2);
        list1.add(answer3);
        list1.add(answer4);
        list1.add(answer5);
        createQuest(position);
        tvScore.setText("Skor: "+score);
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
                    dialogSmall.small_btnOK.setOnClickListener(v ->{
                        dialogSmall.dismiss();
                        countDown.restart(300000,1000);
                    });
                }
            }
        }.start();
    }

    @SuppressLint("SetTextI18n")
    private void createQuest(int i){
        change.setText("Kesempatan: "+(changer));
        question.setText(quests.get(i).getQuestion());
        arrayLists.clear();
        arrayLists.add(getList(quests.get(i).getImgAnswer1(),quests.get(i).isCorrect1()));
        arrayLists.add(getList(quests.get(i).getImgAnswer2(),quests.get(i).isCorrect2()));
        arrayLists.add(getList(quests.get(i).getImgAnswer3(),quests.get(i).isCorrect3()));
        arrayLists.add(getList(quests.get(i).getImgAnswer4(),quests.get(i).isCorrect4()));
        arrayLists.add(getList(quests.get(i).getImgAnswer5(),quests.get(i).isCorrect5()));
        Collections.shuffle(arrayLists);
        int x=0;
        for (List<Object> list : arrayLists){
            Log.d("Question: ","Image: "+ list.get(0)+" Boolean: "+list.get(1));
            Glide.with(context).load(list.get(0)).into(list1.get(x));
            list1.get(x).setContentDescription(""+x);
            x++;
        }
    }

    @SafeVarargs
    private final <T> List<T> getList(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    @OnClick({R.id.answer1,R.id.answer2,R.id.answer3,R.id.answer4,R.id.answer5})
    void Answer(View view){
        switch (view.getId()){
            case R.id.answer1:
                if(imgCheck1.getVisibility()==View.INVISIBLE){
                    checking(answer1);
                    imgCheck1.setVisibility(View.VISIBLE);
                    count++;
                }else if(imgCheck1.getVisibility()==View.VISIBLE){
                    tempScore-=2;
                    imgCheck1.setVisibility(View.INVISIBLE);
                    count--;
                }
                break;
            case R.id.answer2:
                if(imgCheck2.getVisibility()==View.INVISIBLE){
                    checking(answer2);
                    imgCheck2.setVisibility(View.VISIBLE);
                    count++;
                }else if(imgCheck2.getVisibility()==View.VISIBLE){
                    tempScore-=2;
                    imgCheck2.setVisibility(View.INVISIBLE);
                    count--;
                }
                break;
            case R.id.answer3:
                if(imgCheck3.getVisibility()==View.INVISIBLE){
                    checking(answer3);
                    imgCheck3.setVisibility(View.VISIBLE);
                    count++;
                }else if(imgCheck3.getVisibility()==View.VISIBLE){
                    tempScore-=2;
                    imgCheck3.setVisibility(View.INVISIBLE);
                    count--;
                }
                break;
            case R.id.answer4:
                if(imgCheck4.getVisibility()==View.INVISIBLE){
                    checking(answer4);
                    imgCheck4.setVisibility(View.VISIBLE);
                    count++;
                }else if(imgCheck4.getVisibility()==View.VISIBLE){
                    tempScore-=2;
                    imgCheck4.setVisibility(View.INVISIBLE);
                    count--;
                }
                break;
            case R.id.answer5:
                if(imgCheck5.getVisibility()==View.INVISIBLE){
                    checking(answer5);
                    imgCheck5.setVisibility(View.VISIBLE);
                    count++;
                }else if(imgCheck5.getVisibility()==View.VISIBLE){
                    tempScore-=2;
                    imgCheck5.setVisibility(View.INVISIBLE);
                    count--;
                }
                break;
        }
    }

    private void checking(ImageButton answer) {
        Log.d("Checking: ", "ContentDescription: "+answer.getContentDescription());
        int i=0;
        for(List<Object> list : arrayLists){
            if (answer.getContentDescription().equals(""+i)&&list.get(1).equals(true)){
                Log.d("Checking: ", ""+i+" isCorrect"+list.get(1));
                tempScore+=2;
                break;
            }else if (answer.getContentDescription().equals(""+i)&&list.get(1).equals(true)){
                Log.d("Checking: ", ""+i+" isCorrect"+list.get(1));
                tempScore+=2;
                break;
            }else if (answer.getContentDescription().equals(""+i)&&list.get(1).equals(true)){
                Log.d("Checking: ", ""+i+" isCorrect"+list.get(1));
                tempScore+=2;
                break;
            }else if (answer.getContentDescription().equals(""+i)&&list.get(1).equals(true)){
                Log.d("Checking: ", ""+i+" isCorrect"+list.get(1));
                tempScore+=2;
                break;
            }else if (answer.getContentDescription().equals(""+i)&&list.get(1).equals(true)){
                Log.d("Checking: ", ""+i+" isCorrect"+list.get(1));
                tempScore+=2;
                break;
            }
            i++;
        }
    }

    @OnClick(R.id.quest_btnNext)
    void NextLevel(){
        Log.d("Question: ", "NextLevel: "+count);
        if (position == levels.size() - 1 && !levels.get(position).isCanPlay()) {
            DialogSmall dialogSmall = new DialogSmall("Selesai!", "Skor keseluruhan kamu " + score);
            dialogSmall.show(getChildFragmentManager(), "Timer");
        } else if (levels.get(position).isCanPlay()&& count!=0) {
            if (tempScore == 6) {
                tempScore += 4;
            }else if(tempScore==8){
                tempScore+=2;
            }
            countDown.pause();
            score += tempScore;
            levels.get(position).setCanPlay(false);
            levels.get(position + 1).setCanPlay(true);
            levels.get(position).setPlayed(true);
            if (fragmentCallBacks != null) {
                fragmentCallBacks.CallBackQuest(levels, changer, score, millis);
            }
        }else{
            if (fragmentCallBacks != null) {
                fragmentCallBacks.CallBackQuest(levels, changer, score, millis);
            }
        }
    }
}
