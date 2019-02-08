package com.company.RealTime;

import com.company.BattleDisplayController;
import javafx.scene.image.ImageView;

import java.util.List;

class BattlePlayer{
    Tile curtile;
    Grid grid;
    ImageView playerImage;

    private BattleDisplayController uiDisplay;
    int curHp;
    int maxHp = 150;
    private int id;
    protected boolean canAct = true;

    FighterData curStats;
    List<FighterData> party;

    public BattlePlayer(ImageView playerImage,Grid grid,BattleDisplayController uiDisplay, List<FighterData> party) {
        this.playerImage = playerImage;
        this.grid = grid;
        this.uiDisplay = uiDisplay;

        curHp = maxHp;
        grid.setPlayer(this);

        if(playerImage != null){
            playerImage.setScaleX(grid.isFlipped?1:-1);
        }

        this.party = party;
        if(party != null)
            curStats = party.get(0);
        if(uiDisplay!= null){
            uiDisplay.update(curStats);
        }
    }

    public void moveToTile(Tile newTile){
        curtile = newTile;
        if(playerImage != null)
            playerImage.relocate(newTile.getX(),newTile.getY());
    }

    public void takeDamage(int damage){
        curHp = Math.max(curHp-damage,0);
        if(uiDisplay != null)
            uiDisplay.update(curHp,maxHp);
    }

    public void disableActions(boolean shouldDisable){
        canAct = !shouldDisable;
        System.out.println("player is no longer disabled");
    }

    public void handleSwap(int monToSwapWith){
        System.out.println("swapping not ready with "+ monToSwapWith);
    }

    public void handleSwapRequest(){
        System.out.println("Base class can't generate swap requests");
    }

    public boolean canFight(){
        for (FighterData fd:party) {
            if(fd.curHp>0)
                return  true;
        }
        return  false;
    }

    public void setMove(int dx,int dy){
        grid.movePlayer(this,dx,dy);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
