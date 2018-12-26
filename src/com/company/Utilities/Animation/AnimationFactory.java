package com.company.Utilities.Animation;

import com.company.Utilities.Animation.SingleLoopAnimation;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimationFactory {
    public static AnimationData getFlameAnimation(){
        return  new AnimationData(new String("Assets/Animations/11 - Copie.png"), new Duration(500),
                4, 1, 4, 0, 0,82, 100);
    }
    public static AnimationData getSlashAnimation(){
        return  new AnimationData(new String("Assets/Animations/4.png"), new Duration(500),
                5 ,1, 5, 0, 0,102, 100);
    }
}
