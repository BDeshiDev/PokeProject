package com.company;

import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Animation.SingleLoopAnimation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class ExecutableTester extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        ImageView view = new ImageView();
        Text dialogText = new Text();
        root.getChildren().addAll(view,dialogText);

        primaryStage.setScene(new Scene(root,900,600));
        primaryStage.setTitle("Executable interface TEST ver 0.666");


        BattleExecutable executable = new DelayedCallBack(AnimationFactory.getSlashAnimation().toSingleLoop(view),()-> System.out.println("done"));
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
                    executable.continueExecution(1/60,dialogText);
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
/*
* these are helper classes thta have not been used in code yet.
* The battlcommand class will be implemented using these in the next refactoring phase if I have time to do so
* */
class DelayedCallBack implements BattleExecutable {
    BattleExecutable whatToExecute;
    private MyCallBack FuncToCallAtEnd;
    private boolean hasCalled = false;

    public void start(){
        whatToExecute.start();
    }
    @Override
    public boolean isComplete() {
        return executionComplete() && hasCalled;
    }

    @Override
    public void continueExecution(double delta, Text executionOutputText) {
        if(executionComplete()){
            FuncToCallAtEnd.call();
            hasCalled = true;
        }
    }

    public DelayedCallBack(BattleExecutable whatToExecute, MyCallBack funcToCallAtEnd) {
        this.whatToExecute = whatToExecute;
        FuncToCallAtEnd = funcToCallAtEnd;
    }

    public boolean executionComplete(){
        return  whatToExecute != null && whatToExecute.isComplete();
    }

    @Override
    public void end() {
        whatToExecute.end();
    }
}

class SimultaneousMultiExecute implements BattleExecutable{
    List<BattleExecutable> stuffToExecute;
    private MyCallBack FuncToCallAtEnd;
    private boolean hasCalled = false;

    public void start(){
        for (BattleExecutable be:stuffToExecute) {
            be.start();
        }
    }
    @Override
    public boolean isComplete() {
        return hasCalled && executionComplete() ;
    }

    @Override
    public void continueExecution(double delta, Text executionOutputText) {
        for (BattleExecutable be:stuffToExecute) {
            be.continueExecution(delta,executionOutputText);
        }
        if(executionComplete()){
            activateCallBack();
        }
    }

    public void activateCallBack(){
        System.out.println("calling");
        FuncToCallAtEnd.call();
        hasCalled = true;
    }

    public SimultaneousMultiExecute( MyCallBack funcToCallAtEnd,BattleExecutable... thingsToExecute) {
        this.stuffToExecute = new ArrayList<>();
        for (BattleExecutable be:thingsToExecute) {
            this.stuffToExecute.add(be);
        }
        FuncToCallAtEnd = funcToCallAtEnd;
    }

    public boolean executionComplete(){
        for (BattleExecutable be:stuffToExecute) {
            if(be!= null && !be.isComplete())
                return false;
        }
        return true;
    }

    @Override
    public void end() {
        for (BattleExecutable be:stuffToExecute) {
            be.end();
        }
    }
}
interface MyCallBack{
    void call();
}
