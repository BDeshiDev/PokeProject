package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Trainer {
    protected ArrayList<Pokemon> party = new ArrayList<>();
    protected int curPokeIndex;//index in party of the current pokemon
    protected Pokemon curPokemon;
    String name;

    protected Trainer(String _name,Pokemon[] pokemons) {
        curPokeIndex = 0;
        name = _name;
        for (Pokemon p:pokemons) {
            party.add(p);
        }

        curPokemon = pokemons[0];
    }

    public boolean canFight() {
        for (Pokemon p:party) {
            if(!p.isDead())
                return true;
        }
        return false;
    }

    public Pokemon getCurPokemon(){
        return  curPokemon;
    }

    private void setCurPokemon(int index){
        curPokeIndex = index;
        curPokemon = party.get(curPokeIndex);
    }

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
    }

    //abstract funcs go here
    public abstract Attack getCommand(Pokemon target);
    public abstract Boolean hasCommand();
    public abstract void prepTurn();

}
