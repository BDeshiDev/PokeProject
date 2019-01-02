package com.company.Utilities.Animation;

import com.company.BattleExecutable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SingleLoopAnimation extends  SpriteAnimation  implements BattleExecutable {
    public SingleLoopAnimation(ImageView imageView,String imagePath, Duration duration, int count, int columns, int offsetX, int offsetY, int width, int height) {
        super(imageView, imagePath, duration,1, count, columns, offsetX, offsetY, width, height);
    }

    public SingleLoopAnimation(ImageView viewer, AnimationData data){
        this(viewer,data.imagePath, data.duration,  data.count,  data.columns,  data.offsetX,  data.offsetY,  data.width,  data.height);
    }

    public boolean isComplete(){
        return getLoopCount() >=1;
    }


    @Override
    public void start() {
        super.playFromStart();
    }


    @Override
    public void continueExecution(double delta, Text dialogText) {
//do nothing
    }

    @Override
    public void end() {
        stop();
        System.out.println("stopping anim");
    }

    @Override
    public void handleLoopEnd() {
        super.handleLoopEnd();
        getImageView().setImage(null);
    }
}

