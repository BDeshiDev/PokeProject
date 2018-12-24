package com.company;

import java.util.Random;

public class aiTrainer extends Trainer {
    Random rand;
    @Override
    public Attack getCommand(Pokemon target) {
        return  new Attack(curPokemon,target,curPokemon.getRandomMove(rand));
    }

    @Override
    public void prepTurn() {
        //no need to do anything yet
    }

    public aiTrainer(String _name, Pokemon... pokemons) {
        super(_name, pokemons);
        rand = new Random();
    }

    @Override
    public Boolean hasCommand() {
        return true;//always true since our ai doesn't need to think
    }
}
