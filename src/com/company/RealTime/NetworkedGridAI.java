package com.company.RealTime;

import com.company.BattleDisplayController;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;

import java.util.List;

class NetworkedGridAI extends  GridAI{
    NetworkConnection connection;

    public NetworkedGridAI(Grid grid, NetworkConnection connection, BattleDisplayController UI, List<FighterData> party) {
        super(grid,UI,party);
        this.connection = connection;
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
        connection.writeToConnection.println(AttackMessage.getTestMessage(getId(),curtile.x,curtile.y).toJsonData());
    }

}
