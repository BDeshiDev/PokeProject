package com.company;

import com.company.Pokemon.Pokemon;
import com.company.Utilities.Animation.AnimationData;
import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Debug.Debugger;
import com.company.networking.swapCommandData;

public class SwapCommand extends TrainerCommand{
    private int monIndexToSwapWith;
    public SwapCommand(Trainer swapper,int monIndexToSwapWith) {
        super(swapper, AnimationFactory.getPokeChangeAnim(), "Swapping", true,
                swapper.name+" :" + (swapper.ownedSlot.isEmpty()?"":"Come back, "+ swapper.ownedSlot.getCurPokemon().name +"."),
                "Go ! " + swapper.party.get(monIndexToSwapWith).name+ "!!!");
        this.monIndexToSwapWith=monIndexToSwapWith;
    }

    @Override
    public void callBack() {
        Pokemon pokeToSwapWith = commandUser.party.get(monIndexToSwapWith);
        Debugger.out("swapping between" +  commandUser.ownedSlot.getCurPokemon().name + " and " + pokeToSwapWith.name);
        commandUser.swapPokemon(monIndexToSwapWith);
    }

    @Override
    public String toJsonData() {
        return new swapCommandData(monIndexToSwapWith).toJsonData();
    }
}
