package com.company;

import com.company.Pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Random;

public class WildMon implements  Battler{

    private Pokemon fighter;
    private Random rand;
    private String name;

    BattleSlot ownedSlot;
    BattleSlot targetSlot;

    @Override
    public String getName() {
        return name;
    }

    public WildMon( Pokemon fighter) {
        this.fighter = fighter;
        this.name = "The wild " + fighter.name;
        rand = new Random();
    }

    @Override
    public void heal() {
        fighter.heal();
    }

    @Override
    public int getDefeatXp() {
        return fighter.getDefeatXp();
    }

    @Override
    public Pokemon getStagedPokemon() {
        return fighter;
    }

    @Override
    public boolean canFight() {
        return !fighter.isDead();
    }

    @Override
    public void prepareForBattle(BattleSlot ownedSlot, BattleSlot enemySlot) {
        this.ownedSlot =ownedSlot;
        this.targetSlot = enemySlot;

        ownedSlot.setPokemon(fighter,false);
    }

    @Override
    public void endBattle() {

    }

    @Override
    public void prepTurn() {

    }

    @Override
    public void onCommandAccepted() {

    }

    @Override
    public boolean hasCommandBeforeTurnEnd() {
        return false;
    }

    @Override
    public BattleCommand getCommandToExecuteBeforeTurnEnd() {
        System.out.println("Warning! attempting to get trainer command from wild monster");
        return null;
    }

    @Override
    public BattleCommand getCommand() {
       return new AttackCommand(fighter,fighter.getRandomMove(rand),targetSlot);
    }

    @Override
    public Boolean hasFinalizedCommands() {
        return true;
    }

    @Override
    public void endTurnPrep() {
        //do nothing
    }

    @Override
    public boolean canEndTurn() {
        return true;
    }
}
