package com.company.RealTime;

import javafx.scene.image.ImageView;

class BattlePlayer{
    Tile curtile;
    Grid grid;
    ImageView playerImage;

    private HpUI hpUI;
    int curHp;
    int maxHp = 150;
    private int id;
    protected boolean canAct = true;

    public BattlePlayer(ImageView playerImage,Grid grid,HpUI hpUI) {
        this.playerImage = playerImage;
        this.grid = grid;
        this.hpUI = hpUI;
        curHp = maxHp;
        grid.setPlayer(this);
    }

    public void moveToTile(Tile newTile){
        curtile = newTile;
        if(playerImage != null)
            playerImage.relocate(newTile.getX(),newTile.getY());
    }

    public void takeDamage(int damage){
        curHp = Math.max(curHp-damage,0);
        if(hpUI != null)
            hpUI.update(curHp,maxHp);
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
        return curHp > 0;
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
