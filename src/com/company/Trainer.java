package com.company;

import java.util.ArrayList;
import java.util.Scanner;

class Trainer{
    private  ArrayList<Pokemon> party = new ArrayList<>();
    private int curPokeIndex;//index in party of the current pokemon
    private Pokemon curPokemon;
    String name;

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

    public Trainer(String _name, Pokemon... pokemons){//... used for quickness,use list or something better
        name = _name;
        curPokeIndex = 0;
        curPokemon = null;
        for (Pokemon p:pokemons) {
            party.add(p);
        }
        if(party.size() > 0)
            curPokemon = party.get(curPokeIndex);
    }

    //needs a better name
    public Attack takeTurn(Scanner cin, Pokemon target){//returns the attack chosen this turn
        if(curPokemon.isDead())
            curPokemon = swapPokemon();

        curPokemon.printTurn();
        System.out.println("enter command: ");
        int command=cin.nextInt();

        return curPokemon.receiveCommand(command,target);
    }
}
