package com.company;

import com.company.Pokemon.Pokemon;

import java.util.ArrayList;
import java.util.List;

/*
* contains info for the result of a battle
* */
public class BattleResult {
    public int totalXp = 0;
    public boolean playerWon = false;
    public List<Pokemon> caughtMons = new ArrayList<>();

    public void reset(){
        totalXp = 0;
        playerWon = false;
        caughtMons.clear();
    }

    public void addCaughtMon(Pokemon newlyCaughtMon){
        caughtMons.add(newlyCaughtMon);
    }

    @Override
    public String toString() {
        return "BattleResult{" +
                "totalXp=" + totalXp +
                ", playerWon=" + playerWon +
                '}';
    }
}
