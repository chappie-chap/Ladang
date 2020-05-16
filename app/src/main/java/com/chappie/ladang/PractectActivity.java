package com.chappie.ladang;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chappie.ladang.adapter.DialogLevel;
import com.chappie.ladang.adapter.DialogSmall;
import com.chappie.ladang.adapter.puzzle.PuzzleAdapter;
import com.chappie.ladang.helper.CountDownTimerPausable;
import com.chappie.ladang.helper.Puzzle;
import com.chappie.ladang.model.Level;
import com.chappie.ladang.model.Pieces;
import com.chappie.ladang.model.PuzzlePiece;
import com.chappie.ladang.model.Quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PractectActivity extends AppCompatActivity {

    @BindView(R.id.layout_constraint)
    ConstraintLayout layout;
    @BindView(R.id.constraintLayout1)
    ConstraintLayout constraintLayout1;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.practice_timer)
    TextView timer;
    @BindView(R.id.practice_change)
    TextView change;
    @BindView(R.id.practice_score)
    TextView score;
    @BindView(R.id.practice_question)
    TextView question;
    @BindView(R.id.bg_answer)
    ImageView bg_answer;
    @BindView(R.id.img_puzzle)
    ImageView imgPuzzle;
    @BindView(R.id.imgCheck1)
    ImageView imgCheck1;
    @BindView(R.id.imgCheck2)
    ImageView imgCheck2;
    @BindView(R.id.imgCheck3)
    ImageView imgCheck3;
    @BindView(R.id.imgCheck4)
    ImageView imgCheck4;
    @BindView(R.id.imgCheck5)
    ImageView imgCheck5;
    @BindView(R.id.answer1)
    ImageButton answer1;
    @BindView(R.id.answer2)
    ImageButton answer2;
    @BindView(R.id.answer3)
    ImageButton answer3;
    @BindView(R.id.answer4)
    ImageButton answer4;
    @BindView(R.id.answer5)
    ImageButton answer5;
    @BindView(R.id.select_next)
    ImageButton nextLevel;
    @BindView(R.id.selectLevel)
    ImageButton selectLevel;
    @BindView(R.id.rv_puzzle)
    RecyclerView rv_puzzle;
    @BindView(R.id.scrollView)
    FrameLayout scrollView;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    private List<Pieces> piecesModelListMain = new ArrayList<>();
    private HashMap<String, Pieces> piecesModelHashMap = new HashMap<>();
    private ArrayList<PuzzlePiece> puzzlePiecesList = new ArrayList<>();
    private PuzzleAdapter puzzleListAdapter;
    private Puzzle puzzle;
    private boolean widthCheck =true;
    private int countGrid =0, widthFinal,heightFinal;
    private List<Level> levels;
    private List<Quest> quests;
    private List<List<Object>> arrayLists = new ArrayList<>();
    private List<ImageButton> list1 = new ArrayList<>();
    private int changer =3,skor=0,tempskor=0,positionAdapter;
    private CountDownTimerPausable countDown;
    private boolean isFirst;

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practect);
        ButterKnife.bind(this);
        initLevel();
        initQuestion();
        layout.setVisibility(View.INVISIBLE);
        constraintLayout1.setVisibility(View.GONE);
        constraintLayout2.setVisibility(View.GONE);
        bg_answer.setVisibility(View.GONE);
        imgCheck1.setVisibility(View.INVISIBLE);
        imgCheck2.setVisibility(View.INVISIBLE);
        imgCheck3.setVisibility(View.INVISIBLE);
        imgCheck4.setVisibility(View.INVISIBLE);
        imgCheck5.setVisibility(View.INVISIBLE);
        list1.add(answer1);
        list1.add(answer2);
        list1.add(answer3);
        list1.add(answer4);
        list1.add(answer5);
        setFirst(true);
        dialogLevel();
        score.setText("Skor: "+skor);
    }

    void dialogLevel(){
        DialogLevel level = new DialogLevel(levels);
        FragmentManager fm = getSupportFragmentManager();
        level.show(fm,"Level");
        fm.executePendingTransactions();
        level.setOnItemClickCallback(new DialogLevel.OnItemClickCallback() {
            @Override
            public void onItemClicked(int i) {
                CreateQuest(i);
            }

            @Override
            public void onItemClicked() {
                if (layout.getVisibility()==View.VISIBLE){
                    levels.get(positionAdapter).setPlay(true);
                    level.dismiss();
                }else if(layout.getVisibility()==View.INVISIBLE){
                    level.dismiss();
                    DialogSmall small = new DialogSmall("Keluar","Yakin inngin kembali ke halaman menu ?");
                    FragmentManager fmi = getSupportFragmentManager();
                    small.show(fmi,"Exit");
                    fmi.executePendingTransactions();
                    small.small_btnOK.setOnClickListener(view ->{
                        Intent intent = new Intent(PractectActivity.this,MenuActivity.class);
                        startActivity(intent);
                        finish();
                    });
                    small.small_close.setOnClickListener(v1 -> dialogLevel());
                }
                if(countDown.isPaused()){
                    countDown.resume();
                }
            }
        });
    }

    private void CreateQuest(int i) {
        Log.d("Practice: ","Position Adapter: "+i);
        positionAdapter=i;
        if(isFirst()){
            setFirst(false);
            createQuest(i);
            countDown = new CountDownTimerPausable(300000,1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    timer.setText(""+millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    if(changer==0){
                        //dialogSelesai()
                    }else if(changer>0){
                        countDown.pause();
                        changer-=1;
                        change.setText("Kesempatan: " + changer);
                        DialogSmall dialogSmall = new DialogSmall("Waktu Habis", "Kesempatan kamu tersisa "+changer);
                        FragmentManager fm = getSupportFragmentManager();
                        dialogSmall.show(fm,"Timer");
                        fm.executePendingTransactions();
                        dialogSmall.small_btnOK.setOnClickListener(v ->{
                            dialogSmall.dismiss();
                            countDown.start();
                        });
                    }
                }
            }.start();
            constraintLayout1.setVisibility(View.VISIBLE);
            bg_answer.setVisibility(View.VISIBLE);
        }else{
            switch (levels.get(i).getType()) {
                case "Question":
                    createQuest(i);
                    break;
                case "Puzzle":
                    constraintLayout1.setVisibility(View.GONE);
                    bg_answer.setVisibility(View.GONE);
                    constraintLayout2.setVisibility(View.VISIBLE);
                    question.setText(quests.get(i).getQuestion());
                    scrollView.setOnDragListener(new MyDragListener(null));
                    relativeLayout.setOnDragListener(new MyDragListener(null));
                    rv_puzzle.setOnDragListener(new MyDragListener(null));
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    rv_puzzle.setLayoutManager(linearLayoutManager);
                    puzzle = new Puzzle();
                    puzzlePiecesList.clear();
                    piecesModelListMain.clear();
                    piecesModelHashMap.clear();

                    ViewTreeObserver obs = scrollView.getViewTreeObserver();
                    obs.addOnGlobalLayoutListener(() -> {
                        if (widthCheck) {
                            Log.d("Practice: ","in WidthCheck");
                            widthFinal = scrollView.getWidth();
                            heightFinal = scrollView.getHeight();
                            puzzlePiecesList = puzzle.createPuzzlePieces(PractectActivity.this, null, widthFinal, heightFinal, imgPuzzle, "/puzzles/", 3, 3, quests.get(i).getImgPuzzle());
                            getAdapter();
                            widthCheck = false;
                        }
                    });

                    setPuzzleListAdapter();
                    levels.get(i).setPlay(true);
                    break;
                case "Drawing":
                    //createDrawing(i);
                    break;
            }
            if(countDown.isPaused()){
                countDown.resume();
            }
        }
    }

    public void getAdapter() {
        RelativeLayout.LayoutParams params;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                PuzzlePiece piece = puzzlePiecesList.get(countGrid);
                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                int dimX = piece.getAnchorPoint().x - piece.getCenterPoint().x;
                int dimY = piece.getAnchorPoint().y - piece.getCenterPoint().y;

                params.setMargins(dimX, dimY, 0, 0);
                ImageView button2 = new ImageView(this);
                button2.setId(generateViewId());
                button2.setTag(i + "," + j);

                button2.setImageResource(R.drawable.ic_1);

                button2.setOnDragListener(new MyDragListener(button2));
                button2.setLayoutParams(params);
                relativeLayout.addView(button2);

                Pieces piecesModel = new Pieces();
                piecesModel.setpX(i);
                piecesModel.setpY(j);
                piecesModel.setPosition(countGrid);
                piecesModel.setOriginalResource(puzzlePiecesList.get(countGrid).getImage());
                piecesModelListMain.add(piecesModel);
                Collections.shuffle(piecesModelListMain);
                piecesModelHashMap.put(i + "," + j, piecesModel);
                piecesModel = null;

                countGrid++;

            }
        }
    }

    public int generateViewId() {
        AtomicInteger sNextGeneratedId = new AtomicInteger(1);
        for (; ; ) {
            int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public void setPuzzleListAdapter() {
        if (puzzleListAdapter != null)
            puzzleListAdapter = null;

        puzzleListAdapter = new PuzzleAdapter(this, piecesModelListMain);
        rv_puzzle.setHasFixedSize(true);
        rv_puzzle.setAdapter(puzzleListAdapter);

        puzzleListAdapter.notifyDataSetChanged();
    }

    static public class MyClickListener implements View.OnLongClickListener {

        // called when the item is long-clicked
        @Override
        public boolean onLongClick(View view) {
            // TODO Auto-generated method stub

            // create it from the object's tag
            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

            view.startDrag(data, //data to be dragged
                    shadowBuilder, //drag shadow
                    view, //local data about the drag and drop operation
                    0   //no needed flags
            );

            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    public class MyDragListener implements View.OnDragListener {

        ImageView imageView;

        public MyDragListener(ImageView imageView) {
            this.imageView = imageView;
        }


        @Override
        public boolean onDrag(View v, DragEvent event) {

            // Handles each of the expected events
            switch (event.getAction()) {

                //signal for the start of a drag and drop operation.
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;

                //the drag point has entered the bounding box of the View
                case DragEvent.ACTION_DRAG_ENTERED:
                    //v.setBackgroundResource(R.drawable.target_shape);    //change the shape of the view
                    break;

                //the user has moved the drag shadow outside the bounding box of the View
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackgroundResource(R.drawable.normal_shape);    //change the shape of the view back to normal
                    break;

                //drag shadow has been released,the drag point is within the bounding box of the View
                case DragEvent.ACTION_DROP:
                    //v is the dynamic grid imageView, we accept the drag item
                    //view is listView imageView the dragged item
                    if (v == imageView) {
                        View view = (View) event.getLocalState();

                        ViewGroup owner = (ViewGroup) v.getParent();
                        if (owner == relativeLayout) {
                            String selectedViewTag = view.getTag().toString();

                            Pieces piecesModel = piecesModelHashMap.get(v.getTag().toString());
                            String xy = piecesModel.getpX() + "," + piecesModel.getpY();

                            if (xy.equals(selectedViewTag)) {
                                ImageView imageView = (ImageView) v;
                                imageView.setImageBitmap(piecesModel.getOriginalResource());
                                piecesModelListMain.remove(piecesModel);
                                setPuzzleListAdapter();
                                piecesModel = null;
                                if (piecesModelListMain.size() == 0) {
                                    Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "The correct Puzzle", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                piecesModel = null;
                                view.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Not the correct Puzzle", Toast.LENGTH_LONG).show();
                                break;
                            }
                        } else {
                            View view1 = (View) event.getLocalState();
                            view1.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                            break;
                        }
                    } else if (v == scrollView) {
                        View view1 = (View) event.getLocalState();
                        view1.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    } else if (v == rv_puzzle) {
                        View view1 = (View) event.getLocalState();
                        view1.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You can't drop the image here", Toast.LENGTH_LONG).show();
                        break;
                    }
                    break;
                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundResource(R.drawable.normal_shape);	//go back to normal shape
                default:
                    break;
            }
            return true;
        }
    }

    void createQuest(int i){
        levels.get(i).setPlay(true);
        change.setText("Kesempatan: "+(changer));
        question.setText(quests.get(i).getQuestion());
        arrayLists.clear();
        arrayLists.add(getList(quests.get(i).getImgAnswer1(),quests.get(i).isCorrect1()));
        arrayLists.add(getList(quests.get(i).getImgAnswer2(),quests.get(i).isCorrect2()));
        arrayLists.add(getList(quests.get(i).getImgAnswer3(),quests.get(i).isCorrect3()));
        arrayLists.add(getList(quests.get(i).getImgAnswer4(),quests.get(i).isCorrect4()));
        arrayLists.add(getList(quests.get(i).getImgAnswer5(),quests.get(i).isCorrect5()));
        Collections.shuffle(arrayLists);
        layout.setVisibility(View.VISIBLE);
        int x=0;
        for (List<Object> list : arrayLists){
            Log.d("Practice: ","Image: "+ list.get(0)+" Boolean: "+list.get(1));
            Glide.with(this.getApplicationContext()).load(list.get(0)).into(list1.get(x));
            list1.get(x).setContentDescription(""+x);
            x++;
        }
        constraintLayout1.setVisibility(View.VISIBLE);
        bg_answer.setVisibility(View.VISIBLE);
    }

    @SafeVarargs
    public final <T> List<T> getList(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    private void initLevel(){
        Log.d("Practice: ", "Init");
        levels = new ArrayList<>();
        levels.add(new Level(R.drawable.tingkat_1,"Question",false));
        levels.add(new Level(R.drawable.tingkat_2,"Question",false));
        levels.add(new Level(R.drawable.tingkat_3,"Question",false));
        levels.add(new Level(R.drawable.tingkat_4,"Question",false));
        levels.add(new Level(R.drawable.tingkat_5,"Puzzle",false));
        levels.add(new Level(R.drawable.tingkat_6,"Puzzle",false));
        levels.add(new Level(R.drawable.tingkat_7,"Puzzle",false));
        levels.add(new Level(R.drawable.tingkat_8,"Puzzle",false));
        levels.add(new Level(R.drawable.tingkat_9,"Drawing",false));
        levels.add(new Level(R.drawable.tingkat_10,"Drawing",false));
    }

    private void initQuestion(){
        quests = new ArrayList<>();
        quests.add(new Quest("Media apa saja yang digunakan untuk membuat gambar sketsa ? (+4) (kalau benar semua)",R.drawable.kertas,true,R.drawable.pensil,true,R.drawable.rapido, true ,R.drawable.tipex,false,R.drawable.printer,false));
        quests.add(new Quest("Media apa saja yang digunakan untuk membuat gambar ilustrasi manual ? (+4) (kalau benar semua)",R.drawable.drawing_pen,true,R.drawable.kamera,false,R.drawable.komputer, false ,R.drawable.penggaris,true,R.drawable.pensil,true));
        quests.add(new Quest("Media apa saja yang digunakan untuk membuat gambar ilustrasi digital ? (+4) (kalau benar semua)",R.drawable.drawing_pad,true,R.drawable.fd,false,R.drawable.komputer, true ,R.drawable.usb,false,R.drawable.scan,true));
        quests.add(new Quest("Media apa saja yang digunakan untuk membuat gambar ilustrasi digital ? (+4) (kalau benar semua)",R.drawable.acrylic,true,R.drawable.water_color,true,R.drawable.lem, false ,R.drawable.pensil,true,R.drawable.scan,true));
        quests.add(new Quest("Susunlah gambar dibawah ini sehingga terlihat seperti gambar sketsa (+10)",R.drawable.puzzle1));
        quests.add(new Quest("Susunlah gambar dibawah ini sehingga terlihat seperti gambar sketsa (+10)",R.drawable.puzzle2));
        quests.add(new Quest("Susunlah gambar dibawah ini sehingga terlihat seperti gambar sketsa (+10)",R.drawable.puzzle3));
        quests.add(new Quest("Susunlah gambar dibawah ini sehingga terlihat seperti gambar sketsa (+10)",R.drawable.puzzle4));
    }

    @Override
    public void onBackPressed() {
        DialogSmall small = new DialogSmall("Keluar","Yakin inngin kembali ke halaman menu ?");
        FragmentManager fm = getSupportFragmentManager();
        small.show(fm,"Exit");
        fm.executePendingTransactions();
        small.small_btnOK.setOnClickListener(v ->{
            Intent intent = new Intent(PractectActivity.this,MenuActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @OnClick({R.id.answer1,R.id.answer2,R.id.answer3,R.id.answer4,R.id.answer5})
    void Answer(View view){
        switch (view.getId()){
            case R.id.answer1:
                if(imgCheck1.getVisibility()==View.INVISIBLE){
                    checking(answer1);
                    imgCheck1.setVisibility(View.VISIBLE);
                }else if(imgCheck1.getVisibility()==View.VISIBLE){
                    tempskor-=2;
                    imgCheck1.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.answer2:
                if(imgCheck2.getVisibility()==View.INVISIBLE){
                    checking(answer2);
                    imgCheck2.setVisibility(View.VISIBLE);
                }else if(imgCheck2.getVisibility()==View.VISIBLE){
                    tempskor-=2;
                    imgCheck2.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.answer3:
                if(imgCheck3.getVisibility()==View.INVISIBLE){
                    checking(answer3);
                    imgCheck3.setVisibility(View.VISIBLE);
                }else if(imgCheck3.getVisibility()==View.VISIBLE){
                    tempskor-=2;
                    imgCheck3.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.answer4:
                if(imgCheck4.getVisibility()==View.INVISIBLE){
                    checking(answer4);
                    imgCheck4.setVisibility(View.VISIBLE);
                }else if(imgCheck4.getVisibility()==View.VISIBLE){
                    tempskor-=2;
                    imgCheck4.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.answer5:
                if(imgCheck5.getVisibility()==View.INVISIBLE){
                    checking(answer5);
                    imgCheck5.setVisibility(View.VISIBLE);
                }else if(imgCheck5.getVisibility()==View.VISIBLE){
                    tempskor-=2;
                    imgCheck5.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    private void checking(ImageButton answer) {
        Log.d("Checking: ", "ContentDescription: "+answer.getContentDescription());
        int i=0;
        for(List<Object> list : arrayLists){
            if (answer.getContentDescription().equals(""+i)){
                Log.d("Checking: ", ""+i+" isCorrect"+list.get(1));
                tempskor+=2;
                break;
            }else if (answer.getContentDescription().equals(""+i)){
                Log.d("Checking: ", ""+i+" isCorrect"+list.get(1));
                tempskor+=2;
                break;
            }else if (answer.getContentDescription().equals(""+i)){
                Log.d("Checking: ", ""+i+" isCorrect"+list.get(1));
                tempskor+=2;
                break;
            }else if (answer.getContentDescription().equals(""+i)){
                Log.d("Checking: ", ""+i+" isCorrect"+list.get(1));
                tempskor+=2;
                break;
            }else if (answer.getContentDescription().equals(""+i)){
                Log.d("Checking: ", ""+i+" isCorrect"+list.get(1));
                tempskor+=2;
                break;
            }
            i++;
        }
    }

    @OnClick(R.id.select_next)
    void Next(){
        countDown.pause();
        int i =0;
        while (i<levels.size()){
            if(levels.get(i).isPlay()){
                i++;
                if(i==levels.size()){
                    DialogSmall dialogSmall = new DialogSmall("Selesai!","Skor keseluruhan kamu "+skor);
                    dialogSmall.show(getSupportFragmentManager(),"Timer");
                }
            }else{
                if(tempskor==6){
                    tempskor+=4;
                }
                skor+=tempskor;
                tempskor=0;
                score.setText("Skor: "+skor);
                imgCheck1.setVisibility(View.INVISIBLE);
                imgCheck2.setVisibility(View.INVISIBLE);
                imgCheck3.setVisibility(View.INVISIBLE);
                imgCheck4.setVisibility(View.INVISIBLE);
                imgCheck5.setVisibility(View.INVISIBLE);
                widthCheck=true;
                countGrid=0;
                CreateQuest(i);
                break;
            }
        }
    }

    @OnClick(R.id.selectLevel)
    void selectLevel(){
        if (!countDown.isPaused()) countDown.pause();
        Toast.makeText(getApplicationContext(),"Skor untuk level ini tidak dihitung",Toast.LENGTH_SHORT).show();
        levels.get(positionAdapter).setPlay(false);
        widthCheck=true;
        countGrid=0;
        dialogLevel();
    }
}
