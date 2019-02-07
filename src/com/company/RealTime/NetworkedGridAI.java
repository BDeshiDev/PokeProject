package com.company.RealTime;

import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;

class NetworkedGridAI extends  GridAI{
    NetworkConnection connection;

    public NetworkedGridAI(Grid grid, NetworkConnection connection,String imageName,HpUI hpUI) {
        super(grid,imageName,hpUI);
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
