package com.chappie.ladang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chappie.ladang.adapter.DialogGetWord;
import com.chappie.ladang.adapter.DialogMessage;
import com.chappie.ladang.adapter.DialogSmall;
import com.chappie.ladang.adapter.DialogWinner;
import com.chappie.ladang.adapter.RecyclerViewAdapter;
import com.chappie.ladang.helper.ColumnQty;
import com.chappie.ladang.helper.GridSpacing;
import com.chappie.ladang.model.Game;
import com.chappie.ladang.model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    public static final String EXTRA_PENDUDUK = "extra_penduduk";
    public static final String EXTRA_PeNDATANG = "extra_pendatang";
    public static final String EXTRA_RAJA = "extra_raja";
    @BindView(R.id.game_tvEliminate)
    TextView tvEliminate;
    @BindView(R.id.rv_game)
    RecyclerView rv_Game;
    @BindView(R.id.game_back)
    ImageButton gameBack;
    private RecyclerViewAdapter rvAdapter;
    private RecyclerViewAdapter.GridViewHolder holder;
    private ArrayList<Game> gameList;
    private ArrayList<Word> wordList;
    private int penduduk, pendatang, raja, sisaPenduduk, sisaPendatang, sisaRaja;
    private Boolean isPlayAgain;
    private SharedPreferences pref;

    public int getPenduduk() {
        return penduduk;
    }

    public void setPenduduk(int penduduk) {
        this.penduduk = penduduk;
    }

    public int getPendatang() {
        return pendatang;
    }

    public void setPendatang(int pendatang) {
        this.pendatang = pendatang;
    }

    public int getRaja() {
        return raja;
    }

    public void setRaja(int raja) {
        this.raja = raja;
    }

    public int getSisaPenduduk() {
        return sisaPenduduk;
    }

    public void setSisaPenduduk(int sisaPenduduk) {
        this.sisaPenduduk = sisaPenduduk;
    }

    public int getSisaPendatang() {
        return sisaPendatang;
    }

    public void setSisaPendatang(int sisaPendatang) {
        this.sisaPendatang = sisaPendatang;
    }

    public int getSisaRaja() {
        return sisaRaja;
    }

    public void setSisaRaja(int sisaRaja) {
        this.sisaRaja = sisaRaja;
    }

    public void setPlayAgain(Boolean playAgain) {
        isPlayAgain = playAgain;
    }

    public Boolean isPlayAgain() {
        return isPlayAgain;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.back).into(gameBack);
        tvEliminate.setVisibility(View.GONE);
        ColumnQty columnQty = new ColumnQty(GameActivity.this, R.layout.item_game);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GameActivity.this, columnQty.calculateNoOfColumns());
        rv_Game.addItemDecoration(new GridSpacing(columnQty.calculateSpacing()));
        rv_Game.setLayoutManager(gridLayoutManager);
        pref = getSharedPreferences("Game_Ladang", 0);
        boolean check = pref.getBoolean("isOpenedBefore", false );
        setPenduduk(getIntent().getIntExtra(EXTRA_PENDUDUK, 0));
        setPendatang(getIntent().getIntExtra(EXTRA_PeNDATANG, 0));
        setRaja(getIntent().getIntExtra(EXTRA_RAJA, 0));
        initWord();
        init();
        setPlayAgain(false);
        rvAdapter = new RecyclerViewAdapter(this,gameList);
        rv_Game.setAdapter(rvAdapter);
        rvAdapter.setOnItemClickCallback((i, gridViewHolder) -> {
            holder = gridViewHolder;
            if(isPlayAgain()){
                if(!gameList.get(i).isReady()){
                    Toast.makeText(getApplicationContext(),"Ready",Toast.LENGTH_SHORT).show();
                    dialogChoose(i);
                }else if(gameList.get(0).getJumlah() == gameList.size() && gameList.get(i).isReady()) {
                    Toast.makeText(getApplicationContext(),"not Ready yet",Toast.LENGTH_SHORT).show();
                    dialogVote(i);
                }
            }else if(!gameList.get(i).isReady()){
                Toast.makeText(getApplicationContext(),"Let's Play!",Toast.LENGTH_SHORT).show();
                dialogGetWord(i);
            }else if(gameList.get(0).getJumlah() == gameList.size() && gameList.get(i).isReady()){
                Log.d("Game: ", " Vote statment");
                dialogVote(i);
            }
        });
        if(check){
            Toast.makeText(getApplicationContext(),"Opened Before", Toast.LENGTH_SHORT).show();
        }
    }

    private void dialogChoose(int i) {
        DialogSmall small = new DialogSmall("Kartu", "Ini adalah kartu milik "+gameList.get(i).getName());
        FragmentManager fm = getSupportFragmentManager();
        small.show(fm, "Choose");
        fm.executePendingTransactions();
        small.small_btnOK.setOnClickListener(v -> {
            small.dismiss();
            dialogGetWord(i);
        });

    }

    private void dialogVote(int i) {
        String name =gameList.get(i).getName();
        DialogSmall small = new DialogSmall("Vote", name+", Akan dieliminasi, Yakin ?");
        FragmentManager fm= getSupportFragmentManager();
        small.show(fm,"Vote");
        fm.executePendingTransactions();
        small.small_btnOK.setOnClickListener( view ->{
            if(!gameList.isEmpty()){
                if(!gameList.get(i).getRole().equals("Raja")){
                    gameList.get(i).setEliminate(true);
                }
                small.dismiss();
                dialogEliminated(i);
            }else {
                small.dismiss();
            }
        });
    }

    private void dialogEliminated(int i) {
        ArrayList<Integer> sisa = new ArrayList<>();
        sisa.add(getSisaPenduduk());
        sisa.add(getSisaPendatang());
        sisa.add(getSisaRaja());
        DialogMessage message = new DialogMessage(gameList, i, holder, sisa);
        FragmentManager fm= getSupportFragmentManager();
        message.show(fm,"Eliminated");
        message.setOnItemClickCallback(sisa1 -> {
            if (message.TAG.equals("Guest")) {
                Log.d("Message: ","Guest");
                String tebakan = message.message_edText.getText().toString().toLowerCase();
                for(int x=0; x<gameList.size(); x++){
                    if(gameList.get(x).getRole().equals("Penduduk")){
                        if (gameList.get(x).getSecretWord().toLowerCase().trim().equals(tebakan)){
                            gameList.get(x).setEliminate(true);
                            message.eliminate();
                        }else {
                            message.dismiss();
                            dialogWinner(3);
                            Log.d("Message: ","Dialog Winner "+3);
                        }
                    }
                }
            }else if(message.TAG.equals("Eliminated")) {
                Log.d("Message: ","Eliminated");
                if(sisa1.get(0) ==1){
                    if(sisa1.get(1) !=0 && sisa1.get(2)!=0){
                        message.dismiss();
                        dialogWinner(4);
                        Log.d("Message: ","Dialog Winner "+4);
                    }else if(sisa1.get(1)!=0){
                        message.dismiss();
                        dialogWinner(2);
                        Log.d("Message: ","Dialog Winner "+2);
                    }else {
                        message.dismiss();
                        dialogWinner(3);
                        Log.d("Message: ","Dialog Winner "+3);
                    }
                }else if(sisa1.get(1)==0 && sisa1.get(2)==0){
                    message.dismiss();
                    dialogWinner(1);
                    Log.d("Message: ","Dialog Winner "+1);
                }
            }else{
                Log.d("Message: ","dismiss");
                message.dismiss();
            }
        });
    }

    private void dialogWinner(int i) {
        DialogWinner winner = new DialogWinner(gameList, i);
        FragmentManager fm = getSupportFragmentManager();
        winner.show(fm,"Winner");
        fm.executePendingTransactions();
        winner.setOnItemClickCallback(list -> {
            winner.dismiss();
            gameList = list;
            setPlayAgain(true);
            gameList.get(0).setJumlah(0);
            initAgain();
            rvAdapter.notifyDataSetChanged();
        });
        winner.winner_btnPraktik.setOnClickListener(v -> {
            Intent intent = new Intent(GameActivity.this, PractectActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void dialogGetWord(final int i) {
        final DialogGetWord dialogGetWord = new DialogGetWord(i,gameList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        dialogGetWord.show(fragmentManager,"Get Word");
        fragmentManager.executePendingTransactions();
        dialogGetWord.btnGetWord.setOnClickListener(v -> {
            dialogGetWord.setBtnGetWord();
            if (!GameActivity.this.gameList.get(i).getName().trim().equals("Player")){
                holder.titleGrid.setText(GameActivity.this.gameList.get(i).getName().trim());
                holder.titleGrid.setVisibility(View.VISIBLE);
                mulai(i);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void mulai(int i) {
        if(gameList.get(0).getJumlah()==gameList.size() && gameList.get(i).isReady()){
            DialogMessage dialogMessage = new DialogMessage(gameList,i);
            FragmentManager fragmentManager = getSupportFragmentManager();
            dialogMessage.show(fragmentManager,"Start");
            tvEliminate.setVisibility(View.VISIBLE);
            tvEliminate.setText("Pilih yang ingin dieliminasi!");
        }
    }

    private void init() {
        ArrayList<String> arrayList = new ArrayList<>();
        int i =0;
        while (i<wordList.size()){
            if(!wordList.get(i).isPlayed()){
                arrayList.add(wordList.get(i).getsWord1());
                arrayList.add(wordList.get(i).getsWord2());
                i = wordList.size();
            }
            if (i== wordList.size() && arrayList.get(0)==null){
                initWord();
                i=0;
            }
            i++;
        }
        gameList = new ArrayList<>();
        int x=0;
        while (true){
            int i1 = getPenduduk();
            int i2 = getPendatang();
            int i3 = getRaja();
            if (x< i1+i2+i3){
                if(x<i1) gameList.add(new Game("Player","Penduduk", arrayList.get(0),false,false,0));
                else if(x<i1+i2) gameList.add(new Game("Player","Pendatang", arrayList.get(1),false,false,0));
                else if(x<i1+i2+i3) gameList.add(new Game("Player","Raja", "^^",false,false,0));
                x++;
            }else{
                Collections.shuffle(gameList);
                gameList.get(0).setJumlah(0);
                setSisaPenduduk(getPenduduk());
                setSisaPendatang(getPendatang());
                setSisaRaja(getRaja());
                return;
            }
        }
    }

    void initAgain(){
        ArrayList<String> arrayList = new ArrayList<>();
        initWord();
        int i =0;
        while (i<wordList.size()){
            if(!wordList.get(i).isPlayed()){
                arrayList.add(wordList.get(i).getsWord1());
                arrayList.add(wordList.get(i).getsWord2());
                i = wordList.size();
            }
            if (i== wordList.size() && arrayList.get(0)==null){
                initWord();
                i=0;
            }
            i++;
        }
        Collections.shuffle(arrayList);
        Collections.shuffle(gameList);
        int x=0;
        while (true){
            int i1 = getPenduduk();
            int i2 = getPendatang();
            int i3 = getRaja();
            if (x<i1+i2+i3){
                if(x<i1) {
                    gameList.get(x).setRole("Penduduk");
                    gameList.get(x).setEliminate(false);
                    gameList.get(x).setReady(false);
                    gameList.get(x).setSecretWord(arrayList.get(0));
                }
                else if(x<i1+i2){
                    gameList.get(x).setRole("Pendatang");
                    gameList.get(x).setEliminate(false);
                    gameList.get(x).setReady(false);
                    gameList.get(x).setSecretWord(arrayList.get(1));
                }
                else if(x<i1+i2+i3){
                    gameList.get(x).setRole("Raja");
                    gameList.get(x).setEliminate(false);
                    gameList.get(x).setReady(false);
                    gameList.get(x).setSecretWord("^^");
                }
                x++;
            }else{
                Collections.shuffle(gameList);
                setSisaPenduduk(getPenduduk());
                setSisaPendatang(getPendatang());
                setSisaRaja(getRaja());
                return;
            }
        }
    }

    private void initWord() {
        wordList = new ArrayList<>();
        wordList.add(new Word(false,"ilustrasi", "sketsa"));
        wordList.add(new Word(false,"photoshop", "coreldraw"));
        wordList.add(new Word(false,"poster", "brosur"));
        wordList.add(new Word(false,"garis vertikal", "garis horizontal"));
        wordList.add(new Word(false,"fotografi", "lukisan"));
        wordList.add(new Word(false,"kartun", "karikatur"));
        wordList.add(new Word(false,"proporsi", "komposisi"));
        wordList.add(new Word(false,"kubitis", "silindris"));
        wordList.add(new Word(false,"linier", "arsir"));
        wordList.add(new Word(false,"perspektif 1 titik", "perspektif 2 titik"));
        Collections.shuffle(wordList);
    }

    @Override
    public void onBackPressed() {
        DialogSmall small = new DialogSmall("Keluar", "Kamu yakin ingin keluar ?");
        small.show(getSupportFragmentManager(),"Exit");

    }

    @OnClick(R.id.game_back)
    void back(){
        onBackPressed();
    }
}
