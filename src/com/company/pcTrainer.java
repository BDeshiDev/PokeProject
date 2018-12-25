package com.company;

import java.util.*;

class pcTrainer extends Trainer {

    private ArrayList<Attack> selectedMoves = new ArrayList<>();

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

    public void updateMoveUI(BattleController.MovesListUI moveUI){
        moveUI.load(getStagedPokemon(),this);
    }


    @Override
    public void prepTurn() {
        if (ownedSlot.isEmpty()){
            Pokemon newlyStagedMon = stageFirstAvailablePokemon();
            ownedSlot.setPokemon(newlyStagedMon);
        }
        selectedMoves.clear();
    }

    public ArrayList<Attack> getCommands() {
        return selectedMoves;
    }

    public pcTrainer(String _name, Pokemon... pokemons){
        super(_name,pokemons);//... used for quickness,use list or something better
    }

    //needs a better name

}
