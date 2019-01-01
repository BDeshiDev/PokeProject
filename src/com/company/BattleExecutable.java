package com.company;

public interface BattleExecutable {
    void start();
    boolean isComplete();
    void continueExecution(double delta);
    void end();
}
