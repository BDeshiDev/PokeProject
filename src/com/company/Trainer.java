package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Trainer {
    protected ArrayList<Pokemon> party = new ArrayList<>();
    private Pokemon stagedPokemon = null;
    protected  BattleSlot ownedSlot;
    protected BattleSlot enemySlot;
    String name;

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

    public boolean canSwap(){
        for (Pokemon p:party) {
            if(!p.isDead() && p != getStagedPokemon())
                return  true;
            else{
                System.out.println(p.name + " is fainted and can't battle now");
            }
        }
        return  false;
    }
    public void swapPokemon(){
        System.out.println(getStagedPokemon().name + " was recalled");
        Pokemon pokemonToSwapWith = sendOutFirstAvailablePokemon();
        swapPokemon(pokemonToSwapWith);
    }

    public void swapPokemon(Pokemon pokemonToSwapWith ){
        if(pokemonToSwapWith == null)
            System.out.println("swap failed");
        else{
            stagedPokemon = pokemonToSwapWith;//no need to add the previous staged pokemon to party again
            ownedSlot.setPokemon(stagedPokemon);
        }
    }

    public Pokemon sendOutFirstAvailablePokemon(){//get first not dead pokemon that's not already sent out or return null,
        for (Pokemon p :party) {
            if(!p.isDead() && stagedPokemon != p) {
                return p;
            }
        }
        return  null;
    }

    public Pokemon stageFirstAvailablePokemon(){// used for getting pokemon to send out first in battle//also stages the mon
        Pokemon p = sendOutFirstAvailablePokemon();
        stagedPokemon = p;
        System.out.println(name+"sending poke "+ p.name);
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


    //abstract funcs go here
    public abstract ArrayList<Attack> getCommands();
    public abstract Boolean hasFinalizedCommands();
    public abstract void prepTurn();
}
