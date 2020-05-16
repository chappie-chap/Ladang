package com.chappie.ladang.adapter;

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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.chappie.ladang.GameActivity;
import com.chappie.ladang.R;
import com.chappie.ladang.model.Game;
import com.chappie.ladang.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogSmall extends DialogFragment {
    @BindView(R.id.small_bg)
    ImageView small_bg;
    @BindView(R.id.small_title)
    TextView small_title;
    @BindView(R.id.small_message)
    TextView small_message;
    @BindView(R.id.small_btnOK)
    public ImageButton small_btnOK;
    @BindView(R.id.small_close)
    public ImageButton small_close;
    private String title,message;

    public DialogSmall(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_small,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        Glide.with(view.getContext()).load(R.drawable.border).into(small_bg);
        Glide.with(view.getContext()).load(R.drawable.close).into(small_close);
        Glide.with(view.getContext()).load(R.drawable.ok).into(small_btnOK);
        small_title.setText(title);
        small_message.setText(message);
    }

    @OnClick(R.id.small_close)
    void Close(){
        Objects.requireNonNull(getDialog()).cancel();
    }
}