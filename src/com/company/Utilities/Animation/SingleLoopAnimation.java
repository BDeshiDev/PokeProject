package com.company.Utilities.Animation;

import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SingleLoopAnimation extends  SpriteAnimation {
    public SingleLoopAnimation(ImageView imageView,String imagePath, Duration duration, int count, int columns, int offsetX, int offsetY, int width, int height) {
        super(imageView, imagePath, duration,1, count, columns, offsetX, offsetY, width, height);
    }

    public SingleLoopAnimation(ImageView viewer, AnimationData data){
        this(viewer,data.imagePath, data.duration,  data.count,  data.columns,  data.offsetX,  data.offsetY,  data.width,  data.height);
    }

    public boolean ShouldEnd(){
        return getLoopCount() >=1;
    }

    @Override
    public void handleLoopEnd(){
        stop();
        super.getImageView().setImage(null);//clear image after end
    }

}
