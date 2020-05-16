package com.chappie.ladang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chappie.ladang.R;
import com.chappie.ladang.model.Level;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {
    private Context context;
    private List<Level> levelList;
    private OnItemClickCallback onItemClickCallback;


    public interface OnItemClickCallback{
        void onItemClicked(int i);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public LevelAdapter(Context context, List<Level> levelList) {
        this.context = context;
        this.levelList = levelList;
    }

    @NonNull
    @Override
    public LevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_level,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(levelList.get(position).isPlay()){
            holder.btnLevel.setEnabled(false);
            holder.imgDisabled.setVisibility(View.VISIBLE);
            Glide.with(context).load(levelList.get(position).getImgLevel()).into(holder.btnLevel);
        }else {
            holder.btnLevel.setEnabled(true);
            holder.imgDisabled.setVisibility(View.INVISIBLE);
            Glide.with(context).load(levelList.get(position).getImgLevel()).into(holder.btnLevel);
        }
        holder.btnLevel.setOnClickListener(v -> {
            onItemClickCallback.onItemClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return levelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnLevel)
        ImageButton btnLevel;
        @BindView(R.id.imgDisabled)
        ImageView imgDisabled;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
