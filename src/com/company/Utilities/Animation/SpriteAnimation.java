package com.company.Utilities.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class SpriteAnimation extends Transition {

    private Image spriteSheet;
    private final ImageView imageView;
    private final int count;
    private int loopCount;
    private final int maxLoops;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    public ImageView getImageView() {
        return imageView;
    }

    private int lastIndex;
    public SpriteAnimation(
            ImageView imageView, String imagePath,
            Duration duration,int maxLoops,
            int count,   int columns,
            int offsetX, int offsetY,
            int width,   int height) {
        this.imageView = imageView;
        this.spriteSheet = new Image(imagePath);
        this.maxLoops = maxLoops;
        this.setCycleCount(maxLoops);
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    public SpriteAnimation(ImageView imageView,AnimationData data){
        this(imageView,data.imagePath,data.duration,data.maxLoops,data.count,data.columns,data.offsetX,data.offsetY,data.width,data.height);
    }

    @Override
    public void play() {
        imageView.setImage(spriteSheet);
        super.play();
    }

    public int getLoopCount() {
        return loopCount;
    }

    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {

            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;

            if(lastIndex>= (count-1)){
                loopCount++;
                handleLoopEnd();
            }
        }
    }

    public  void  handleLoopEnd(){

    }
}