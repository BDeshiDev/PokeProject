package com.company;

import com.company.Utilities.Animation.SingleLoopAnimation;
import com.company.Utilities.Animation.SpriteAnimation;
import javafx.scene.text.Text;

import java.util.*;

public class Attack implements Comparable<Attack> {//so that we can sort easily
    private final Pokemon user;
    private BattleSlot targettedSlot;
    private int targetIndex;
    private final Move move;
    //private LineStream linesSource;
    private SingleLoopAnimation animation;
    private boolean executionFailed = false;
    private LineStreamExecutable lineSetter;

    public Attack(Pokemon user, Move m,BattleSlot targetedSlot) {//used for holding move data once moves are finalized
        this.user = user;
        this.targettedSlot = targetedSlot;
        this.move = m;
        lineSetter = new LineStreamExecutable();
    }

    public int compareTo(Attack other){// for sorting
        int retVal;
        if(move==null)
            System.out.println(this.user.name+ " is null");
        if(other.move==null)
            System.out.println(other.user.name+ " is null");

        if(move.priority != other.move.priority)
            return (other.move.priority - move.priority);
        else
            return ( other.user.stats.speed.getCurVal() - this.user.stats.speed.getCurVal());
    }

    public void execute(LineStream streamToAppendTo){
        if(!user.isDead())
            move.use(user,targettedSlot,streamToAppendTo);
    }

    public void startExecution(){
        animation = move.animationData.toSingleLoop(targettedSlot.getAnimationViewer());
        if(user.isDead()){
            executionFailed = true;
        }else {
            move.use(user, targettedSlot, lineSetter);
            animation.play();
        }
    }
    public void continueExecution(double delta, Text dialogueTarget){
        System.out.println("continue execution" + this);
        if(isExecutionComplete())
            return;
        else{
            if(!lineSetter.isComplete()){
                lineSetter.continueExecution(delta,dialogueTarget);
            }
        }
    }

    public boolean isExecutionComplete(){
        if(animation == null || executionFailed) {
            System.out.println("anim null");// temporary fix
            return  true;
        }
        return  lineSetter.isComplete() && animation.isComplete();//add animation check later
    }

    public void end(){
        animation.end();
    }


    @Override
    public String toString() {
        return "Attack{" +
                "user=" + user.name +
                ", move=" + move.getName() +
                '}';
    }
}


