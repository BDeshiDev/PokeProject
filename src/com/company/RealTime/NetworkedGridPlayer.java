package com.company.RealTime;

import com.company.BattleDisplayController;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

 public class NetworkedGridPlayer extends  GridPlayer{
    NetworkConnection connection;

    public NetworkedGridPlayer(ImageView playerImage, Grid grid,boolean isOnLeft, Scene scene, BattleDisplayController UI, List<FighterData> fighters, NetworkConnection connection, BattleScreenController battleScreenController) {
        super(playerImage, grid, isOnLeft, scene,battleScreenController, UI,fighters);
        this.connection = connection;
    }

    @Override
    public void handleMove(int dx,int  dy){
        connection.writeToConnection.println(BattleProtocol.createMessage(new moveMessage(getId(),dx,dy),BattleProtocol.moveMessageHeader));
    }

    @Override
    public void handleAttack(MoveCardData selectedMove) {
        if(selectedMove == null){
            System.out.println("invalid move");
        }else{//convert and send the attack as a message
            connection.writeToConnection.println(selectedMove.toMessage(getId(),isOnLeft,curtile.x,curtile.y).toJsonData());
        }
    }

    @Override
    public void handleChargeAttack() {

        connection.writeToConnection.println(getDefaultAttack().toMessage(getId(),isOnLeft,true,curtile.x,curtile.y).toJsonData());
    }

    @Override
    public void handleSwapPressed() {
        super.handleSwapPressed();
        if(!readyForTurn())
            return;
        connection.writeToConnection.println(SwapMessage.createSwapRequest(getId(),true));
    }


    @Override
    public void handleSwapButtonClick(int index) {
        super.handleSwapButtonClick(index);;
        FighterData monToSwapWith  =party.get(index);
        if(monToSwapWith.canFight() && monToSwapWith != curFighter) {
            connection.writeToConnection.println(SwapMessage.createSwapEventMessage(getId(), index,true));
            battleScreenController.toggleChoiceBox(false);
        }else{
            if(!monToSwapWith.canFight())
                System.out.print("can't fight");
            if(monToSwapWith == curFighter)
                System.out.print("currently fighting");
            System.out.println("Player:can't send invalid swap "+curFighter.name +"with"+ monToSwapWith);
        }
    }
    @Override
    public void handleExitButton() {
        super.handleExitButton();
        if(canCancelSwap) {
            battleScreenController.toggleChoiceBox(false);
            connection.writeToConnection.println(SwapMessage.createSwapFailMessage(getId()));
        }
    }

    @Override
    public void handleTurnConfirm() {
        super.handleTurnConfirm();
        connection.writeToConnection.println(new TurnConfirmMessage(getId(),new ArrayList<MoveCardData>(selectedCards)).toJsonData());
    }

    public void handleKo(){
        connection.writeToConnection.println(new koMessage(getId()).toJsonData());
    }
}
