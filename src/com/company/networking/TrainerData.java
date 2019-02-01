package com.company.networking;

import java.util.Arrays;

public class TrainerData {
    public String name;
    public String[] pokemonName;

    public TrainerData(String name,String... pokemonName) {
        this.pokemonName = pokemonName;
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
