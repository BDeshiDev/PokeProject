package com.company;

import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Animation.SingleLoopAnimation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ExecutableTester extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        ImageView view = new ImageView();
        root.getChildren().add(view);
        primaryStage.setScene(new Scene(root,900,600));
        BattleExecutable executable = new AnimatedCallBack(AnimationFactory.getSlashAnimation().toSingleLoop(view),()-> System.out.println("done"));
        new AnimationTimer(){

            @Override
            public void start() {
                super.start();
                executable.start();
            }

            @Override
            public void handle(long now) {
                if(executable.isComplete())
                    stop();
                else
                    executable.continueExecution(1/60);
            }

            @Override
            public void stop() {
                super.stop();
                System.out.println("IT WORKS DAMMIT");
            }
        }.start();
        primaryStage.show();

    }
}

class AnimatedCallBack implements BattleExecutable {
    SingleLoopAnimation animToPlay;
    private MyCallBack FuncToCallAtEnd;
    private boolean hasCalled = false;


    public void start(){
        animToPlay.playFromStart();
    }
    @Override
    public boolean isComplete() {
        return animFinished() && hasCalled;
    }

    @Override
    public void continueExecution(double delta) {
        if(animFinished()){
            FuncToCallAtEnd.call();
            hasCalled = true;
        }
    }

    public AnimatedCallBack(SingleLoopAnimation animToPlay, MyCallBack funcToCallAtEnd) {
        this.animToPlay = animToPlay;
        FuncToCallAtEnd = funcToCallAtEnd;
    }

    public boolean animFinished(){
        return  animToPlay != null && animToPlay.ShouldEnd();
    }

    @Override
    public void end() {

    }
}

interface MyCallBack{
    void call();
}
