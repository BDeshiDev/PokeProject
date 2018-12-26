package com.company.Utilities.Animation;

import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class InfiniteLoopingAnimation extends  SpriteAnimation {
    public InfiniteLoopingAnimation(ImageView imageView,String imagePath, Duration duration, int count, int columns, int offsetX, int offsetY, int width, int height) {
        super(imageView,imagePath, duration, Animation.INDEFINITE, count, columns, offsetX, offsetY, width, height);
        super.setCycleCount(Animation.INDEFINITE);
    }

    public InfiniteLoopingAnimation(ImageView viewer, AnimationData data){
        this(viewer,data.imagePath, data.duration, data.count, data.columns, data.offsetX, data.offsetY, data.width, data.height);
    }
}
