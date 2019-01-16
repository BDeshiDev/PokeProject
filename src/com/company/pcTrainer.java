package com.company;
import com.company.Pokemon.Move;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineHolder;

import java.util.List;

public class pcTrainer extends Trainer {

    private BattleCommand selectedMove = null;
    private MovesListUI movesListUI;
    private BattleController.SwapUI swapUI;//#refactor the ui stuff shouldn't really be here
    private boolean canCancelSwap = true;
    private boolean waitingForSwap = false;
    private boolean hasSwapped = false;

    public void addMoveToAllInParty(Move m){
        for (Pokemon p :party) {
            p.getMoves().add(m);
        }
    }

    public boolean isPartyFull(){
        return party.size() >= Settings.maxPartySize;
    }

    public  void addMonToParty(Pokemon newMon){
        party.add(newMon);
    }


    public void setCommand(BattleCommand battleCommand){
        selectedMove = battleCommand;
    }

    @Override
    public Boolean hasFinalizedCommands() {
        return selectedMove != null;//return true if we have selected a move or we need to skip turn
    }

    public boolean hasPokeBalls(){
        return true;//fix later if needed
    }

    public void setCommand(Move m, Pokemon user){
        selectedMove =(new AttackCommand(user,m,enemySlot));
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

        selectedMove = null;
    }

    public BattleCommand getCommand() {
        return selectedMove;
    }

    public pcTrainer(String _name, Pokemon... pokemons){
        super(_name,pokemons);//... used for quickness,use list or something better
    }


    @Override
    public void swapPokemon(Pokemon pokemonToSwapWith) {
        super.swapPokemon(pokemonToSwapWith);
        movesListUI.load(getStagedPokemon(),this);
    }

    public void applyXp(BattleResult br, LineHolder lineHolder){
        for (Pokemon p:party) {
            p.applyXp(br.totalXp,lineHolder);
        }
    }

    public void tryToSwap(Pokemon pokeToSwapWith){
        Debugger.out("swapping between" +  ownedSlot.getCurPokemon().name + " and " + pokeToSwapWith.name);
        if(getStagedPokemon() != pokeToSwapWith && !pokeToSwapWith.isDead()){
            setCommandToExecuteAtTurnEnd(new TrainerCommand(this, AnimationFactory.getPokeChangeAnim(),"swap",true,
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
            selectedMove = getCommandToExecuteBeforeTurnEnd();
        }else{
            Debugger.out(pokeToSwapWith.name + " has already been sent out");
        }
    }
    public List<Pokemon> getParty(){
        return  party;
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
        selectedMove = null;

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
