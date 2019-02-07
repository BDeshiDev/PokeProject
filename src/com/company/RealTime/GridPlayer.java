package com.company.RealTime;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;

class GridPlayer extends  BattlePlayer{
    Scene scene;
    boolean leftPressed = false,rightPressed = false,upPressed = false,downPressed = false,attackPressed;
    public GridPlayer(ImageView playerImage, Grid grid, Scene scene,HpUI hpUI) {
        super(playerImage,grid,hpUI);
        this.scene = scene;
        this.grid =grid;
        addListeners(scene);
    }
    public void addListeners(Scene s){//the player should only move one tile at a time
        s.setOnKeyPressed(e->{
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
                    if(!attackPressed)
                        handleAttack();
                    attackPressed = true;
                    break;
            }});
        s.setOnKeyReleased(e->{
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
                    attackPressed = false;
                    break;
            }});
    }

    public void handleMove(int dx,int  dy){
        grid.movePlayer(this,dx,dy);
    }
    public void handleAttack(){
        System.out.println("can't attack in non networked mode...");
    }
}
