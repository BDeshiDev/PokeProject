package com.company.RealTime;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class Grid {
    Tile[][] grid;
    final int gridSize = 3;
    public static final int tileSize = 128;
    Pane gridParentPane;
    public final boolean isFlipped;

    public Grid(Pane gridParentPane,boolean isFlipped) {
        this.grid = new Tile[gridSize][gridSize];
        this.isFlipped = isFlipped;
        this.gridParentPane = gridParentPane;
        for(int i = 0 ; i < gridSize ; i++){
            for(int j = 0 ; j < gridSize ; j++){
                grid[i][j] = new Tile(gridParentPane,isFlipped?180:0,gridSize,tileSize,isFlipped,i,j);
            }
        }
    }

    public int mirrorX(int x){
        return  (gridSize -x -1) ;
    }

    public void setPlayer(BattlePlayer player){
        if(gridParentPane != null)
            gridParentPane.getChildren().add(player.playerImage);
        player.moveToTile(grid[0][0]);//start at middle tile
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
