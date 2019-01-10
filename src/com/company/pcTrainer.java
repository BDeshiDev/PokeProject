package com.company;
import com.company.Pokemon.Move;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Debug.Debugger;

import java.util.*;

public class pcTrainer extends Trainer {

    private ArrayList<BattleCommand> selectedMoves = new ArrayList<>();
    private MovesListUI movesListUI;
    private BattleController.SwapUI swapUI;//#refactor the ui stuff shouldn't really be here
    private boolean canCancelSwap = true;
    private boolean waitingForSwap = false;
    private boolean hasSwapped = false;

    @Override
    public Boolean hasFinalizedCommands() {
        return selectedMoves.size()>0;//return true if we have selected a move
    }

    public void setCommand(Move m, Pokemon user){
        selectedMoves.add(new AttackCommand(user,m,enemySlot));
    }

    public void setMovesListUI(MovesListUI movesListUI) {
        this.movesListUI = movesListUI;
    }

    public void updateMoveUI(){
        movesListUI.load(getStagedPokemon(),this);
    }

    @Override
    public void prepTurn() {
        super.prepTurn();
        Debugger.out("player turn start");
        canCancelSwap = true;
        waitingForSwap = false;

        selectedMoves.clear();
    }

    public ArrayList<BattleCommand> getCommands() {
        return selectedMoves;
    }

    public pcTrainer(String _name, Pokemon... pokemons){
        super(_name,pokemons);//... used for quickness,use list or something better
    }


    @Override
    public void swapPokemon(Pokemon pokemonToSwapWith) {
        super.swapPokemon(pokemonToSwapWith);
        movesListUI.load(getStagedPokemon(),this);
    }

    public void tryToSwap(Pokemon pokeToSwapWith){
        Debugger.out("swapping between" +  ownedSlot.getCurPokemon().name + " and " + pokeToSwapWith.name);
        if(getStagedPokemon() != pokeToSwapWith && !pokeToSwapWith.isDead()){
            setCommandToExecuteAtTurnEnd(new TrainerCommand(this, AnimationFactory.getPokeChangeAnim(),"swap",
                    ()-> {
                    updateSwapUI();
                        Debugger.out("swap success " + ownedSlot.getCurPokemon().name + "," + getFirstAvailablePokemon().name);
                    swapPokemon(pokeToSwapWith);
                    waitingForSwap = false;
                }
            ,name+" :" +
                    (ownedSlot.isEmpty()?"":"Come back, "+ ownedSlot.getCurPokemon().name +"."),
                    "Go ! " + pokeToSwapWith.name+ "!!!"));
            swapUI.toggle(false);
            selectedMoves.add(getCommandToExecuteBeforeTurnEnd());
        }else{
            Debugger.out(pokeToSwapWith.name + " has already been sent out");
        }
    }

    public boolean canCancelSwap(){
        return canCancelSwap;
    }

    public void setSwapUI(BattleController.SwapUI swapUI) {
        this.swapUI = swapUI;
    }

    public void updateSwapUI(){
        swapUI.clear();
        for (Pokemon p : party) {
            swapUI.addPokemon(this,p);
        }
        swapUI.toggle(false);
    }

    @Override
    public void endBattle() {
        super.endBattle();
        swapUI = null;
        movesListUI = null;
    }

    @Override
    public void endTurnPrep() {
        Debugger.out("player turn end");
        setCommandToExecuteAtTurnEnd(null);
        selectedMoves.clear();

        if (ownedSlot.getCurPokemon().isDead()){
            canCancelSwap = false;
            waitingForSwap = true;
            swapUI.toggle(true);
            Debugger.out("player needs to swap");
        }
    }

    @Override
    public boolean canEndTurn() {
        return !waitingForSwap;
    }
}
