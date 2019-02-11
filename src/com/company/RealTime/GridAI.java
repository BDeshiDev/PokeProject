package com.company.RealTime;

import com.company.BattleDisplayController;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

import java.util.*;

class GridAI extends  BattlePlayer{

    MoveCardData defaultAttack = MoveCardData.getTestMove();
    Queue<MoveCardData> selectedCards = new ArrayDeque<>();
    Random rand = new Random();

    public GridAI(Grid grid, boolean isOnLeft,BattleDisplayController battleDisplayController, List<FighterData> party) {
        super(new ImageView(),grid,isOnLeft,battleDisplayController,party);
        this.grid = grid;

        new AnimationTimer(){
            int moveTimer = 0;
            int moveDelay = 90;
            int attackTimer = 0;
            int attackDelay = 120;
            int yDir = 1;
            @Override
            public void handle(long now) {
                if(!canAct)
                    return;
                moveTimer++;
                attackTimer++;
                if(moveTimer>moveDelay){
                    moveTimer=0;
                    if(curtile.y <= 0 || curtile.y >= 2)
                        yDir = -yDir;
                    handleMove(yDir,yDir);
                }else if(attackTimer > attackDelay){
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
