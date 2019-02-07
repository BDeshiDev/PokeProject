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
