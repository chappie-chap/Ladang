package com.chappie.ladang.model;

public class Level {
    private int imgLevel;
    private String type;
    private boolean isPlay;

    public Level(int imgLevel, String type, boolean isPlay) {
        this.imgLevel = imgLevel;
        this.type = type;
        this.isPlay = isPlay;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public int getImgLevel() {
        return imgLevel;
    }

    public String getType() {
        return type;
    }
}
