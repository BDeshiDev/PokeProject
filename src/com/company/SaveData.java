package com.company;
/*
* Class for holding saveData
* */
public class SaveData {
    private int score;
    public int getScore() {
        return score;
    }
    public  void increaseScore(int amount){
        score+= amount;
    }
    public Trainer player;
}
