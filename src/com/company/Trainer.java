package com.company;

import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;

import java.util.ArrayList;

public abstract class Trainer {
    protected ArrayList<Pokemon> party = new ArrayList<>();
    private Pokemon stagedPokemon = null;
    protected  BattleSlot ownedSlot;
    protected BattleSlot enemySlot;
    private BattleCommand commandToExecuteAtTurnEnd =null;
    public final String name;

    public void heal(){
        for (Pokemon p :party) {
            p.heal();
        }
    }

    protected Trainer(String _name,Pokemon[] pokemons) {
        name = _name;
        for (Pokemon p:pokemons) {
            party.add(p);
        }
    }
    public  Pokemon getStagedPokemon(){
        return  stagedPokemon;
    }

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
            ownedSlot.setPokemon(stagedPokemon);
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

    public void prepareForBattle(BattleSlot ownedSlot,BattleSlot enemySlot){
        this.ownedSlot = ownedSlot;
        this.enemySlot = enemySlot;

        ownedSlot.setPokemon(stageFirstAvailablePokemon());
    }

    public void endBattle(){
        stagedPokemon = null;
        ownedSlot = null;
        enemySlot = null;
    }
    public void prepTurn(){
        commandToExecuteAtTurnEnd = null;
    }
    public boolean hasCommandBeforeTurnEnd(){
        return commandToExecuteAtTurnEnd != null;
    }
    public  BattleCommand getCommandToExecuteBeforeTurnEnd(){
        return  commandToExecuteAtTurnEnd;
    }

    public void setCommandToExecuteAtTurnEnd(BattleCommand commandToExecuteAtTurnEnd) {
        this.commandToExecuteAtTurnEnd = commandToExecuteAtTurnEnd;
    }

    //abstract funcs go here
    public abstract ArrayList<BattleCommand> getCommands();
    public abstract Boolean hasFinalizedCommands();
    public abstract void endTurnPrep();
    public abstract boolean canEndTurn();
}
