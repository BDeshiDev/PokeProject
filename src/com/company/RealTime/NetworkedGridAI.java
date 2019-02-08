package com.company.RealTime;

import com.company.BattleDisplayController;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
import javafx.scene.control.ProgressBar;

import java.util.List;
import java.util.Random;

class NetworkedGridAI extends  GridAI{
    NetworkConnection connection;
    ProgressBar turnProgressBar;
    Random rand = new Random();

    public NetworkedGridAI(Grid grid, NetworkConnection connection, BattleDisplayController UI,ProgressBar turnProgressBar, List<FighterData> party) {
        super(grid,UI,party);
        this.connection = connection;
        this.turnProgressBar = turnProgressBar;
    }
    @Override
    public void handleMove(int dx, int dy) {
        connection.writeToConnection.println(BattleProtocol.createMessage(new moveMessage(getId(),dx,dy),BattleProtocol.moveMessageHeader));
    }

    @Override
    public void handleKo(){
        connection.writeToConnection.println(new koMessage(getId()).toJsonData());
    }

    @Override
    public void updateTurn(double amount) {
        super.updateTurn(amount);
        turnProgressBar.setProgress(getTurnProgress());
    }


    @Override
    public void handleSwapRequest(boolean canCancel) {
        for (int i = 0 ; i< party.size() ;i++) {
            FighterData  fd = party.get(i);
            if(fd.canFight()){
                connection.writeToConnection.println(SwapMessage.createSwapEventMessage(getId(),i,false));
                return;
            }
        }
    }

    @Override
    public void handleAttack() {
        int maxIndex = movesList.size();
        connection.writeToConnection.println(movesList.get(rand.nextInt(maxIndex)).toMessage(getId(),curtile.x,curtile.y).toJsonData());
    }

}
