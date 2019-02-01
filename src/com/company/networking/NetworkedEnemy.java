package com.company.networking;

import com.company.*;
import com.company.Utilities.Debug.Debugger;

public class NetworkedEnemy extends Trainer {

    volatile boolean canStopReading = false;
    volatile boolean readFailed = false;

    NetworkedInputReader reader;
    BattleCommand selectedCommand = null;

    boolean okayToEndTurn = false;

    @Override
    public boolean canFight() {
        return super.canFight() || readFailed;
    }

    @Override
    public boolean hasCommandBeforeTurnEnd() {
        return selectedCommand != null;
    }

    @Override
    public void onCommandAccepted() {

    }

    @Override
    public BattleCommand getCommandToExecuteBeforeTurnEnd() {
        return selectedCommand;
    }


    @Override
    public BattleCommand getCommand() {
        return selectedCommand;
    }

    @Override
    public Boolean hasFinalizedCommands() {
        return selectedCommand != null;
    }

    @Override
    public void endTurnPrep() {
        Debugger.out("networked enemy turn end");

        okayToEndTurn = false;
        selectedCommand = null;
    }

    @Override
    public boolean canEndTurn() {
        return okayToEndTurn;//CHECK IF WE NEED  TO MOD THIS #TODO
    }

    @Override
    public void prepTurn() {
        selectedCommand = null;
    }

    public NetworkedEnemy(TrainerData td, NetworkConnection nc) {
        super(td);
        reader = new NetworkedInputReader(nc.readFromConnection,this);
    }

    @Override
    public void prepareForBattle(BattleSlot ownedSlot, BattleSlot enemySlot) {
        super.prepareForBattle(ownedSlot, enemySlot);
        readFailed = false;
        Thread readThread = new Thread(reader);
        readThread.setDaemon(true);
        readThread.start();
    }

    @Override
    public void endBattle() {
        super.endBattle();
        canStopReading = true;
    }

    public void setSelectedCommand(AttackCommandData networkedCommand) {
        this.selectedCommand = networkedCommand.toAttackCommand(ownedSlot.getCurPokemon(),enemySlot);
    }

    public TrainerCommand createSwapCommand(swapCommandData networkedCommand){
        return  networkedCommand.toSwapCommand(this);
    }

    public void setSelectedCommand(swapCommandData networkedCommand) {
        this.selectedCommand = createSwapCommand(networkedCommand);
    }


}

