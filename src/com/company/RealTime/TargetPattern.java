package com.company.RealTime;

import java.util.ArrayList;
import java.util.List;

enum TargetPattern{
    singleTile{
        @Override
        public List<Tile> getTargetTiles(Grid gridToCheck, int startX, int startY) {
            List<Tile> SelectedTiles = new ArrayList<>();
            SelectedTiles.add(gridToCheck.grid[startX][startY]);
            return SelectedTiles;
        }
    },row{
        @Override
        public List<Tile> getTargetTiles(Grid gridToCheck, int startX, int startY) {
            List<Tile> SelectedTiles = new ArrayList<>();
            for(int i = 0 ; (startX + i ) < gridToCheck.gridSize;i++){
                SelectedTiles.add(gridToCheck.grid[startX +i][startY]);
            }
            return SelectedTiles;
        }
    },column{
        @Override
        public List<Tile> getTargetTiles(Grid gridToCheck, int startX, int startY) {
            List<Tile> SelectedTiles = new ArrayList<>();
            for(int i = 0 ; (startY + i ) < gridToCheck.gridSize;i++){
                SelectedTiles.add(gridToCheck.grid[startX][startY+i]);
            }
            return SelectedTiles;
        }
    };

    public  abstract List<Tile> getTargetTiles(Grid gridToCheck,int startX,int startY);
}
