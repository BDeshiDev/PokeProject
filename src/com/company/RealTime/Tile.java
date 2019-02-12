package com.company.RealTime;

import com.company.Utilities.Animation.AnimationFactory;
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
    ImageView tileImage;
    ImageView animationView;


    public Tile(Pane gridParent,int xOffset, int yOffset ,  int tileSize, int x, int y) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;
        this.yOffset = yOffset;
        this.xOffset = xOffset;
        if(gridParent != null) {
            Rectangle r= new Rectangle(tileSize*.9,tileSize*.9);
            r.setStyle("-fx-fill: rgba(189,209,233,0.16);");
            animationView = new ImageView();
            this.getChildren().addAll(r,animationView);
            gridParent.getChildren().addAll(this);
            r.relocate(getX(),getY());
            /*
            tileImage = new ImageView("Assets/MapImages/Temp/emptyTile.png");
            tileImage.relocate(getX(),getY());
            */
            animationView.relocate(getX()+tileSize/4.0,getY()+tileSize/4.0);//small offset

            AnimationFactory.getHotFixAnim().toSingleLoop(animationView).start();
            /*
            Label debugLabel = new Label(x+","+y);
            debugLabel.relocate(getX(),getY());
            this.getChildren().add(debugLabel);*/
        }
    }

    public int getX(){
        return xOffset +tileSize * x;
    }

    public int getY(){
        return yOffset - tileSize * y;
    }


    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
