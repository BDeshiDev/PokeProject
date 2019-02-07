package com.company.RealTime;

import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

class NetworkedGridPlayer extends  GridPlayer{
    NetworkConnection connection;

    public NetworkedGridPlayer(ImageView playerImage, Grid grid, Scene scene,HpUI hpUI ,NetworkConnection connection) {
        super( playerImage, grid, scene,hpUI);
        this.connection = connection;
    }
    @Override
    public void handleMove(int dx,int  dy){
        connection.writeToConnection.println(BattleProtocol.createMessage(new moveMessage(getId(),dx,dy),BattleProtocol.moveMessageHeader));
    }

    @Override
    public void handleAttack() {
        connection.writeToConnection.println(BattleProtocol.createMessage(AttackMessage.getTestMessage(getId(),curtile.x,curtile.y),BattleProtocol.attackMessageHeader));
    }
}
