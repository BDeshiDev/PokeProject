package com.company.RealTime;

import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;

class NetworkedGridAI extends  GridAI{
    NetworkConnection connection;

    public NetworkedGridAI(Grid grid, NetworkConnection connection,String imageName) {
        super(grid,imageName);
        this.connection = connection;
    }
    @Override
    public void handleMove(int dx, int dy) {
        connection.writeToConnection.println(BattleProtocol.createMessage(new moveMessage(getId(),dx,dy),BattleProtocol.moveMessageHeader));
    }
}
