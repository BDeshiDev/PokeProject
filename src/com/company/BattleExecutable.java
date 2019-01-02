package com.company;

import javafx.scene.text.Text;

/*
* Essentially a couroutine interface
*
* */
public interface BattleExecutable {
    //interface for anything that needs to be executed over multiple frames in battle with commentary support
    void start();
    boolean isComplete();
    void continueExecution(double delta, Text executionOutputText);//executionOutputText == dialog box
    void end();
}
/*
* Extended version with sorting capability specifically for commands in case we want to reuse executables some where.
* Also, interfaces seem to support multi inheritance/ consider it as extending
* */
abstract class BattleCommand implements BattleExecutable, Comparable<BattleCommand>{
    public abstract int getPriority();
    public abstract int getSpeed();

    @Override
    public int compareTo(BattleCommand other) {
        int retVal;

        if(this.getPriority() != other.getPriority())
            return (other.getPriority() - this.getPriority());
        else
            return ( other.getSpeed() - this.getSpeed());
    }
}
