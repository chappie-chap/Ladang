package com.chappie.ladang.model;

public class Rule {
    private int img;
    private String title, description;

    public Rule(int img, String title, String description) {
        this.img = img;
        this.title = title;
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
