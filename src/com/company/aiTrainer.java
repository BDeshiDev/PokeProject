package com.company;

import com.company.Pokemon.Pokemon;
import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Debug.Debugger;
import java.util.ArrayList;
import java.util.Random;
/*
* Computer controlled trainer class that behaves like an idiot
* */
public class aiTrainer extends Trainer {

    Random rand;//I don't actually know if keeping the same rand has any side effects
    @Override
    public BattleCommand getCommand() {

        Pokemon pokeInSlot = ownedSlot.getCurPokemon();
        return new AttackCommand(pokeInSlot,pokeInSlot.getRandomMove(rand),enemySlot);
    }


    @Override
    public void prepTurn() {
        super.prepTurn();
    }

    public aiTrainer(String _name, Pokemon... pokemons) {
        super(_name, pokemons);
        rand = new Random();
    }

    @Override
    public Boolean hasFinalizedCommands() {
        return true;//always true since our ai doesn't need to think
    }

    @Override
    public void endTurnPrep() {
        setCommandToExecuteAtTurnEnd(null);
        if(ownedSlot.getCurPokemon().isDead()){
            setCommandToExecuteAtTurnEnd(new TrainerCommand(this,
                    AnimationFactory.getPokeChangeAnim(),"swap",
                    ()->{
                Pokemon newlyStagedMon = stageFirstAvailablePokemon();
                ownedSlot.setPokemon(newlyStagedMon,false);

            },name+" :" +
                    (ownedSlot.isEmpty()?"":"Come back, "+ ownedSlot.getCurPokemon().name +"."),
                    "Go ! " + getFirstAvailablePokemon().name+ "!!!"));
        }
        Debugger.out("AI turn end");
    }


    @Override
    public boolean canEndTurn() {
        return !hasCommandBeforeTurnEnd();
    }

}
