package com.company.networking;

import com.company.Pokemon.PokemonSaveData;
import com.company.Pokemon.Stats.Level;

public class TrainerData {
    public String name;
    public PokemonSaveData[] pokemonSaves;

    public TrainerData(String name,PokemonSaveData... pokemonName) {
        this.pokemonSaves = pokemonName;
        this.name = name;
    }

    public TrainerData(String name,String... pokemonNames) {
        pokemonSaves = new PokemonSaveData[pokemonNames.length];
        for (int i = 0 ; i < pokemonNames.length ; i++) {
            pokemonSaves[i] = new PokemonSaveData(pokemonNames[i], Level.maxLevel);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
