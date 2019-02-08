package com.company.RealTime;

import com.company.BattleDisplayController;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

import java.util.List;

class NetworkedGridPlayer extends  GridPlayer{
    NetworkConnection connection;

    public NetworkedGridPlayer(ImageView playerImage, Grid grid, Scene scene, BattleDisplayController UI, List<FighterData> fighters, NetworkConnection connection, BattleScreenController battleScreenController) {
        super(playerImage, grid, scene,battleScreenController, UI,fighters);
        this.connection = connection;
    }

    @Override
    public void handleMove(int dx,int  dy){
        connection.writeToConnection.println(BattleProtocol.createMessage(new moveMessage(getId(),dx,dy),BattleProtocol.moveMessageHeader));
    }

    @Override
    public void handleAttack(int attackNo) {
        switch (attackNo){
            case 0:
                connection.writeToConnection.println(AttackMessage.getFlameThrower(getId(),curtile.x,curtile.y).toJsonData());
                break;
            case 1:
                connection.writeToConnection.println(AttackMessage.getSlash(getId(),curtile.x,curtile.y).toJsonData());
                break;
        }

    }

    @Override
    public void handleMenuPressed() {
        connection.writeToConnection.println(SwapMessage.createSwapRequest(getId()));
    }

    @Override
    public void handleSwapButtonClick() {
        connection.writeToConnection.println(SwapMessage.createSwapEventMessage(getId(),1));
        battleScreenController.toggleChoiceBox(false);
    }
}
