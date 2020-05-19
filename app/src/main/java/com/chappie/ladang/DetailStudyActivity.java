package com.chappie.ladang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chappie.ladang.model.Study;


import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailStudyActivity extends AppCompatActivity {

    public static final String EXTRA_STUDIES = "studies";
    private Study studies;
    @BindView(R.id.study_poster)
    ImageView poster;
    @BindView(R.id.study_title)
    TextView title;
    @BindView(R.id.study_desc)
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_study);
        ButterKnife.bind(this);
        if(getIntent().getParcelableExtra(EXTRA_STUDIES)!=null)
        studies = getIntent().getParcelableExtra(EXTRA_STUDIES);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(studies.getTitle());
        }

        Glide.with(this).load(studies.getImgBack()).into(poster);
        title.setText(studies.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            desc.setText(Html.fromHtml(studies.getDesc(),Html.FROM_HTML_MODE_COMPACT));
        }else{
            desc.setText(Html.fromHtml(studies.getDesc()));
        }


    }
}
