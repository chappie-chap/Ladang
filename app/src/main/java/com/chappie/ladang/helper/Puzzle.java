package com.chappie.ladang.helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import com.chappie.ladang.helper.cutter.CutMap;
import com.chappie.ladang.helper.cutter.ImageCutter;
import com.chappie.ladang.model.PuzzlePiece;

import java.io.File;
import java.util.ArrayList;


public class Puzzle {

    private Activity mActivity;
    private Bitmap mSourceImage = null;
    private ArrayList<PuzzlePiece> puzzlePieceArrayList ;


    public ArrayList<PuzzlePiece> createPuzzlePieces(Activity aActivity, Bitmap aBitmap, int width, int height,
                                                     ImageView imageView, String path, int horizontalResolution, int verticalResolution, int imagePuzzle) {
        this.mActivity = aActivity;
        this.puzzlePieceArrayList = new ArrayList<>();
        getDisplaySize(aBitmap, width, height, imageView,imagePuzzle);
        deleteDirectories(path);
        CutMap cutMap = new CutMap(horizontalResolution, verticalResolution);
        ImageCutter imageCutter = new ImageCutter(mSourceImage, cutMap);
        drawOrderedPuzzlePieces(imageCutter, cutMap);
        mSourceImage = null;
        return puzzlePieceArrayList;
    }

    private void getDisplaySize(Bitmap bitmap, int widthFinal, int heightFinal, ImageView imageView, int imagePuzzle) {
        Bitmap image = null;
        try {
            image = BitmapFactory.decodeResource(mActivity.getResources(), imagePuzzle);
            mSourceImage = Bitmap.createScaledBitmap(image, widthFinal, heightFinal, false);
        } catch (OutOfMemoryError ex) {
            ex.printStackTrace();
        } finally {
            image = null;
        }
        mActivity.runOnUiThread(() -> imageView.setImageBitmap(mSourceImage));
    }

    private void deleteDirectories(String aPath) {
        File dir = new File(Environment.getExternalStorageDirectory().toString() + aPath);
        if (dir.exists() && dir.isDirectory()) {
            dir.delete();
            dir.mkdir();
        } else {
            dir.mkdir();
        }
    }

    private void drawOrderedPuzzlePieces(ImageCutter imageCutter, CutMap cutMap) {
        PuzzlePiece[][] puzzlePieces = imageCutter.cutImage();
        for (int i = 0; i < cutMap.getHorizontalResolution(); i++) {
            for (int j = 0; j < cutMap.getVerticalResolution(); j++) {
                PuzzlePiece piece = puzzlePieces[i][j];
                puzzlePieceArrayList.add(piece);
            }
        }
    }

}
