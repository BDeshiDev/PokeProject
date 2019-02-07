package com.company.RealTime;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

class GridAI extends  BattlePlayer{
    public GridAI(Grid grid,String imageName) {
        super(new ImageView(imageName),grid);
        this.grid = grid;

        new AnimationTimer(){
            int t;
            int yDir = 1;
            @Override
            public void handle(long now) {
                t++;

                if(t>30){
                    t=0;
                    if(curtile.y <= 0 || curtile.y >= 2)
                        yDir = -yDir;

                    handleMove(0,yDir);
                }
            }
        }.start();
    }


    public void handleMove(int dx,int dy){
        grid.movePlayer(this,dx,dy);
    }
}
