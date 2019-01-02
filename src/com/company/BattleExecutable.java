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
interface BattleCommand extends BattleExecutable, Comparable<BattleCommand>{
    CommandPriority getPriority();;
}
/*
*enum for denoting type of command/ battleExecutable
*In all cases, Trainer's commands must happen before any pokemon attacks
*This uses ordinal values to compare. So TrainerCommand must be FIRST.
*This is the simplest way I thought of to implement sorting commands
* but I'm sure that this is the wrong way to do it
 */
enum CommandPriority implements  Comparable<CommandPriority>{
    TrainerCommand,//trainerCommand Must be FIRST
    PokemonAttack,
    //edit: enums seem to automatically implement comparator. The extends part might be useless...
}
