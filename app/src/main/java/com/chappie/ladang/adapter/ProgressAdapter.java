package com.chappie.ladang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chappie.ladang.R;
import com.chappie.ladang.model.Game;

import java.util.Collections;
import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.pbHolder> {
    private Context context;
    private List<Game> gameList;

    public ProgressAdapter(Context context2, List<Game> list) {
        this.context = context2;
        this.gameList = list;
    }

    @NonNull
    public pbHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new pbHolder(LayoutInflater.from(this.context).inflate(R.layout.item_progress, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull pbHolder pbholder, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < this.gameList.size(); i3++) {
            i2 += this.gameList.get(i3).getPoint();
        }
        Collections.sort(this.gameList, (game, game2) ->
                Integer.compare(game2.getPoint(), game.getPoint()));

        pbholder.pb_Name.setMax(i2);
        pbholder.tv_Name.setText(this.gameList.get(i).getName());
        pbholder.tv_pointName.setText(String.valueOf(this.gameList.get(i).getPoint()));
        pbholder.pb_Name.setProgress(this.gameList.get(i).getPoint());
    }

    public int getItemCount() {
        return this.gameList.size();
    }

    static class pbHolder extends RecyclerView.ViewHolder {
        ProgressBar pb_Name;
        TextView tv_Name;
        TextView tv_pointName;

        pbHolder(View view) {
            super(view);
            this.pb_Name = view.findViewById(R.id.pb_Name);
            this.tv_pointName =  view.findViewById(R.id.tv_pointName);
            this.tv_Name =  view.findViewById(R.id.tv_Name);
        }
    }
}
