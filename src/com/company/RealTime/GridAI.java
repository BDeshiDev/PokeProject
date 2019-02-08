package com.company.RealTime;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

class GridAI extends  BattlePlayer{
    public GridAI(Grid grid,String imageName,HpUI hpUI) {
        super(new ImageView(imageName),grid,hpUI);
        this.grid = grid;

        new AnimationTimer(){
            int moveTimer = 0;
            int attackTimer = 0;
            int yDir = 1;
            @Override
            public void handle(long now) {
                if(!canAct)
                    return;
                moveTimer++;
                attackTimer++;
                if(moveTimer>60){
                    moveTimer=0;
                    if(curtile.y <= 0 || curtile.y >= 2)
                        yDir = -yDir;
                    handleMove(0,yDir);
                }else if(attackTimer > 120){
                    attackTimer = 0;
                    System.out.println("attackin");
                    handleAttack();
                }
            }
        }.start();
    }


    public void handleMove(int dx,int dy){
        grid.movePlayer(this,dx,dy);
    }
    public void handleAttack(){
        System.out.println("Ai can't attack in networked mode");
    }
}
