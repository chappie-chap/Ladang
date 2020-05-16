package com.chappie.ladang.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.chappie.ladang.GameActivity;
import com.chappie.ladang.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogsPlay extends DialogFragment {

    @BindView(R.id.sPlayer_bg)
    ImageView sPlayer_bg;
    @BindView(R.id.sPlayer_player)
    TextView sPlayer_player;
    @BindView(R.id.sPlayer_seekBar)
    SeekBar sPlayer_seekBar;
    @BindView(R.id.sPlayer_Penduduk)
    TextView sPlayer_penduduk;
    @BindView(R.id.sPlayer_Pendatang)
    TextView sPlayer_pendatang;
    @BindView(R.id.sPlayer_Raja)
    TextView sPlayer_Raja;
    @BindView(R.id.sPlayer_Mulai)
    ImageButton sPlayer_mulai;
    @BindView(R.id.sPlayer_Aturan)
    ImageButton sPlayer_aturan;
    private int penduduk, pendatang, raja;

    public DialogsPlay() {
    }

    private int getPenduduk() {
        return penduduk;
    }

    private void setPenduduk(int penduduk) {
        this.penduduk = penduduk;
    }

    private int getPendatang() {
        return pendatang;
    }

    private void setPendatang(int pendatang) {
        this.pendatang = pendatang;
    }

    private int getRaja() {
        return raja;
    }

    private void setRaja(int raja) {
        this.raja = raja;
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_set_player, container, false);
        ButterKnife.bind(this, view);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        Glide.with(view.getContext())
                .load(R.drawable.border)
                .into(sPlayer_bg);
        Glide.with(view.getContext())
                .load(R.drawable.mulai)
                .into(sPlayer_mulai);
        Glide.with(view.getContext())
                .load(R.drawable.ic_aturan)
                .into(sPlayer_aturan);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sPlayer_seekBar.setMin(3);
        }
        sPlayer_seekBar.setMax(8);
        sPlayer_seekBar.setProgress(3);
        setPenduduk(2);
        setPendatang(1);
        setRaja(0);
        sPlayer_player.setText(String.format("%s %d",getResources().getString(R.string.player),sPlayer_seekBar.getProgress()));
        roleBtn();
        sPlayer_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged =3;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged =progress;
                if(progress<3){
                    Toast.makeText(view.getContext(),getResources().getString(R.string.player_minimum),Toast.LENGTH_SHORT).show();
                }else {
                    sPlayer_player.setText(String.format("%s %d",getResources().getString(R.string.player),progress));
                    ruleGame(progressChanged);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    private void ruleGame(int progressChanged) {
        if (progressChanged == 3 || progressChanged == 4) {
            if (progressChanged == 3) {
                setPenduduk(2);
                setPendatang(1);
                setRaja(0);
                roleBtn();
            } else {
                setPenduduk(3);
                setPendatang(1);
                setRaja(0);
                roleBtn();
            }
        } else if (progressChanged == 5 || progressChanged == 6) {
            if (progressChanged == 5) {
                setPenduduk(3);
                setPendatang(1);
                setRaja(1);
                roleBtn();
            } else {
                setPenduduk(4);
                setPendatang(1);
                setRaja(1);
                roleBtn();
            }
        } else if (progressChanged == 7 || progressChanged == 8) {
            if (progressChanged == 7) {
                setPenduduk(4);
                setPendatang(2);
                setRaja(1);
                roleBtn();
            } else {
                setPenduduk(5);
                setPendatang(2);
                setRaja(1);
                roleBtn();
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private void roleBtn() {
        sPlayer_penduduk.setText(String.format("%s %d",getResources().getString(R.string.penduduk),getPenduduk()));
        sPlayer_pendatang.setText(String.format("%s %d",getResources().getString(R.string.pendatang),getPendatang()));
        sPlayer_Raja.setText(String.format("%s %d",getResources().getString(R.string.raja),getRaja()));
    }

    @OnClick(R.id.sPlayer_Mulai)
    void Start() {
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra(GameActivity.EXTRA_PENDUDUK, getPenduduk());
        intent.putExtra(GameActivity.EXTRA_PeNDATANG, getPendatang());
        intent.putExtra(GameActivity.EXTRA_RAJA, getRaja());
        Objects.requireNonNull(getActivity()).startActivity(intent);
        dismiss();
    }

    @OnClick(R.id.sPlayer_Aturan)
    void setsPlayerAturan(){
        Toast.makeText(getContext(),"Aturan Clicked", Toast.LENGTH_SHORT).show();
    }
}
