package com.company;

import com.company.Pokemon.Pokemon;
import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Debug.Debugger;
import com.company.networking.TrainerData;

import java.util.ArrayList;
import java.util.Random;
/*
* Computer controlled trainer class that behaves like an idiot
* */
public class aiTrainer extends Trainer {
    BattleCommand turnEndCommand;
    @Override
    public void prepTurn() {
        turnEndCommand = null;
    }

    @Override
    public void onCommandAccepted() {

    }

    @Override
    public boolean hasCommandBeforeTurnEnd() {
        return turnEndCommand != null;
    }

    @Override
    public BattleCommand getCommandToExecuteBeforeTurnEnd() {
        return turnEndCommand;
    }

    Random rand;
    @Override
    public BattleCommand getCommand() {

        Pokemon pokeInSlot = ownedSlot.getCurPokemon();
        return new AttackCommand(pokeInSlot,pokeInSlot.getRandomMove(rand),enemySlot);
    }


    public aiTrainer(String _name, Pokemon... pokemons) {
        super(_name, pokemons);
        rand = new Random();
    }

    public aiTrainer(TrainerData td) {
        super(td);
        this.rand = new Random();
    }

    @Override
    public Boolean hasFinalizedCommands() {
        return true;//always true since our ai doesn't need to think
    }

    @Override
    public void endTurnPrep() {
        turnEndCommand = null;
        if(ownedSlot.getCurPokemon().isDead()){
            turnEndCommand = new SwapCommand(this,getFirstAvailableMonIndex());
        }
        Debugger.out("AI turn end");
    }


    @Override
    public boolean canEndTurn() {
        return !hasCommandBeforeTurnEnd();
    }

}
