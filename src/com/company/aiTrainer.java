package com.company;

import com.company.Utilities.Animation.AnimationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class aiTrainer extends Trainer {

    Random rand;
    @Override
    public ArrayList<Attack> getCommands() {
        ArrayList<Attack> commandsList = new ArrayList<>();

        Pokemon pokeInSlot = ownedSlot.getCurPokemon();
        Attack newCommmand =new Attack(pokeInSlot,pokeInSlot.getRandomMove(rand),enemySlot);
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
            setCommandToExecuteAtTurnEnd(new AnimatedCallBack(
                    AnimationFactory.getSlashAnimation().toSingleLoop(ownedSlot.getAnimationViewer())
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
