package com.company;

import com.company.Utilities.Animation.SingleLoopAnimation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimationFactory {
    public static SingleLoopAnimation getFlameAnimation(ImageView viewer){
        viewer.setImage(new Image("Assets/Animations/11 - Copie.png"));
        return  new SingleLoopAnimation(viewer, new Duration(500),
                4, 4, 0, 0, 82, 100);
    }
    public static SingleLoopAnimation getSlashAnimation(ImageView viewer){
        viewer.setImage(new Image("Assets/Animations/4.png"));
        return  new SingleLoopAnimation(viewer, new Duration(700),
                5, 5, 0, 0, 102, 100);
    }
}
