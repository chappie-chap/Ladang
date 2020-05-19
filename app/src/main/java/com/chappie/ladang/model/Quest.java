package com.chappie.ladang.model;

import java.util.List;

public class Quest {
    private String question;
    private int imgAnswer1;
    private boolean isCorrect1;
    private int imgAnswer2;
    private boolean isCorrect2;
    private int imgAnswer3;
    private boolean isCorrect3;
    private int imgAnswer4;
    private boolean isCorrect4;
    private int imgAnswer5;
    private boolean isCorrect5;
    private int imgPuzzle;

    public Quest(String question) {
        this.question = question;
    }

    public Quest(String question, int imgPuzzle) {
        this.question = question;
        this.imgPuzzle = imgPuzzle;
    }

    public Quest(String question, int imgAnswer1, boolean isCorrect1, int imgAnswer2, boolean isCorrect2, int imgAnswer3, boolean isCorrect3, int imgAnswer4, boolean isCorrect4, int imgAnswer5, boolean isCorrect5) {
        this.question = question;
        this.imgAnswer1 = imgAnswer1;
        this.isCorrect1 = isCorrect1;
        this.imgAnswer2 = imgAnswer2;
        this.isCorrect2 = isCorrect2;
        this.imgAnswer3 = imgAnswer3;
        this.isCorrect3 = isCorrect3;
        this.imgAnswer4 = imgAnswer4;
        this.isCorrect4 = isCorrect4;
        this.imgAnswer5 = imgAnswer5;
        this.isCorrect5 = isCorrect5;
    }

    public int getImgPuzzle() {
        return imgPuzzle;
    }

    public String getQuestion() {
        return question;
    }

    public int getImgAnswer1() {
        return imgAnswer1;
    }

    public boolean isCorrect1() {
        return isCorrect1;
    }

    public int getImgAnswer2() {
        return imgAnswer2;
    }

    public boolean isCorrect2() {
        return isCorrect2;
    }

    public int getImgAnswer3() {
        return imgAnswer3;
    }

    public boolean isCorrect3() {
        return isCorrect3;
    }

    public int getImgAnswer4() {
        return imgAnswer4;
    }

    public boolean isCorrect4() {
        return isCorrect4;
    }

    public int getImgAnswer5() {
        return imgAnswer5;
    }

    public boolean isCorrect5() {
        return isCorrect5;
    }
}
