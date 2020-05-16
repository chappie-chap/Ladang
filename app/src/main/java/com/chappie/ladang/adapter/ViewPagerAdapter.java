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
import com.chappie.ladang.model.Intro;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<Intro> introList;
    private LayoutInflater layoutInflater;
    private Context context;

    public ViewPagerAdapter(List<Intro> introList, Context context) {
        this.introList = introList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return introList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_intro, container,false);

        ImageView imageView;
        TextView tvIntro;

        imageView = view.findViewById(R.id.intro_bgText);
        tvIntro = view.findViewById(R.id.intro_tv);

        imageView.setImageResource(introList.get(position).getImgbg());
        tvIntro.setText(introList.get(position).getTv_intro());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
