package com.chappie.ladang.model;

public class Word {
    private boolean played;
    private String sWord1;
    private String sWord2;

    public Word(boolean played, String sWord1, String sWord2) {
        this.played = played;
        this.sWord1 = sWord1;
        this.sWord2 = sWord2;
    }

    public boolean isPlayed() {
        return played;
    }

    public String getsWord1() {
        return sWord1;
    }

    public String getsWord2() {
        return sWord2;
    }
}
