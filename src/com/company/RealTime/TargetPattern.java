package com.company.RealTime;

import java.util.ArrayList;
import java.util.List;

enum TargetPattern{
    singleTile{
        @Override
        public List<Tile> getTargetTiles(Grid gridToCheck, boolean isMirrored,int startX, int startY,int maxXCount,int maxYCount) {
            List<Tile> SelectedTiles = new ArrayList<>();
            SelectedTiles.add(gridToCheck.grid[startX][startY]);
            return SelectedTiles;
        }
    },row{
        @Override
        public List<Tile> getTargetTiles(Grid gridToCheck, boolean isMirrored,int startX, int startY,int maxXCount,int maxYCount) {
            List<Tile> SelectedTiles = new ArrayList<>();
            int dir = isMirrored? -1:1;
            for(int i = 0 ; i < maxXCount;i++){
                int targetX = startX +dir * i;
                System.out.println("targeting  " + targetX + "," +startY);
                if(targetX>=0 && targetX< gridToCheck.tileCountX){
                    SelectedTiles.add(gridToCheck.grid[targetX][startY]);
                }
            }
            return SelectedTiles;
        }
    },column{
        @Override
        public List<Tile> getTargetTiles(Grid gridToCheck,boolean isMirrored, int startX, int startY,int maxXCount,int maxYCount) {
            List<Tile> SelectedTiles = new ArrayList<>();
            for(int i = 0 ; (startY + i ) < gridToCheck.tileCountY && i < maxYCount;i++){
                SelectedTiles.add(gridToCheck.grid[startX][startY+i]);
            }
            return SelectedTiles;
        }
    };

    public  abstract List<Tile> getTargetTiles(Grid gridToCheck,boolean isMirrored,int startX,int startY,int maxXCount,int maxYCount);
}
