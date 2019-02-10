package com.company.RealTime;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sun.plugin.dom.css.Rect;

import java.util.Arrays;

public class Grid {
    private static final int gridSize = 3;
    public  static final int tileCountX = gridSize * 2 ;
    public  static final  int tileCountY = gridSize;

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

    public Rectangle[][] createGridView(GridPane gridPane ,Color cellColor,int viewCellSize){
        Rectangle[][] retVal = new Rectangle[tileCountX][tileCountY];
        for(int i = 0 ; i < tileCountX ; i++){
            for(int j = 0 ; j < tileCountY ; j++){
                retVal[i][j]= new Rectangle(viewCellSize,viewCellSize);
                retVal[i][j].setFill(cellColor);
                retVal[i][j].setStyle("-fx-stroke: rgba(140,248,255,0.34); -fx-stroke-width: 5");
                gridPane.add(retVal[i][j],i,tileCountY - j -1);//the gridPane is flipped for some reason...
            }
        }
        return  retVal;
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
