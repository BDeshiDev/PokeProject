package com.company;

import com.company.Utilities.Animation.AnimationData;
import com.company.Utilities.Animation.SingleLoopAnimation;
import javafx.scene.text.Text;


//TODO USE THE EXECUTABLE IMPLEMENTATIONS I MADE EARLIER
class TrainerCommand extends BattleCommand{
    private final AnimationData animDataToUse;
    private final Trainer commandUser;
    private SingleLoopAnimation animation;
    private final LineStreamExecutable lineSetter;
    private boolean hasCalled = false;
    private final MyCallBack actualCommand;
    public final String commandDesc;

    public TrainerCommand(Trainer commandUser,AnimationData animDataToUse,String commandDesc, MyCallBack actualCommand) {
        this.animDataToUse = animDataToUse;
        this.actualCommand = actualCommand;
        this.commandUser = commandUser;
        this.commandDesc = commandDesc;
        lineSetter = new LineStreamExecutable();
        hasCalled = false;
    }

    public TrainerCommand(Trainer commandUser,AnimationData animDataToUse,String commandDesc, MyCallBack actualCommand,String... strings) {
        this(commandUser, animDataToUse, commandDesc,actualCommand);
        for (String s:strings) {
            lineSetter.push(s);
        }
    }

    @Override
    public void start() {
        animation = animDataToUse.toSingleLoop(commandUser.ownedSlot.getAnimationViewer());
        animation.play();
        System.out.println(commandDesc + " trainer command from " + commandUser.name + " started");
    }

    @Override
    public boolean isComplete() {
        return  lineSetter.isComplete() && animation.isComplete() && hasCalled;//add animation check later
    }

    public void call(){
        actualCommand.call();
        hasCalled = true;
        System.out.println(commandDesc + " trainer command from " + commandUser.name + " activate callback");
    }


    public void continueExecution(double delta, Text dialogueTarget){
        /*
        System.out.println("trainer command from " + commandUser.name +
                            "line" + lineSetter.isComplete()+
                            "anim" + animation.isComplete()+
                            "call " + hasCalled
        );*/
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
        System.out.println(commandDesc + "trainer command from " + commandUser.name + " ended" + " and " + isComplete());
    }

    @Override
    public int getPriority() {
        return 999;//DO NOT CHANGE
    }

    @Override
    public int getSpeed() {
        return 999;//DO NOT CHANGE
    }
}
