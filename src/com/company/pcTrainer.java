package com.company;
import com.company.Utilities.Animation.AnimationFactory;

import java.util.*;

class pcTrainer extends Trainer {

    private ArrayList<BattleCommand> selectedMoves = new ArrayList<>();
    private MovesListUI movesListUI;
    private BattleController.SwapUI swapUI;
    private boolean canCancelSwap = true;
    private boolean waitingForSwap = false;
    private boolean hasSwapped = false;

    @Override
    public Boolean hasFinalizedCommands() {
        return selectedMoves.size()>0;//return true if we have selected a move
    }

    public void setCommand(Move m,Pokemon user){
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
        System.out.println("player turn start");
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
        if(getStagedPokemon() != pokeToSwapWith){
            setCommandToExecuteAtTurnEnd(new TrainerCommand(this, AnimationFactory.getPokeChangeAnim(),
                    ()-> {
                    updateSwapUI();
                    swapPokemon(pokeToSwapWith);
                    waitingForSwap = false;
                    System.out.println("swap success");
                }
            ));
            swapUI.toggle(false);
            selectedMoves.add(getCommandToExecuteBeforeTurnEnd());
        }else{
            System.out.println(pokeToSwapWith.name + " has already been sent out");

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
    public void endTurn() {
        System.out.println("player turn end");
        selectedMoves.clear();

        if (ownedSlot.getCurPokemon().isDead()){
            canCancelSwap = false;
            waitingForSwap = true;
            swapUI.toggle(true);
            System.out.println("player needs to swap");
        }
    }

    @Override
    public boolean canEndTurn() {
        return !waitingForSwap;
    }
}
