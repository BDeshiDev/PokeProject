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
    int xOffset,yOffset;
    boolean isFlipped;
    ImageView tileImage;
    ImageView animationView;


    public Tile(Pane gridParent,int xOffset, int yOffset ,  int tileSize,boolean isFlipped, int x, int y) {
        this.x = x;
        this.y = y;
        this.isFlipped = isFlipped;
        this.tileSize = tileSize;
        this.yOffset = yOffset;
        this.xOffset = xOffset;
        if(gridParent != null) {
            Rectangle r= new Rectangle(tileSize,tileSize,Color.GREY);
            r.setStyle("-fx-background-color: #afccc5; -fx-stroke-type:outside;-fx-stroke:  #5550ff;-fx-stroke-width: 8;-fx-stroke-line-cap: round;");
            animationView = new ImageView();
            this.getChildren().addAll(r,animationView);
            gridParent.getChildren().addAll(this);
            r.relocate(getX(),getY());
            /*
            tileImage = new ImageView("Assets/MapImages/Temp/emptyTile.png");
            tileImage.relocate(getX(),getY());
            */
            animationView.relocate(getX()+tileSize/4.0,getY()+tileSize/4.);//small offset
            animationView.setScaleX(1);
            animationView.setScaleY(1);
            //gridParent.getChildren().addAll(tileImage, animationView);
            /*
            Label debugLabel = new Label(x+","+y);
            debugLabel.relocate(getX(),getY());
            this.getChildren().add(debugLabel);
             */
        }
    }

    public int getX(){
        return xOffset +(isFlipped?-1:1)*tileSize * x;
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
