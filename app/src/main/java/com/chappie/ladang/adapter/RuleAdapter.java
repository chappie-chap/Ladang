package com.chappie.ladang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.chappie.ladang.R;
import com.chappie.ladang.model.Rule;

import java.util.List;

public class RuleAdapter extends PagerAdapter {

    private List<Rule> rules;
    private LayoutInflater layoutInflater;
    private Context context;

    public RuleAdapter(List<Rule> rules, Context context) {
        this.rules = rules;
        this.context = context;
    }

    @Override
    public int getCount() {
        return rules.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_rule, container,false);

        ImageView imageView;
        TextView tvTitle, tvDesc;

        imageView = view.findViewById(R.id.img_aturan);
        tvTitle = view.findViewById(R.id.title_aturan);
        tvDesc = view.findViewById(R.id.description);

        imageView.setImageResource(rules.get(position).getImg());
        tvTitle.setText(rules.get(position).getTitle());
        tvDesc.setText(rules.get(position).getDescription());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
