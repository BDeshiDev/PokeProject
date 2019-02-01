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

    public static SaveData newGameData(){
        return new SaveData(new Position(36,36), new pcTrainer("Ash",PokemonFactory.getCharizard().toPokemon(),
                PokemonFactory.getBlastoise().toPokemon(),PokemonFactory.getVenasaur().toPokemon()),"C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\pokemap\\ForestMap.txt");
    }

}
