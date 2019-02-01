package com.company;
import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineHolder;
import com.company.networking.TrainerData;
import javafx.scene.layout.Pane;

import java.io.PrintWriter;
import java.util.List;

public class pcTrainer extends Trainer {

    private transient BattleCommand selectedMove = null;
    private transient MovesListUI movesListUI;
    private transient BattleController.SwapUI swapUI;//#refactor the ui stuff shouldn't really be here
    private transient Pane actionControlPane;//parent pane to attack buttons

    private boolean canCancelSwap = true;
    protected boolean waitingForSwap = false;
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

    public void updateMoveUI(){
        movesListUI.load(getStagedPokemon(),this);
    }

    public boolean hasPokeBalls(){
        return true;//fix later if needed
    }

    public void setCommand(Move m, Pokemon user){
        setCommand(new AttackCommand(getStagedPokemon(),m,enemySlot));
    }

    public boolean canCancelSwap(){
        return canCancelSwap;
    }

    public void setSwapUI(BattleController.SwapUI swapUI) {
        this.swapUI = swapUI;
        swapUI.addParty(this,party);
    }


    public void applyXp(BattleResult br, LineHolder lineHolder){
        for (int i = party.size()-1; i>=0 ; i--) {
            party.set(i , party.get(i).applyXp(br.totalXp,lineHolder));// handle evolution logic lazily
        }
    }

    public void setMovesListUI(MovesListUI movesListUI) {
        this.movesListUI = movesListUI;
        updateMoveUI();
    }

    public void setActionControlPane(Pane actionControlPane){
        this.actionControlPane = actionControlPane;
    }

    public BattleCommand getCommand() {
        return selectedMove;
    }

    public pcTrainer(String _name, Pokemon... pokemons){
        super(_name,pokemons);//... used for quickness,use list or something better
    }

    public pcTrainer(TrainerData td){
        super(td);
    }


    @Override
    public Boolean hasFinalizedCommands() {
        return selectedMove != null;//return true if we have selected a move or we need to skip turn
    }

    @Override
    public void prepTurn() {
        Debugger.out("player turn start");
        canCancelSwap = true;
        waitingForSwap = false;
        if(actionControlPane != null)
            actionControlPane.setDisable(false);
        selectedMove = null;
    }

    @Override
    protected void swapPokemon(Pokemon pokemonToSwapWith) {
        Debugger.out("swapping between" +  ownedSlot.getCurPokemon().name + " and " + pokemonToSwapWith.name);
        super.swapPokemon(pokemonToSwapWith);
        movesListUI.load(getStagedPokemon(),this);
        swapUI.toggle(false);
        waitingForSwap = false;
    }


    @Override
    public boolean hasCommandBeforeTurnEnd() {
        return selectedMove != null;
    }

    @Override
    public BattleCommand getCommandToExecuteBeforeTurnEnd() {
        return selectedMove;
    }

    public void tryToSwap(int swapNo){
        Pokemon pokeToSwapWith = party.get(swapNo);
        if(getStagedPokemon() != pokeToSwapWith && !pokeToSwapWith.isDead()){//if we can actually swap it in
            swapUI.toggle(false);
            setCommand(new SwapCommand(this,swapNo));
        }else{
            Debugger.out(pokeToSwapWith.name + " can't be sent out");
        }
    }

    @Override
    public void endBattle() {
        super.endBattle();
        swapUI = null;
        movesListUI = null;
        actionControlPane =null;
    }

    public void onCommandAccepted(){
        selectedMove = null;
        actionControlPane.setDisable(true);
    }

    @Override
    public void endTurnPrep() {
        Debugger.out("player turn end");
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
