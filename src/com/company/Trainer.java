package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Trainer {
    protected ArrayList<Pokemon> party = new ArrayList<>();
    private Pokemon stagedPokemon = null;
    protected  BattleSlot ownedSlot;
    protected BattleSlot enemySlot;
    BattleUIHolder ownedUI;
    String name;

    protected Trainer(String _name,Pokemon[] pokemons) {
        name = _name;
        for (Pokemon p:pokemons) {
            party.add(p);
        }
    }

    public void setOwnedSlots( BattleSlot newSlot){
        ownedSlot= newSlot;
    }

    public void setEnemySlots(BattleSlot enemySlot) {
        this.enemySlot = enemySlot;
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

    public Pokemon sendOutFirstAvailablePokemon(){//get first not dead pokemon that's not already sent out or return null,
        System.out.println("seinding first poke");
        for (Pokemon p :party) {
            if(!p.isDead() && stagedPokemon != p) {
                return p;
            }
        }
        return  null;
    }
    public Pokemon stageFirstAvailablePokemon(){// used for getting pokemon to send out first in battle//also stages the mon
        Pokemon p = sendOutFirstAvailablePokemon();
        System.out.println(name+" sending poke "+ p.name);
        stagedPokemon = p;
        return stagedPokemon;
    }

    public void endBattle(){
        stagedPokemon = null;
        ownedSlot = null;
        enemySlot = null;
        ownedUI = null;
    }

    /*
    public Pokemon swapPokemon(){//simply swaps with next pokemon
        return swapPokemon(curPokeIndex+1);
    }

    public Pokemon swapPokemon(int newIndex){
           newIndex = newIndex % party.size();
           if(!party.get(newIndex).isDead()){
               System.out.println(this.name+" recalled " + curPokemon.name +"!" );
               setCurPokemon(newIndex);
               System.out.println(this.name+" sent out " + curPokemon.name +"!" );
           }
           return curPokemon;
    }*/

    //abstract funcs go here
    public abstract ArrayList<Attack> getCommands();
    public abstract Boolean hasFinalizedCommands();
    public abstract void prepTurn();
}
