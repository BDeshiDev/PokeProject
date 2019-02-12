package com.company;

import com.company.networking.TrainerData;
import pokemap.PlayerEntity;
import pokemap.Position;

/*
* Class for holding saveData
* */
public class SaveData {
    private int score;
    public Position position = null;
    public pcTrainer pcTrainer;
    public  String mapName;


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

    public void updateSaveLocally(Position p , pcTrainer pct){
        position=p;
        pcTrainer =pct;
    }

    public static SaveData newGameData(){
        return new SaveData(null, new pcTrainer("Ash",PokemonFactory.getCharizard().toPokemon(),
                PokemonFactory.getBlastoise().toPokemon(),PokemonFactory.getVenasaur().toPokemon()),"src/pokemap/ForestMap.txt");
    }

}
