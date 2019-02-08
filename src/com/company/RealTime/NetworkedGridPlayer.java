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
        connection.writeToConnection.println(SwapMessage.createSwapRequest(getId(),true));
    }

    @Override
    public void handleSwapButtonClick(int index) {
        FighterData monToSwapWith  =party.get(index);
        if(monToSwapWith.canFight() && monToSwapWith != curFighter) {
            connection.writeToConnection.println(SwapMessage.createSwapEventMessage(getId(), index,true));
            battleScreenController.toggleChoiceBox(false);
        }else{
            if(!monToSwapWith.canFight())
                System.out.print("can't fight");
            if(monToSwapWith == curFighter)
                System.out.print("currently fighting");
            System.out.println("Player:can't send invalid swap "+curFighter.Name +"with"+ monToSwapWith);
        }
    }
    @Override
    public void handleCancelButton() {
        super.handleCancelButton();
        connection.writeToConnection.println(BattleProtocol.MenuOffMessage);
    }

    public void handleKo(){

        connection.writeToConnection.println(new koMessage(getId()).toJsonData());
    }
}
