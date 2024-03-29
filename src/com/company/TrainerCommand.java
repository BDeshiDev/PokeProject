package com.company;

import com.company.Utilities.Animation.AnimationData;
import com.company.Utilities.Animation.SingleLoopAnimation;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineStreamExecutable;
import javafx.scene.text.Text;


//TODO USE THE EXECUTABLE IMPLEMENTATIONS I MADE EARLIER
public abstract class TrainerCommand extends BattleCommand{
    private final AnimationData animDataToUse;
    private SingleLoopAnimation animation;
    private final LineStreamExecutable lineSetter;
    private boolean hasCalled = false;
    private  boolean playAnimOnPlayer = true;
    protected final Trainer commandUser;
    public final String commandDesc;


    public TrainerCommand(Trainer commandUser,AnimationData animDataToUse,String commandDesc,boolean playAnimOnPlayer) {
        this.animDataToUse = animDataToUse;
        this.playAnimOnPlayer = playAnimOnPlayer;
        this.commandUser = commandUser;
        this.commandDesc = commandDesc;
        lineSetter = new LineStreamExecutable();
        hasCalled = false;
    }

    public TrainerCommand(Trainer commandUser,AnimationData animDataToUse,String commandDesc,boolean playAnimOnPlayer,String... strings) {
        this(commandUser, animDataToUse, commandDesc,playAnimOnPlayer);
        for (String s:strings) {
            lineSetter.push(s);
        }
    }

    @Override
    public void start() {
        animation = animDataToUse.toSingleLoop(playAnimOnPlayer?
                commandUser.ownedSlot.getAnimationViewer():commandUser.enemySlot.getAnimationViewer());
        animation.play();
        Debugger.out(commandDesc + " trainer command from " + commandUser.name + " started");
    }

    @Override
    public boolean isComplete() {
        return  lineSetter.isComplete() && animation.isComplete() && hasCalled;//add animation check later
    }

    public void call(){
        callBack();
        hasCalled = true;
        Debugger.out(commandDesc + " trainer command from " + commandUser.name + " activate callback");
    }


    public void continueExecution(double delta, Text dialogueTarget){
        if(isComplete())
            return;
        if(lineSetter.isComplete() && animation.isComplete()){
            call();
        }
        else{
            if(!lineSetter.isComplete()){
                lineSetter.continueExecution(delta,dialogueTarget);
            }
        }
    }

    @Override
    public void end() {
        animation.end();
        System.out.println(commandDesc + "trainer command from " + commandUser.name + " ended" + " and isComplete = " + isComplete());
    }

    @Override
    public int getPriority() {
        return 999;//DO NOT CHANGE
    }

    @Override
    public int getSpeed() {
        return 999;//DO NOT CHANGE
    }

    public abstract void callBack();
}
