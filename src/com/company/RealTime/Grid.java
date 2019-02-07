package com.company.RealTime;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class Grid {
    Tile[][] grid;
    final int gridSize = 3;
    public static final int tileSize = 64;
    Pane gridParentPane;

    public Grid(Pane gridParentPane) {
        this.grid = new Tile[gridSize][gridSize];
        this.gridParentPane = gridParentPane;
        for(int i = 0 ; i < gridSize ; i++){
            for(int j = 0 ; j < gridSize ; j++){
                grid[i][j] = new Tile(gridParentPane,gridSize,tileSize,i,j);
            }
        }
    }

    public void setPlayer(BattlePlayer player){
        if(gridParentPane != null)
            gridParentPane.getChildren().add(player.playerImage);
        player.moveToTile(grid[gridSize/2][gridSize/2]);//start at middle tile
    }

    public void movePlayer(BattlePlayer player,int dx,int dy){
        Tile prevTile = player.curtile;

        System.out.println("player tile " +player.curtile);
        if(isMoveValid(prevTile,dx,dy)){
            player.moveToTile(grid[prevTile.x + dx][prevTile.y + dy]);
        }else{
            System.out.println("invalid move " + dx+","+dy);
        }

        System.out.println("new tile " +player.curtile);
    }

    boolean isMoveValid(Tile prevTile ,int dx , int dy){
        return ((prevTile.x + dx <3) && (prevTile.y + dy < 3) && (prevTile.x + dx >=0) && (prevTile.y + dy >=0));
    }


}
