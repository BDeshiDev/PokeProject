package com.company.networking;

import com.company.*;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Debug.Debugger;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;

public class NetworkedEnemy extends Trainer {

    NetworkedInputReader reader;
    BattleCommand selectedCommand = null;

    boolean okayToEndTurn = false;

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
        setCommandToExecuteAtTurnEnd(null);
        okayToEndTurn = false;
        selectedCommand = null;
    }

    @Override
    public boolean canEndTurn() {
        return okayToEndTurn;//CHECK IF WE NEED  TO MOD THIS #TODO
    }

    @Override
    public void prepTurn() {
        super.prepTurn();
        selectedCommand = null;
    }

    public NetworkedEnemy(TrainerData td, NetworkConnection nc) {
        super(td);
        reader = new NetworkedInputReader(nc.readFromConnection,this);
    }

    @Override
    public void prepareForBattle(BattleSlot ownedSlot, BattleSlot enemySlot) {
        super.prepareForBattle(ownedSlot, enemySlot);
        Thread readThread = new Thread(reader);
        readThread.setDaemon(true);
        readThread.start();
    }

    @Override
    public void endBattle() {
        super.endBattle();
        reader.setShouldStopReading(true);
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

    public void setTurnEndSwapCommand(swapCommandData networkedCommand) {
        super.setCommandToExecuteAtTurnEnd(createSwapCommand(networkedCommand));
    }
}

class NetworkedInputReader implements  Runnable{
    private BufferedReader reader;
    private NetworkedEnemy readingEnemy;
    private boolean shouldStopReading = false;

    public synchronized void setShouldStopReading(boolean shouldStop){
        shouldStopReading = shouldStop;
    }


    public NetworkedInputReader(BufferedReader reader, NetworkedEnemy readingEnemy) {
        this.reader = reader;
        this.readingEnemy = readingEnemy;
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        while(!shouldStopReading){
            try {
                String readLine = reader.readLine();
                System.out.println("networker reads: " +readLine);
                if(readLine.startsWith(BattleProtocol.AttackCommandHeader)){
                    String jsonToRead = readLine.substring(BattleProtocol.AttackCommandHeader.length());
                    AttackCommandData acd = gson.fromJson(jsonToRead,AttackCommandData.class);
                    readingEnemy.setSelectedCommand(acd);
                }else if(readLine.startsWith(BattleProtocol.SwapCommandHeader)){
                    String jsonToRead = readLine.substring(BattleProtocol.SwapCommandHeader.length());
                    swapCommandData scd = gson.fromJson(jsonToRead,swapCommandData.class);
                    readingEnemy.setSelectedCommand(scd);
                }else if (readLine.startsWith(BattleProtocol.TurnEndOkay)){
                    readingEnemy.okayToEndTurn = true;
                }else if(readLine.startsWith(BattleProtocol.TurnEndSwapHeader)){
                    String jsonToRead = readLine.substring(BattleProtocol.TurnEndSwapHeader.length());
                    swapCommandData scd = gson.fromJson(jsonToRead,swapCommandData.class);
                    readingEnemy.setTurnEndSwapCommand(scd);
                }
            }catch(IOException ioe){
                ioe.printStackTrace();
                System.out.println("Failed to read enemy input from server");
            }

        }
        System.out.println("terminating enemy Read thread");
    }
}
