package com.chappie.ladang.adapter;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.chappie.ladang.R;
import com.chappie.ladang.model.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DialogMessage extends DialogFragment {
    @BindView(R.id.message_bg)
    ImageView message_bg;
    @BindView(R.id.message_1)
    TextView message_1;
    @BindView(R.id.message_Ava)
    CircleImageView message_ava;
    @BindView(R.id.message_2)
    TextView message_2;
    @BindView(R.id.message_edText)
    public EditText message_edText;
    @BindView(R.id.message_btnOK)
    public ImageButton message_btnOK;
    @BindView(R.id.message_lay1)
    LinearLayout message_lay1;
    @BindView(R.id.message_lay2)
    LinearLayout message_lay2;
    public String TAG;
    private ArrayList<Game> gameList;
    private int index;
    private RecyclerViewAdapter.GridViewHolder holder;
    private View v;
    private ArrayList<Integer> sisa= new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public interface OnItemClickCallback{
        void onItemClicked(ArrayList<Integer> sisa);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback =onItemClickCallback;
    }

    public DialogMessage(ArrayList<Game> gameList, int index) {
        this.gameList = gameList;
        this.index = index;
    }

    public DialogMessage(ArrayList<Game> gameList, int index, RecyclerViewAdapter.GridViewHolder holder, ArrayList<Integer> sisa) {
        this.gameList = gameList;
        this.index = index;
        this.holder = holder;
        this.sisa.addAll(sisa);
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
        TAG = tag;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_message, container, false);
        v =view;
        ButterKnife.bind(this, view);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        init();
        Glide.with(view.getContext()).load(R.drawable.border).into(message_bg);
        Glide.with(view.getContext()).load(R.drawable.ava_1).into(message_ava);
        Glide.with(view.getContext()).load(R.drawable.ok).into(message_btnOK);

        message_btnOK.setOnClickListener(v1 -> {
            if(sisa.isEmpty()){
                dismiss();
            }else {
                onItemClickCallback.onItemClicked(sisa);
            }
        });

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        switch (TAG) {
            case "Start":
                ArrayList<String> arrayList = new ArrayList<>();
                for (int x = 0; x < gameList.size(); x++) {
                    arrayList.add(gameList.get(x).getName());
                }
                Collections.shuffle(arrayList);
                message_1.setVisibility(View.VISIBLE);
                message_2.setVisibility(View.GONE);
                message_edText.setVisibility(View.GONE);
                message_1.setText("Deskripsikan katamu " + arrayList.get(0) + ", Mulai duluan");
                message_lay2.setVisibility(View.GONE);
                break;
            case "Eliminated":
                holder.item_card.setEnabled(false);
                String message, role;
                int drawable;
                if (gameList.get(index).getRole().equals("Penduduk")) {
                    Log.d("Message: ", "Penduduk");
                    sisa.set(0, sisa.get(0) - 1);
                    if (sisa.get(0) > 1) {
                        role = gameList.get(index).getRole();
                        message = "Hmmm, Kamu salah menebak";
                        drawable = R.drawable.aturan_2;
                    } else {
                        role = gameList.get(index).getRole();
                        message = "Hmmm, Kamu salah menebak";
                        drawable = R.drawable.aturan_2;
                    }
                } else if (gameList.get(index).getRole().equals("Pendatang")) {
                    Log.d("Message: ", "Pendatang");
                    sisa.set(1, sisa.get(1) - 1);
                    if (sisa.get(1) != 0) {
                        role = gameList.get(index).getRole();
                        message = "Yeaay, Kamu kamu berhasil menebak";
                        drawable = R.drawable.aturan_3;
                    } else {
                        role = gameList.get(index).getRole();
                        message = "Yeaay, Kamu kamu berhasil menebak";
                        drawable = R.drawable.aturan_3;
                    }
                } else {
                    Log.d("Message: ", "Guest");
                    sisa.set(2, sisa.get(2) - 1);
                    if (sisa.get(2) != 0) {
                        TAG = "Guest";
                        init();
                        return;
                    } else {
                        TAG = "Guest";
                        init();
                        return;
                    }
                }
                message_lay1.setVisibility(View.VISIBLE);
                message_lay2.setVisibility(View.VISIBLE);
                message_1.setVisibility(View.VISIBLE);
                message_2.setVisibility(View.VISIBLE);
                message_edText.setVisibility(View.GONE);
                Glide.with(v.getContext()).load(drawable).into(message_ava);
                Glide.with(v.getContext()).load(drawable).into(holder.crcGrid);
                message_1.setText(message);
                message_2.setText(role);
                break;
            case "Guest":
                message_lay1.setVisibility(View.GONE);
                message_lay2.setVisibility(View.VISIBLE);
                message_1.setVisibility(View.GONE);
                message_2.setVisibility(View.VISIBLE);
                message_edText.setVisibility(View.VISIBLE);
                Glide.with(v.getContext()).load(R.drawable.aturan_4).into(message_ava);
                Glide.with(v.getContext()).load(R.drawable.aturan_4).into(holder.crcGrid);
                message_2.setText("Coba tebak kata warga");
                message_edText.setVisibility(View.VISIBLE);
                message_edText.setHint("Tebak kata rahasia penduduk");
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    public void eliminate(){
            message_lay2.setVisibility(View.GONE);
            message_lay1.setVisibility(View.VISIBLE);
            message_1.setVisibility(View.VISIBLE);
            message_1.setText("Sorry ya! Tebakan kamu salah.");
    }
}
