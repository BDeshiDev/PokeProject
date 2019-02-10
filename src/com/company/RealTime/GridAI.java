package com.company.RealTime;

import com.company.BattleDisplayController;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

import java.util.*;

class GridAI extends  BattlePlayer{

    MoveCardData defaultAttack = MoveCardData.getTestMove();
    Queue<MoveCardData> selectedCards = new ArrayDeque<>();
    Random rand = new Random();

    public GridAI(Grid grid, BattleDisplayController battleDisplayController, List<FighterData> party) {
        super(new ImageView(),grid,battleDisplayController,party);
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


    @Override
    public void handleTurnRequest() {
        super.handleTurnRequest();
        selectedCards.clear();
        selectedCards.addAll(movesList);
    }
    public void handleMove(int dx, int dy){
        grid.movePlayer(this,dx,dy);
    }

    public void handleAttack(){
        if(!selectedCards.isEmpty()){
            MoveCardData newMove = selectedCards.poll();
            handleAttack(newMove);
        }else
            handleAttack(defaultAttack);
    }

    public void handleAttack(MoveCardData usedMove){
        System.out.println("Base class can't use attacks");
    }
}
