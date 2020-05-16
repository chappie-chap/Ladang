package com.chappie.ladang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.chappie.ladang.R;
import com.chappie.ladang.model.Game;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogGetWord extends DialogFragment {
    @BindView(R.id.getWord_bg)
    ImageView getWord_bg;
    @BindView(R.id.getWord_img)
    ImageView imgAva;
    @BindView(R.id.getWord_edtName)
    EditText edtName;
    @BindView(R.id.imageView)
    ImageView bg_Text;
    @BindView(R.id.getWord_secretWord)
    TextView secretWord;
    @BindView(R.id.getWord_btnGW)
    public ImageButton btnGetWord;
    private int index;
    private List<Game> gameList;
    private Context context;
    private String TAG;

    public DialogGetWord(int index, List<Game> gameList) {
        this.index = index;
        this.gameList = gameList;
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
        TAG = tag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_get_word,container,false);
        ButterKnife.bind(this, view);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        context = view.getContext();
        Log.d("DialogWord: ", TAG);
        Glide.with(view.getContext()).load(R.drawable.border).into(getWord_bg);
        Glide.with(view.getContext()).load(R.drawable.ava_1).into(imgAva);
        Glide.with(view.getContext()).load(R.drawable.get_word).into(btnGetWord);
        secretWord.setVisibility(View.GONE);
        bg_Text.setVisibility(View.GONE);
        edtName.setVisibility(View.VISIBLE);
        if(gameList.get(index).getName().trim().equals("Player")){
            edtName.setEnabled(true);
            edtName.setText("");
            Log.d("DialogWord: ", "Equals Player");
        }else{
            edtName.setEnabled(false);
            edtName.setText(gameList.get(index).getName());
            Log.d("DialogWord: ", "not equals Player");
        }
        btnGetWord.setContentDescription("Dapatkan");
        return view;
    }

    @OnClick(R.id.getWord_btnGW)
    public void setBtnGetWord(){
        boolean isSame = false;
        String lowerCase= edtName.getText().toString().trim().toLowerCase();
        if (edtName.isEnabled()){
            Log.d("DialogWord: ", "edt isEnabled");
            if(lowerCase.equals("") || lowerCase.trim().equals("player")){
                edtName.setHint("Masukkan nickname Anda!");
                Glide.with(context).load(R.drawable.get_word).into(btnGetWord);
                btnGetWord.setContentDescription("Dapatkan");
                edtName.setEnabled(true);
                Log.d("DialogWord: ", "equals player or zero");
                return;
            }
            int i = 0;
            while (true) {
                if (i >= gameList.size()) {
                    break;
                } else if (gameList.get(i).getName().trim().toLowerCase().equals(lowerCase)) {
                    Toast.makeText(context, "Nickname sudah terpakai " + lowerCase, Toast.LENGTH_SHORT).show();
                    isSame = true;
                    break;
                } else {
                    i++;
                }
            }
            if(!isSame){
                Log.d("DialogWord: ", "not same");
                edtName.setVisibility(View.GONE);
                bg_Text.setVisibility(View.VISIBLE);
                secretWord.setVisibility(View.VISIBLE);
                setRole(index);
                gameList.get(index).setReady(true);
                gameList.get(index).setName(edtName.getText().toString().trim());
                edtName.setEnabled(false);
                Glide.with(context).load(R.drawable.ok).into(btnGetWord);
                btnGetWord.setContentDescription("OKE");
            }
        }else if(btnGetWord.getContentDescription().equals("Dapatkan")){
            Log.d("DialogWord: ", "content description equals dapatkan");
            edtName.setVisibility(View.GONE);
            bg_Text.setVisibility(View.VISIBLE);
            secretWord.setVisibility(View.VISIBLE);
            setRole(index);
            gameList.get(index).setReady(true);
            gameList.get(index).setName(edtName.getText().toString().trim());
            edtName.setEnabled(false);
            Glide.with(context).load(R.drawable.ok).into(btnGetWord);
            btnGetWord.setContentDescription("OKE");
        }else {
            Log.d("DialogWord: ", "dismiss");
            dismiss();
            edtName.setText("");
            gameList.get(0).setJumlah(gameList.get(0).getJumlah()+1);
        }
    }

    private void setRole(int index) {
        String role = gameList.get(index).getRole();
        String word = gameList.get(index).getSecretWord();
        switch (role) {
            case "Penduduk":
                Glide.with(context)
                        .load(R.drawable.aturan_2)
                        .into(imgAva);
                break;
            case "Pendatang":
                Glide.with(context)
                        .load(R.drawable.aturan_3)
                        .into(imgAva);
                break;
            case "Raja":
                Glide.with(context)
                        .load(R.drawable.aturan_4)
                        .into(imgAva);
                break;
        }
        secretWord.setText(word);
    }

}
