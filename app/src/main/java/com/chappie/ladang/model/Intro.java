package com.chappie.ladang.model;

public class Intro {
    private int imgbg;
    private String tv_intro;

    public Intro(int imgbg, String tv_intro) {
        this.imgbg = imgbg;
        this.tv_intro = tv_intro;
    }

    public int getImgbg() {
        return imgbg;
    }

    public void setImgbg(int imgbg) {
        this.imgbg = imgbg;
    }

    public String getTv_intro() {
        return tv_intro;
    }

    public void setTv_intro(String tv_intro) {
        this.tv_intro = tv_intro;
    }
}
