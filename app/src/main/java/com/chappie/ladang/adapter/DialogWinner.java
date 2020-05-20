package com.chappie.ladang.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.chappie.ladang.R;
import com.chappie.ladang.model.Game;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogWinner extends DialogFragment {
    @BindView(R.id.winner_bg)
    ImageView winner_bg;
    @BindView(R.id.winner_tvRole)
    TextView winner_tvRole;
    @BindView(R.id.winner_tvsWord1)
    TextView winner_tvsWord1;
    @BindView(R.id.winner_tvsWord2)
    TextView winner_tvsWord2;
    @BindView(R.id.winner_btnReplay)
    public ImageButton winner_btnReplay;
    @BindView(R.id.winner_btnPraktik)
    public ImageButton winner_btnPraktik;
    private ArrayList<Game> gameList;
    private int index;
    private OnItemClickCallback onItemClickCallback;


    public interface OnItemClickCallback{
        void onItemClicked(ArrayList<Game> list, boolean isReplay);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback =onItemClickCallback;
    }

    public DialogWinner(ArrayList<Game> gameList, int index) {
        this.gameList = gameList;
        this.index = index;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_winnerr,container,false);
        ButterKnife.bind(this, view);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Glide.with(view.getContext()).load(R.drawable.border).into(winner_bg);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        Glide.with(view.getContext()).load(R.drawable.checklis).into(winner_btnReplay);
        Glide.with(view.getContext()).load(R.drawable.praktik).into(winner_btnPraktik);
        String sWord1= null, sWord2=null,role=null;
        for (int i=0; i<gameList.size(); i++){
            if(gameList.get(i).getRole().equals("Penduduk")){
                sWord1 = gameList.get(i).getSecretWord();
            }else if(gameList.get(i).getRole().equals("Pendatang")){
                sWord2 = gameList.get(i).getSecretWord();
            }
        }
        if (index==1){
            role = "Penduduk Menang!";
        }else if(index==2){
            role = "Pendatang Menang!";
        }else if(index==3){
            role = "Raja Menang!";
        }else if(index==4){
            role = "Penduduk Kalah!";
        }
        winner_tvRole.setText(role);
        winner_tvsWord1.setText("Kata Penduduk: "+sWord1);
        winner_tvsWord2.setText("Kata Pendatang: "+sWord2);
        for (int i=0; i<gameList.size(); i++){
            if(!gameList.get(i).getRole().equals("Penduduk") || index!=1){
                if(!gameList.get(i).getRole().equals("Pendatang")|| !(index ==2 || index ==4)){
                    if (gameList.get(i).getRole().equals("Raja") && ((index ==3 || index ==4) && !gameList.get(i).isEliminate())){
                        gameList.get(i).setPoint(gameList.get(i).getPoint()+30);
                    }
                }else if(!gameList.get(i).isEliminate()){
                    gameList.get(i).setPoint(gameList.get(i).getPoint()+25);
                }
            }else if(!gameList.get(i).isEliminate()){
                gameList.get(i).setPoint(gameList.get(i).getPoint()+25);
            }
        }
        winner_btnReplay.setOnClickListener(v-> onItemClickCallback.onItemClicked(gameList,true));
        winner_btnPraktik.setOnClickListener(v-> onItemClickCallback.onItemClicked(gameList,false));

        return view;
    }
}
