package com.company.RealTime;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

enum TargetPattern{
    singleTile{
        @Override
        public  <T extends Node> List<T> getTargetTiles(T[][] grid, boolean isMirrored,int startX, int startY,int maxXCount,int maxYCount,int tileCountX,int tileCountY) {
            List<T> SelectedTiles = new ArrayList<>();
            SelectedTiles.add(grid[startX][startY]);
            return SelectedTiles;
        }
    },row{
        @Override
        public  <T extends Node> List<T> getTargetTiles(T[][] grid, boolean isMirrored,int startX, int startY,int maxXCount,int maxYCount,int tileCountX,int tileCountY) {
            List<T> SelectedTiles = new ArrayList<>();
            int dir = isMirrored? -1:1;
            for(int i = 0 ; i < maxXCount;i++){
                int targetX = startX +dir * i;
                System.out.println("targeting  " + targetX + "," +startY);
                if(targetX>=0 && targetX< tileCountX){
                    SelectedTiles.add(grid[targetX][startY]);
                }
            }
            System.out.println("targetted " + SelectedTiles.size());
            return SelectedTiles;
        }
    },column{
        @Override
        public  <T extends Node> List<T> getTargetTiles(T[][] grid,boolean isMirrored, int startX, int startY,int maxXCount,int maxYCount,int tileCountX,int tileCountY) {
            List<T> SelectedTiles = new ArrayList<>();
            for(int i = 0 ; (startY + i ) < tileCountY && i < maxYCount;i++){
                SelectedTiles.add(grid[startX][startY+i]);
            }
            return SelectedTiles;
        }
    };

    public abstract  <T extends Node> List<T> getTargetTiles(T[][] grid, boolean isMirrored, int startX, int startY, int maxXCount, int maxYCount,int tileCountX,int tileCountY);
}
