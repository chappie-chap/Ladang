package com.chappie.ladang.fragment;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chappie.ladang.MenuActivity;
import com.chappie.ladang.R;
import com.chappie.ladang.adapter.DialogSmall;
import com.chappie.ladang.adapter.puzzle.PuzzleAdapter;
import com.chappie.ladang.helper.CountDownTimerPausable;
import com.chappie.ladang.helper.Puzzle;
import com.chappie.ladang.model.Level;
import com.chappie.ladang.model.Pieces;
import com.chappie.ladang.model.PuzzlePiece;
import com.chappie.ladang.model.Quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PuzzleFragment extends Fragment {

    @BindView(R.id.practice_timer)
    TextView tvTimer;
    @BindView(R.id.practice_change)
    TextView change;
    @BindView(R.id.practice_score)
    TextView tvScore;
    @BindView(R.id.practice_question)
    TextView question;
    @BindView(R.id.puzzle_btnNext)
    ImageButton nextLevel;
    @BindView(R.id.img_puzzle)
    ImageView imgPuzzle;
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
    private boolean widthCheck = true;
    private int countGrid = 0, widthFinal, heightFinal;
    private List<Level> levels;
    private List<Quest> quests;
    private int changer, score, tempScore = 0, position;
    private long millis;
    private Context context;
    private CountDownTimerPausable countDown;
    private FragmentCallBacks fragmentCallBacks;
    private boolean isGameOver;

    private boolean isGameOver() {
        return isGameOver;
    }

    private void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public interface FragmentCallBacks {
        void CallBackQuest(List<Level> levels, int changer, int score, long millis);
    }

    public void setFragmentCallBacks(FragmentCallBacks fragmentCallBacks) {
        this.fragmentCallBacks = fragmentCallBacks;
    }

    public PuzzleFragment() {
        // Required empty public constructor
    }

    public PuzzleFragment(List<Level> levels, List<Quest> quests, long millis, int changer, int score, int position) {
        this.levels = levels;
        this.quests = quests;
        this.millis = millis;
        this.changer = changer;
        this.score = score;
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_puzzle, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        context = view.getContext();

        createPuzzle(position);
        tvScore.setText("Skor: " + score);
        countDown = new CountDownTimerPausable(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millis = millisUntilFinished;
                tvTimer.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                if (changer == 0) {
                    DialogSmall dialogSmall = new DialogSmall("Game Over", "Kesempatan kamu habis!");
                    dialogSmall.small_close.setVisibility(View.INVISIBLE);
                    FragmentManager fm = getChildFragmentManager();
                    dialogSmall.show(fm,"Game Ovew");
                    fm.executePendingTransactions();
                    dialogSmall.small_btnOK.setOnClickListener(v ->{
                        Intent intent = new Intent(getActivity(), MenuActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    });
                } else if (changer > 0) {
                    countDown.pause();
                    changer -= 1;
                    change.setText("Kesempatan: " + changer);
                    DialogSmall dialogSmall = new DialogSmall("Waktu Habis", "Kesempatan kamu tersisa " + changer);
                    FragmentManager fm = getChildFragmentManager();
                    dialogSmall.show(fm, "Timer");
                    fm.executePendingTransactions();
                    dialogSmall.small_close.setVisibility(View.INVISIBLE);
                    dialogSmall.small_btnOK.setOnClickListener(v -> {
                        dialogSmall.dismiss();
                        countDown.restart(300000,1000);
                    });
                }
            }
        }.start();
    }

    @SuppressLint("SetTextI18n")
    private void createPuzzle(int i) {
        change.setText("Kesempatan: "+(changer));
        question.setText(quests.get(i).getQuestion());
        scrollView.setOnDragListener(new MyDragListener(null));
        relativeLayout.setOnDragListener(new MyDragListener(null));
        rv_puzzle.setOnDragListener(new MyDragListener(null));
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_puzzle.setLayoutManager(linearLayoutManager);
        puzzle = new Puzzle();
        puzzlePiecesList.clear();
        piecesModelListMain.clear();
        piecesModelHashMap.clear();

        ViewTreeObserver obs = scrollView.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(() -> {
            if (widthCheck) {
                Log.d("Practice: ", "in WidthCheck");
                widthFinal = scrollView.getWidth();
                heightFinal = scrollView.getHeight();
                puzzlePiecesList = puzzle.createPuzzlePieces(getActivity(), null, widthFinal, heightFinal, imgPuzzle, "/puzzles/", 3, 3, quests.get(i).getImgPuzzle());
                getAdapter();
                widthCheck = false;
            }
        });
        setPuzzleListAdapter();
    }

    private void getAdapter() {
        RelativeLayout.LayoutParams params;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                PuzzlePiece piece = puzzlePiecesList.get(countGrid);
                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                int dimX = piece.getAnchorPoint().x - piece.getCenterPoint().x;
                int dimY = piece.getAnchorPoint().y - piece.getCenterPoint().y;

                params.setMargins(dimX, dimY, 0, 0);
                ImageView button2 = new ImageView(context);
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

    private int generateViewId() {
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

    private void setPuzzleListAdapter() {
        if (puzzleListAdapter != null)
            puzzleListAdapter = null;

        puzzleListAdapter = new PuzzleAdapter(context, piecesModelListMain);
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

        MyDragListener(ImageView imageView) {
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
                                    Toast.makeText(context, "GAME OVER", Toast.LENGTH_LONG).show();
                                    setGameOver(true);
                                } else {
                                    Toast.makeText(context, "The correct Puzzle", Toast.LENGTH_LONG).show();
                                    setGameOver(false);
                                }
                            } else {
                                piecesModel = null;
                                view.setVisibility(View.VISIBLE);
                                Toast.makeText(context, "Not the correct Puzzle", Toast.LENGTH_LONG).show();
                                setGameOver(false);
                                break;
                            }
                        } else {
                            View view1 = (View) event.getLocalState();
                            view1.setVisibility(View.VISIBLE);
                            Toast.makeText(context, "You can't drop the image here", Toast.LENGTH_LONG).show();
                            setGameOver(false);
                            break;
                        }
                    } else if (v == scrollView) {
                        View view1 = (View) event.getLocalState();
                        view1.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "You can't drop the image here", Toast.LENGTH_LONG).show();
                        setGameOver(false);
                        break;
                    } else if (v == rv_puzzle) {
                        View view1 = (View) event.getLocalState();
                        view1.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "You can't drop the image here", Toast.LENGTH_LONG).show();
                        setGameOver(false);
                        break;
                    } else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "You can't drop the image here", Toast.LENGTH_LONG).show();
                        setGameOver(false);
                        break;
                    }
                    break;
                //the drag and drop operation has concluded.
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundResource(R.id.normal_resource);	//go back to normal shape
                default:
                    break;
            }
            return true;
        }
    }

    @OnClick(R.id.puzzle_btnNext)
    void NextLevel(){
        if (position == levels.size() - 1 && !levels.get(position).isCanPlay()) {
            DialogSmall dialogSmall = new DialogSmall("Selesai!", "Skor keseluruhan kamu " + score);
            dialogSmall.show(getChildFragmentManager(), "Timer");
        } else if (levels.get(position).isCanPlay()&&isGameOver()) {
            tempScore+=10;
            countDown.pause();
            score += tempScore;
            levels.get(position).setCanPlay(false);
            levels.get(position + 1).setCanPlay(true);
            levels.get(position).setPlayed(true);
                /*tempScore=0;
                tvScore.setText("Skor: "+score);*/
            if (fragmentCallBacks != null) {
                fragmentCallBacks.CallBackQuest(levels, changer, score, millis);
            }
        }else{
            if (fragmentCallBacks != null) {
                fragmentCallBacks.CallBackQuest(levels, changer, score, millis);
            }
        }
    }
}
