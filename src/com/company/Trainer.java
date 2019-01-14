package com.company;

import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;

import java.util.ArrayList;

public abstract class Trainer implements Battler {
    protected ArrayList<Pokemon> party = new ArrayList<>();
    private Pokemon stagedPokemon = null;
    protected  BattleSlot ownedSlot;
    protected BattleSlot enemySlot;
    private BattleCommand commandToExecuteAtTurnEnd =null;
    public final String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void heal(){
        for (Pokemon p :party) {
            p.heal();
        }
    }

    @Override
    public int getDefeatXp(){
        int retVal =0;
        for (Pokemon p :party) {
            retVal +=p.getDefeatXp();
        }
        return  retVal;

    }

    protected Trainer(String _name,Pokemon[] pokemons) {
        name = _name;
        for (Pokemon p:pokemons) {
            party.add(p);
        }
    }
    @Override
    public  Pokemon getStagedPokemon(){
        return  stagedPokemon;
    }

    @Override
    public boolean canFight() {
        for (Pokemon p:party) {
            if(!p.isDead())
                return true;
        }
        return false;
    }


    public void swapPokemon(Pokemon pokemonToSwapWith ){
        if(pokemonToSwapWith == null)
            Debugger.out("swap failed");
        else{
            stagedPokemon = pokemonToSwapWith;//no need to add the previous staged pokemon to party again
            ownedSlot.setPokemon(stagedPokemon,true);
        }
    }

    public Pokemon getFirstAvailablePokemon(){//get first not dead pokemon that's not already sent out or return null,
        for (Pokemon p :party) {
            if(!p.isDead() && stagedPokemon != p) {
                return p;
            }
        }
        return  null;
    }

    public Pokemon stageFirstAvailablePokemon(){// used for getting pokemon to send out first in battle//also stages the mon
        Pokemon p = getFirstAvailablePokemon();
        stagedPokemon = p;
        Debugger.out(name+"sending poke "+ p.name);
        return stagedPokemon;
    }

    @Override
    public void prepareForBattle(BattleSlot ownedSlot, BattleSlot enemySlot){
        this.ownedSlot = ownedSlot;
        this.enemySlot = enemySlot;

        ownedSlot.setPokemon(stageFirstAvailablePokemon(),true);
    }

    @Override
    public void endBattle(){
        stagedPokemon = null;
        ownedSlot = null;
        enemySlot = null;
    }
    @Override
    public void prepTurn(){
        commandToExecuteAtTurnEnd = null;
    }
    @Override
    public boolean hasCommandBeforeTurnEnd(){
        return commandToExecuteAtTurnEnd != null;
    }
    @Override
    public  BattleCommand getCommandToExecuteBeforeTurnEnd(){
        return  commandToExecuteAtTurnEnd;
    }

    public void setCommandToExecuteAtTurnEnd(BattleCommand commandToExecuteAtTurnEnd) {
        this.commandToExecuteAtTurnEnd = commandToExecuteAtTurnEnd;
    }

}
