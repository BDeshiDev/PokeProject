package com.company.Utilities.Animation;

import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SingleLoopAnimation extends  SpriteAnimation {
    public SingleLoopAnimation(ImageView imageView, Duration duration, int count, int columns, int offsetX, int offsetY, int width, int height) {
        super(imageView, duration, 1, count, columns, offsetX, offsetY, width, height);
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
