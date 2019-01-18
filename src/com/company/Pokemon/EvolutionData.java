package com.company.Pokemon;

import com.company.Pokemon.Stats.Level;

public class EvolutionData {
    public final PokemonData monToEvolveTo;
    public final int lvRequirement;

    public EvolutionData(){
        this(null,999);//the level is pointless here
    }
    public EvolutionData(PokemonData monToEvolveTo, int lvRequirement) {
        this.monToEvolveTo = monToEvolveTo;
        this.lvRequirement = lvRequirement;
    }
    // to make it more clear that a pokemon can;t evolve further if this is used
    public static final EvolutionData finalEvo = new EvolutionData(null,999);

    boolean canEvolve(Level levelToCheck){
        return monToEvolveTo != null && levelToCheck.getCurLevel() >= lvRequirement ;
    }
}
