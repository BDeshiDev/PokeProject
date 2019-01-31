package com.company;

import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;
import com.company.networking.TrainerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public abstract class Trainer implements Battler {
    protected ObservableList<Pokemon> party = FXCollections.observableArrayList();
    private Pokemon stagedPokemon = null;
    protected  BattleSlot ownedSlot;
    protected BattleSlot enemySlot;
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

    public ObservableList<Pokemon> getParty() {
        return party;
    }

    protected Trainer(String _name, Pokemon[] pokemons) {
        name = _name;
        for (Pokemon p:pokemons) {
            party.add(p);
        }
    }
    protected Trainer(TrainerData td) {
        name = td.name;
        for (String pokeName:td.pokemonName) {
            Pokemon p = PokemonFactory.getMonByName(pokeName);
            if(p!= null)
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


    protected void swapPokemon(Pokemon pokemonToSwapWith ){
        if(pokemonToSwapWith == null)
            Debugger.out("swap failed because null");
        else{
            stagedPokemon = pokemonToSwapWith;//no need to add the previous staged pokemon to party again
            ownedSlot.setPokemon(stagedPokemon,true);
        }
    }

    public void swapPokemon(int swapIndex){
        swapPokemon(party.get(swapIndex));
    }

    public Pokemon getFirstAvailablePokemon(){//get first not dead pokemon that's not already sent out or return null,
        int monIndex =getFirstAvailableMonIndex();
        return  monIndex< 0?null:party.get(monIndex);
    }

    public int getFirstAvailableMonIndex(){
        for (int i = 0 ; i < party.size();i++) {
            Pokemon p =party.get(i);
            if(!p.isDead() && stagedPokemon != p) {
                return i;
            }
        }
        return  -1;
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



}
