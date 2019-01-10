package com.company.Utilities;
/*
* contains info for the result of a battle
* */
public class BattleResult {
    public int totalXp = 0;
    public boolean playerWon = false;

    public void reset(){
        totalXp = 0;
        playerWon = false;
    }
}
