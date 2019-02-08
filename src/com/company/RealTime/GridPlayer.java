package com.company.RealTime;

import com.company.BattleDisplayController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.util.List;
/*
* Supposed to be an offline version of the player... but our game won't work offline
* */
class GridPlayer extends  BattlePlayer{
    Scene scene;
    boolean leftPressed = false,rightPressed = false,upPressed = false,downPressed = false,
            zPressed = false,xPressed = false;
    BattleScreenController battleScreenController;
    boolean canCancel = true;


    public GridPlayer(ImageView playerImage, Grid grid, Scene scene,BattleScreenController battleScreenController, BattleDisplayController uiDisplay, List<FighterData> party) {
        super(playerImage,grid,uiDisplay,party);
        this.scene = scene;
        this.grid =grid;
        this.battleScreenController = battleScreenController;
        FlowPane parentPane = battleScreenController.getSwapParentPane();
        for (int i = 0; i < party.size();i++) {
            Button swapButton = new Button(party.get(i).Name);
            final  int swapNo = i;
            swapButton.setOnAction(event ->handleSwapButtonClick(swapNo));
            parentPane.getChildren().add(swapButton);
        }
        battleScreenController.getExitButton().setOnAction(event -> handleCancelButton());

        addListeners(scene);
    }
    public void addListeners(Scene s){//the player should only move one tile at a time
        s.setOnKeyPressed(e->{
            if(!canAct)
                return;
            switch (e.getCode()){
                case UP:
                    if(!upPressed)
                        handleMove(0,1);
                    upPressed = true;
                    break;
                case DOWN:
                    if(!downPressed)
                        handleMove(0,-1);
                    downPressed = true;
                    break;
                case LEFT:
                    if(!leftPressed)
                        handleMove(-1,0);
                    leftPressed = true;
                    break;
                case RIGHT:
                    if(!rightPressed)
                        handleMove(1,0);
                    rightPressed = true;
                    break;
                case Z:
                    if(!zPressed )
                        handleAttack(0);
                    zPressed = true;
                    break;
                case X:
                    if(!xPressed )
                        handleAttack(1);
                    xPressed = true;
                    break;
            }});
        s.setOnKeyReleased(e->{
            if(!canAct)
                return;
            switch (e.getCode()){
                case UP:
                    upPressed = false;
                    break;
                case DOWN:
                    downPressed = false;
                    break;
                case LEFT:
                    leftPressed = false;
                    break;
                case RIGHT:
                    rightPressed = false;
                    break;
                case Z:
                    zPressed  = false;
                    break;
                case X:
                    xPressed  = false;
                    break;
                case SPACE:
                    handleMenuPressed();
                    break;
            }});
    }
    public void handleMenuPressed(){
        System.out.println("Can't pause in non networked mode");
    }
    public void handleMove(int dx,int  dy){
        grid.movePlayer(this,dx,dy);
    }
    public void handleAttack(int attackNo){
        System.out.println("can't attack in non networked mode...");
    }

    public void handleCancelButton(){
        if(canCancel)
            battleScreenController.toggleChoiceBox(false);
    }

    @Override
    public void handleSwapRequest(boolean canCancel)
    {
        this.canCancel = canCancel;
        battleScreenController.toggleChoiceBox(true);
    }

    public void handleSwapButtonClick(int i){
        System.out.println("can't handle swap in non networked class");
    }
}
