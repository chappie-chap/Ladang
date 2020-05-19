package com.chappie.ladang.model;

public class Level {
    private int imgLevel;
    private String type;
    private boolean isCanPlay;
    private boolean isPlayed;

    public Level(int imgLevel, String type, boolean isCanPlay, boolean isPlayed) {
        this.imgLevel = imgLevel;
        this.type = type;
        this.isCanPlay = isCanPlay;
        this.isPlayed = isPlayed;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public boolean isCanPlay() {
        return isCanPlay;
    }

    public void setCanPlay(boolean play) {
        isCanPlay = play;
    }

    public int getImgLevel() {
        return imgLevel;
    }

    public String getType() {
        return type;
    }
}
