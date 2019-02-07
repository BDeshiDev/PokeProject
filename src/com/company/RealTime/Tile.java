package com.company.RealTime;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Pane {
    int x,y;
    int tileSize;
    int yOffset;
    ImageView tileImage;
    ImageView animationView;

    public Tile(Pane gridParent,int yOffset ,  int tileSize,int x, int y) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;
        this.yOffset = yOffset;
        if(gridParent != null) {
            Rectangle r= new Rectangle(tileSize,tileSize,Color.GREY);
            r.relocate(tileSize * x, tileSize * yOffset - tileSize * y);
            /*
            tileImage = new ImageView("Assets/MapImages/Temp/emptyTile.png");
            tileImage.relocate(tileSize * x, tileSize * yOffset - tileSize * y);
            */
            animationView = new ImageView();
            animationView.relocate(tileSize * x, tileSize * yOffset - tileSize * y);
            animationView.setScaleX(.6);
            animationView.setScaleY(.6);
            //gridParent.getChildren().addAll(tileImage, animationView);
            this.getChildren().addAll(r,animationView);
            gridParent.getChildren().addAll(this);
        }
    }

    public int getX(){
        return tileSize * x;
    }

    public int getY(){
        return tileSize * yOffset - tileSize * y;
    }

    public Tile(int tileSize , int yOffset ,int x,int  y){
        this(null,tileSize,yOffset,x,y);
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
