package com.company;

import com.company.Utilities.Animation.AnimationFactory;

import java.util.ArrayList;
import java.util.Random;

public class aiTrainer extends Trainer {

    Random rand;
    @Override
    public ArrayList<BattleCommand> getCommands() {
        ArrayList<BattleCommand> commandsList = new ArrayList<>();

        Pokemon pokeInSlot = ownedSlot.getCurPokemon();
        BattleCommand newCommmand =new AttackCommand(pokeInSlot,pokeInSlot.getRandomMove(rand),enemySlot);
        commandsList.add(newCommmand);

        return  commandsList;
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
    public void endTurn() {
        if(ownedSlot.getCurPokemon().isDead()){
            setCommandToExecuteAtTurnEnd(new TrainerCommand(this,
                    AnimationFactory.getPokeChangeAnim()
                    ,()->{
                Pokemon newlyStagedMon = stageFirstAvailablePokemon();
                ownedSlot.setPokemon(newlyStagedMon);

            }));
        }
        System.out.println("ai turn end");
    }


    @Override
    public boolean canEndTurn() {
        return !hasCommandBeforeTurnEnd();
    }

}
