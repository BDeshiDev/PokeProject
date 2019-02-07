package com.company.RealTime;

import javafx.scene.image.ImageView;

class BattlePlayer{
    Tile curtile;
    Grid grid;
    ImageView playerImage;
    private int id;

    public BattlePlayer(ImageView playerImage,Grid grid) {
        this.playerImage = playerImage;
        this.grid = grid;
        grid.setPlayer(this);
    }

    public void moveToTile(Tile newTile){
        curtile = newTile;
        playerImage.relocate(newTile.tileImage.getLayoutX(),newTile.tileImage.getLayoutY());
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
