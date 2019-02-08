package com.company.RealTime;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;

class GridPlayer extends  BattlePlayer{
    Scene scene;
    boolean leftPressed = false,rightPressed = false,upPressed = false,downPressed = false,
            zPressed = false,xPressed = false;
    BattleScreenController battleScreenController;



    public GridPlayer(ImageView playerImage, Grid grid, Scene scene,HpUI hpUI,BattleScreenController battleScreenController) {
        super(playerImage,grid,hpUI);
        this.scene = scene;
        this.grid =grid;
        this.battleScreenController = battleScreenController;
        battleScreenController.getSwapButton().setOnAction(event ->handleSwapButtonClick());
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


    @Override
    public void handleSwapRequest() {
        battleScreenController.toggleChoiceBox(true);
    }

    public void handleSwapButtonClick(){
        System.out.println("can't handle swap in non networked class");
    }

}
