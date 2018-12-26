package com.company.Utilities.Animation;

import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimationData {
    public final String imagePath;
    public final Duration duration;
    public final int count;
    public final int maxLoops;
    public final int columns;
    public final int offsetX;
    public final int offsetY;
    public final int width;
    public final int height;

    public AnimationData(String imagePath, Duration duration, int count, int maxLoops, int columns, int offsetX, int offsetY, int width, int height) {
        this.imagePath = imagePath;
        this.duration = duration;
        this.count = count;
        this.maxLoops = maxLoops;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
    }

    public SingleLoopAnimation toSingleLoop(ImageView viewerToUse){
        return  new SingleLoopAnimation(viewerToUse,this);
    }
    public InfiniteLoopingAnimation toInfiniteLoop(ImageView viewerToUse){
        return  new InfiniteLoopingAnimation(viewerToUse,this);
    }
}
