package com.company.RealTime;

import com.company.BattleDisplayController;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;

import java.util.List;

class NetworkedGridAI extends  GridAI{
    NetworkConnection connection;

    public NetworkedGridAI(Grid grid, NetworkConnection connection, String imageName, BattleDisplayController UI, List<FighterData> party) {
        super(grid,imageName,UI,party);
        this.connection = connection;
    }
    @Override
    public void handleMove(int dx, int dy) {
        connection.writeToConnection.println(BattleProtocol.createMessage(new moveMessage(getId(),dx,dy),BattleProtocol.moveMessageHeader));
    }

    @Override
    public void handleAttack() {
        connection.writeToConnection.println(AttackMessage.getTestMessage(getId(),curtile.x,curtile.y).toJsonData());
    }

}
