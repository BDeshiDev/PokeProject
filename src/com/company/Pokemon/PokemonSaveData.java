package com.company.Pokemon;

import com.company.PokemonFactory;

public class PokemonSaveData {
    public final String name;
    public final int level;

    public PokemonSaveData(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public PokemonSaveData(Pokemon p) {
        this(p.name,p.stats.level.getCurLevel());
    }

    public Pokemon toPokemon(){
        return PokemonFactory.getMonByName(name,level);
    }
}
