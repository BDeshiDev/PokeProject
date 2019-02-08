package com.company.RealTime;

import com.company.BattleDisplayController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

class BattlePlayer{
    Tile curtile;
    Grid grid;
    ImageView playerImage;

    private BattleDisplayController uiDisplay;
    private int id;
    protected boolean canAct = true;

    FighterData curFighter;
    List<FighterData> party;

    public BattlePlayer(ImageView playerImage,Grid grid,BattleDisplayController uiDisplay, List<FighterData> party) {
        this.playerImage = playerImage;
        this.grid = grid;
        this.uiDisplay = uiDisplay;
        grid.setPlayer(this);

        if(playerImage != null){
            playerImage.setScaleX(grid.isFlipped?1:-1);
        }

        this.party = party;
        if(party != null) {
            setCurFighter(party.get(0));
        }else {
            System.out.println("party null...");
            System.exit(-1);
        }
        if(uiDisplay!= null){
            uiDisplay.update(curFighter);
        }
    }

    public void setCurFighter(FighterData fd){
        curFighter = fd;
        if(uiDisplay != null)
            uiDisplay.update(fd);
        if(playerImage != null)
            playerImage.setImage(new Image(fd.imageName));
    }

    public void setCurFighter(int index){
        setCurFighter(party.get(index));
    }

    public void moveToTile(Tile newTile){
        curtile = newTile;
        if(playerImage != null)
            playerImage.relocate(newTile.getX(),newTile.getY());
    }

    public void takeDamage(int damage){
        curFighter.curHp = Math.max(curFighter.curHp-damage,0);
        if(uiDisplay != null)
            uiDisplay.update(curFighter.curHp,curFighter.maxHp);
    }

    public void disableActions(boolean shouldDisable){
        canAct = !shouldDisable;
        System.out.println("player is no longer disabled");
    }

    public void handleSwap(int swapIndex){
        if(swapIndex>=party.size()){
            System.out.println("swap index out of bounds " + swapIndex);
        }else{
            if(party.get(swapIndex).canFight())
                setCurFighter(swapIndex);
            else
                System.out.println("invalid swap due to insufficient hp");
        }

    }

    public void handleSwapRequest(){
        System.out.println("Base class can't generate swap requests");
    }

    public boolean canFight(){
        for (FighterData fd:party) {
            if(fd.canFight())
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
