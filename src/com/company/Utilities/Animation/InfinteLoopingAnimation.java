package com.company.Utilities.Animation;

import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class InfinteLoopingAnimation extends  SpriteAnimation {
    public InfinteLoopingAnimation(ImageView imageView, Duration duration, int count, int columns, int offsetX, int offsetY, int width, int height) {
        super(imageView, duration, Animation.INDEFINITE, count, columns, offsetX, offsetY, width, height);
        super.setCycleCount(Animation.INDEFINITE);
    }
}
