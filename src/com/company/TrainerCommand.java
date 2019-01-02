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

    public TrainerCommand(Trainer commandUser,AnimationData animDataToUse, MyCallBack actualCommand) {
        this.animDataToUse = animDataToUse;
        this.actualCommand = actualCommand;
        this.commandUser = commandUser;
        lineSetter = new LineStreamExecutable();
    }

    public TrainerCommand(Trainer commandUser,AnimationData animDataToUse, MyCallBack actualCommand,String... strings) {
        this(commandUser, animDataToUse, actualCommand);
        for (String s:strings) {
            lineSetter.push(s);
        }
    }

    @Override
    public void start() {
        animation = animDataToUse.toSingleLoop(commandUser.ownedSlot.getAnimationViewer());
        animation.play();
    }

    @Override
    public boolean isComplete() {
        return  lineSetter.isComplete() && animation.isComplete() && hasCalled;//add animation check later
    }



    public void continueExecution(double delta, Text dialogueTarget){
        if(isComplete())
            return;
        if(lineSetter.isComplete() && animation.isComplete()){
            actualCommand.call();
            hasCalled = true;
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
