package com.company;

import javafx.scene.text.Text;

/*
* Essentially a couroutine interface
*/
public interface BattleExecutable {
    //interface for anything that needs to be executed over multiple frames in battle with commentary support
    void start();
    boolean isComplete();
    void continueExecution(double delta, Text executionOutputText);//executionOutputText == dialog box
    void end();
}
