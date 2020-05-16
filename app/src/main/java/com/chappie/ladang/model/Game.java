package com.chappie.ladang.model;

public class Game {

    private String Name;
    private int Point;
    private String Role;
    private String SecretWord;
    private boolean isEliminate;
    private boolean isReady;
    private int jumlah;

    public Game(String name, String role, String secretWord, boolean isEliminate, boolean isReady, int point) {
        Name = name;
        Point = point;
        Role = role;
        SecretWord = secretWord;
        this.isEliminate = isEliminate;
        this.isReady = isReady;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getSecretWord() {
        return SecretWord;
    }

    public void setSecretWord(String secretWord) {
        SecretWord = secretWord;
    }

    public boolean isEliminate() {
        return isEliminate;
    }

    public void setEliminate(boolean eliminate) {
        isEliminate = eliminate;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
