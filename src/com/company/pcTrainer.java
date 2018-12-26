package com.company;
import java.util.*;

class pcTrainer extends Trainer {

    private ArrayList<Attack> selectedMoves = new ArrayList<>();
    private MovesListUI movesListUI;
    private BattleController.SwapUI swapUI;
    private boolean canCancelSwap = true;

    @Override
    public Boolean hasFinalizedCommands() {
        return selectedMoves.size()>0;//return true if we have selected a move
    }

    public void setCommand(Move m,Pokemon user){
        selectedMoves.add(new Attack(user,m,enemySlot));
    }

    public void setMovesListUI(MovesListUI movesListUI) {
        this.movesListUI = movesListUI;
    }

    public void updateMoveUI(){
        movesListUI.load(getStagedPokemon(),this);
    }

    @Override
    public void prepTurn() {
        canCancelSwap = true;
        if (ownedSlot.getCurPokemon().isDead()){
            canCancelSwap = false;
            swapUI.toggle(true);
        }else if(ownedSlot.getCurPokemon().isDead()){
            swapPokemon();
        }
        selectedMoves.clear();
    }



    public ArrayList<Attack> getCommands() {
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
        if(canSwap()){
            if(getStagedPokemon() != pokeToSwapWith){
                swapPokemon(pokeToSwapWith);
                updateSwapUI();
            }else{
                System.out.println(pokeToSwapWith.name + " has already been sent out");
            }
        }else{
            System.out.println( name+" can't swap");
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
}
