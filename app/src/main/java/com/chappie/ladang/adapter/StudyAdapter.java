package com.chappie.ladang.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chappie.ladang.R;
import com.chappie.ladang.model.Study;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Study> studies;
    private OnItemClickCallback onItemClickCallback;

    public StudyAdapter(Context context, ArrayList<Study> studies) {
        this.context = context;
        this.studies = studies;
    }

    public interface OnItemClickCallback{
        void onItemClicked(int i);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }
    @NonNull
    @Override
    public StudyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_study, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(studies.get(position).getImgBack()).into(holder.poster);
        holder.title.setText(studies.get(position).getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.desc.setText(Html.fromHtml(studies.get(position).getDesc(),Html.FROM_HTML_MODE_COMPACT));
        }else{
            holder.desc.setText(Html.fromHtml(studies.get(position).getDesc()));
        }
        holder.layout.setOnClickListener(v -> onItemClickCallback.onItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return studies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_study)
        ConstraintLayout layout;
        @BindView(R.id.study_poster)
        ImageView poster;
        @BindView(R.id.study_title)
        TextView title;
        @BindView(R.id.study_desc)
        TextView desc;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
