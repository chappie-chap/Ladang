package com.chappie.ladang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;

import com.chappie.ladang.adapter.RuleAdapter;
import com.chappie.ladang.adapter.ViewPagerAdapter;
import com.chappie.ladang.model.Rule;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);

        ImageView btnOK = findViewById(R.id.btnOK);

        ViewPager mImageViewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mImageViewPager, true);

        List<Rule> rules= new ArrayList<>();
        rules.add(new Rule(R.drawable.aturan_1,"Selamat Datang!","Ladang adalah media pembelajaran berbasis game dimana dimainkan secara berkelompok, yang dibutuhkan hanyalah satu smartphone dan teman kelompok belajar."));
        rules.add(new Rule(R.drawable.aturan_1,"Penduduk","Mereka semua menerima kata rahasia yang sama. Tujuan mereka adalah membuka kedok pendatang dan sang raja."));
        rules.add(new Rule(R.drawable.aturan_1,"Pendatang","pendatang menerima kata yang sedikit berbeda dari warga. Tujuannya adalah berpura-pura menjadi salah satu dari mereka!"));
        rules.add(new Rule(R.drawable.aturan_1,"Raja","Raja tidak menerima sepatah kata pun. Tujuannya adalah bertindak seolah olah dia memiliki kata, sementara menebak kata warga."));
        rules.add(new Rule(R.drawable.aturan_1,"Persiapan","Sesuaikan jumlah pemain dan jumlah peran yang diinginkan."));
        rules.add(new Rule(R.drawable.aturan_1,"Kata Rahasia","Open smartphone secara bergilir untuk mendapatkan kata rahasia. warga mendapatkan kata yang sama, penipu mendapatkan kata yang sedikit berbeda, dan pencuri tidak mendapatkan kata."));
        rules.add(new Rule(R.drawable.aturan_1,"Deskripsi","Setiap pemain harus memberikan deskripsi tentang kata mereka. Pencuri harus berimprovisasi."));
        rules.add(new Rule(R.drawable.aturan_1,"Voting","Temukan pemain yang paling mencurigakan, diskusikan satu sama lain, lalu secara bersamaan semua pemain memilih dengan cara menunjuk."));
        rules.add(new Rule(R.drawable.aturan_1,"Praktik","Setelah menyelesaikan permaianan maka pemain akan bermain praktik yang berkaitan dengan mapel tersebut."));
        rules.add(new Rule(R.drawable.aturan_1,"Poin","Jika berhasil mejawab tantangan dengan benar 25 poin, salah 0 poin."));

        RuleAdapter pagerAdapter = new RuleAdapter(rules, this);
        mImageViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mImageViewPager);

        btnOK.setOnClickListener(v -> onBackPressed());
    }
}
