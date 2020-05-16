package com.chappie.ladang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chappie.ladang.R;
import com.chappie.ladang.model.Game;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.GridViewHolder> {
    private Context context;
    private List<Game> gameList;
    private OnItemClickCallback onItemClickCallback;

    public RecyclerViewAdapter(Context context, List<Game> gameList) {
        this.context = context;
        this.gameList = gameList;
    }

    public interface OnItemClickCallback{
        void onItemClicked(int i,GridViewHolder gridViewHolder);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GridViewHolder(LayoutInflater.from(context).inflate(R.layout.item_game, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.GridViewHolder holder, int position) {
        holder.item_card.setEnabled(true);
        Glide.with(context).load(R.drawable.ava_2).into(holder.bgGrid);
        Glide.with(context).load(R.drawable.ava_1).into(holder.crcGrid);
        holder.titleGrid.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(position, holder));
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.bgGrid)
        ImageView bgGrid;
        @BindView(R.id.crcGrid)
        public ImageView crcGrid;
        @BindView(R.id.titleGrid)
        public TextView titleGrid;
        @BindView(R.id.item_game)
        CardView item_card;

        GridViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }
}
