package com.chappie.ladang.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chappie.ladang.R;
import com.chappie.ladang.model.Level;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogLevel extends DialogFragment {
    private LevelAdapter adapter;
    private List<Level> levels;
    private int i;
    @BindView(R.id.rv_level)
    RecyclerView rv_level;
    @BindView(R.id.level_bg)
    ImageView level_bg;
    @BindView(R.id.level_title)
    TextView level_title;
    @BindView(R.id.level_close)
    ImageButton level_close;
    private OnItemClickCallback onItemClickCallback;

    public interface OnItemClickCallback{
        void onItemClicked(int i);
        void onItemClicked();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public DialogLevel(List<Level> levels) {
        this.levels = levels;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.dialog_level,container,false);
        ButterKnife.bind(this, view);
        Log.d("DialogLevel: ", "Choose");
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        Glide.with(view.getContext()).load(R.drawable.bg_level).into(level_bg);
        Glide.with(view.getContext()).load(R.drawable.close).into(level_close);
        adapter = new LevelAdapter(getContext(),levels);
        rv_level.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv_level.setAdapter(adapter);
        level_title.setText("Pilih Level!");
        adapter.setOnItemClickCallback(i -> {
            this.i =i;
            if(!levels.isEmpty()){
                if(levels.get(i).getType().equals("Question")){
                    dismiss();
                    onItemClickCallback.onItemClicked(i);
                    Log.d("Level: ","position: "+i);
                }
            }
        });
        return view;
    }

    @OnClick(R.id.level_close)
    public void Close(){
        dismiss();
        onItemClickCallback.onItemClicked();
    }

}
