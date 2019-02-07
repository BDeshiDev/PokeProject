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
    boolean isFlipped;
    ImageView tileImage;
    ImageView animationView;

    public Tile(Pane gridParent,int yOffset ,  int tileSize,boolean isFlipped, int x, int y) {
        this.x = x;
        this.y = y;
        this.isFlipped = isFlipped;
        this.tileSize = tileSize;
        this.yOffset = yOffset;
        if(gridParent != null) {
            Rectangle r= new Rectangle(tileSize,tileSize,Color.GREY);
            r.relocate(getX(),getY());
            /*
            tileImage = new ImageView("Assets/MapImages/Temp/emptyTile.png");
            tileImage.relocate(getX(),getY());
            */
            animationView = new ImageView();
            animationView.relocate(getX(),getY());
            animationView.setScaleX(.6);
            animationView.setScaleY(.6);
            //gridParent.getChildren().addAll(tileImage, animationView);

            Label debugLabel = new Label(x+","+y);
            debugLabel.relocate(getX(),getY());

            this.getChildren().addAll(r,animationView,debugLabel);
            gridParent.getChildren().addAll(this);
        }
    }

    public int getX(){
        return (isFlipped?-1:1)*tileSize * x;
    }

    public int getY(){
        return tileSize * yOffset - tileSize * y;
    }


    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
