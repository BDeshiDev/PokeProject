package com.company.RealTime;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class Grid {
    private final int gridSize = 3;
    public final int tileCountX = gridSize * 2 ;
    public final  int tileCountY = gridSize;
    Tile[][] grid;
    public static final int tileSize = 128;
    Pane gridParentPane;

    int gridStartX = 0;
    int gridStartY = 0;

    public Grid(Pane gridParentPane) {
        this.grid = new Tile[tileCountX][tileCountY];
        this.gridParentPane = gridParentPane;
        for(int i = 0 ; i < tileCountX ; i++){
            for(int j = 0 ; j < tileCountY ; j++){
                grid[i][j] = new Tile(gridParentPane,0,j * tileCountY,tileSize,i,j);
            }
        }
    }

    public int mirrorX(int x){
        return  (tileCountX - x -1) ;
    }

    public void setPlayer(BattlePlayer player,boolean isOnLeft){
        if(gridParentPane != null)
            gridParentPane.getChildren().add(player.playerImage);
        player.moveToTile(isOnLeft?grid[gridStartX][gridStartY]:grid[mirrorX(gridStartX)][gridStartY]);//start at middle tile
    }

    public void movePlayer(BattlePlayer player,int dx,int dy){
        Tile prevTile = player.curtile;

        System.out.println("player tile " +player.curtile + " will move " + dx + "," + dy);
        if(!player.isOnLeft)
            dx = -dx;
        if(isMoveValid(prevTile,dx,dy,player.isOnLeft)){
            player.moveToTile(grid[prevTile.x + dx][prevTile.y + dy]);
        }else{
            System.out.println("invalid move " + dx+","+dy);
        }

        System.out.println("new tile " +player.curtile);
    }

    boolean isMoveValid(Tile prevTile ,int dx , int dy,boolean movingFromLeftSide){
        int targetX;
        if(!movingFromLeftSide) {
            targetX = prevTile.x + dx;
            targetX = mirrorX(targetX);
        }else
            targetX = prevTile.x + dx;

        int targetY = prevTile.y + dy;
        System.out.println("checking " + targetX + "," + targetY);
        return ((targetX <gridSize) && (targetY < gridSize) && (targetX >=0) && (targetY >=0));
    }


}
