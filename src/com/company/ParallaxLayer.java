package com.company;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class ParallaxLayer extends AnimationTimer {

    private Image parallaxImage;
    int imageLen = 384;
    List<ImageView> scrollingImages = new ArrayList<>();;
    int count;
    Pane scrollParent ;

    public ParallaxLayer(Image parallaxImage, int imageLen, Pane scrollParent) {
        this.parallaxImage = parallaxImage;
        this.imageLen = imageLen;
        this.scrollParent = scrollParent;

        count =(int)Math.ceil((double)Settings.windowWidth / imageLen)+1;
        for(int i = 0 ; i < count ;i++){
            ImageView newCopy = new ImageView(parallaxImage);
            newCopy.relocate(imageLen * i , scrollParent.getLayoutY());
            scrollingImages.add(newCopy);
        }
        scrollParent.getChildren().addAll(scrollingImages);
    }

    @Override
    public void handle(long now) {
        update();
    }

    public void update(){
        for(int i = scrollingImages.size()-1; i>=0 ; i--){
            ImageView curImage = scrollingImages.get(i);
            curImage.relocate(curImage.getLayoutX() - 5,curImage.getLayoutY());
            if(i == 0 && curImage.getLayoutX()< (-imageLen)){
                scrollingImages.remove(curImage);
                curImage.relocate(scrollingImages.get(scrollingImages.size() -1).getLayoutX()+imageLen -1
                        , scrollParent.getLayoutY());
                scrollingImages.add(curImage);
            }
        }
    }
}
