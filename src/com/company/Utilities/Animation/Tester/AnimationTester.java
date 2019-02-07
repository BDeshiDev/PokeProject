package com.company.Utilities.Animation.Tester;

import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Animation.SpriteAnimation;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import javafx.util.Duration;
public class AnimationTester extends Application {
    String spriteSheetPath = "Assets/Animations/blueExplosion.png";
    final Duration testDuration = new Duration(700);
    final int count = 6;
    final int columns = 6;
    final int xOffset = 0;
    final int yOffset = 0;
    final int width = 113;
    final int height =100;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane p = new  Pane();
        ImageView animationViewer = new ImageView();


        animationViewer.setViewport(new Rectangle2D(xOffset,yOffset,width,height));
        p.getChildren().add(animationViewer);
        primaryStage.setTitle("Animation test Screen");
        /*
        SpriteAnimation animation = new InfiniteLoopingAnimation(
                animationViewer,spriteSheetPath,testDuration,count,columns,
                xOffset,yOffset,width,height
        );
        animation.play();
        */


        SpriteAnimation animation = AnimationFactory.getPlaceholder().toInfiniteLoop(animationViewer);
        animation.play();


        primaryStage.setScene(new Scene(p,900,600));
        primaryStage.show();
    }



}
