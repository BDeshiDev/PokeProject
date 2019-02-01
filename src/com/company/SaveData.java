package com.company;

import com.company.networking.TrainerData;
import pokemap.PlayerEntity;
import pokemap.Position;

/*
* Class for holding saveData
* */
public class SaveData {
    private int score;
    public Position position;
    public pcTrainer pcTrainer;
    public final  String mapName;


    public SaveData(Position position, pcTrainer pcTrainer, String mapName) {
        this.position = position;
        this.pcTrainer = pcTrainer;
        this.mapName = mapName;
    }

    public int getScore() {
        return score;
    }
    public  void increaseScore(int amount){
        score+= amount;
    }

}
