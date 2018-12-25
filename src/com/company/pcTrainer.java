package com.company;

import java.util.*;

class pcTrainer extends Trainer {

    private ArrayList<Attack> selectedMoves = new ArrayList<>();
    private BattleController.MovesListUI movesListUI;

    @Override
    public Boolean hasFinalizedCommands() {
        return selectedMoves.size()>0;//return true we have selected a move
    }


    public void setCommand(Move m,Pokemon user){
        selectedMoves.add(new Attack(user,m,enemySlot));
    }
    //implement if needed hint: make selected moves a stack, convert it to list in getCommands();
    /*
    public void undoCommand(){
    }*/

    public void setMovesListUI(BattleController.MovesListUI movesListUI) {
        this.movesListUI = movesListUI;
    }

    public void updateMoveUI(){
        movesListUI.load(getStagedPokemon(),this);
    }

    @Override
    public void prepTurn() {
        if (ownedSlot.isEmpty()){
            Pokemon newlyStagedMon = stageFirstAvailablePokemon();
            ownedSlot.setPokemon(newlyStagedMon);
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
}
