package com.company.RealTime;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Tile extends Pane {
    int x,y;
    ImageView tileImage;
    ImageView animationView;

    public Tile(Pane gridParent,int yOffset ,  int tileSize,int x, int y) {
        this.x = x;
        this.y = y;

        tileImage = new ImageView("Assets/MapImages/Temp/emptyTile.png");
        animationView = new ImageView();
        tileImage.relocate(tileSize *x,tileSize* yOffset -tileSize * y);
        animationView.relocate(tileSize *x,tileSize* yOffset-tileSize * y);
        gridParent.getChildren().addAll(tileImage,animationView);

    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
