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
    private LineStream linesSource;
    private SingleLoopAnimation animation;

    public Attack(Pokemon user, Move m,BattleSlot targetedSlot) {//used for holding move data once moves are finalized
        this.user = user;
        this.targettedSlot = targetedSlot;
        this.move = m;
        linesSource = new LineStream();
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

    public void startExectution(){
        if(user.isDead())
            return;
        move.use(user,targettedSlot,linesSource);
        animation = move.animationData.toSingleLoop(targettedSlot.getAnimationViewer());
        animation.play();
    }
    public void continueExecution(double delta, Text dialogueTarget){
        if(isExecutionComplete())
            return;
        else{
            if(!linesSource.streamComplete()){//if we have lines to show, do that first or do else statement
                linesSource.addDelta(delta);//update timer on lineSource
                if(linesSource.hasLine()){
                    String s= linesSource.pop();
                    //System.out.println(s);
                    dialogueTarget.setText(s);
                }
            }
        }
    }

    public boolean isExecutionComplete(){
        if(animation == null) {
            System.out.println("anim null");// temporary fix
            return  true;
        }
        return  linesSource.streamComplete() && animation.ShouldEnd();//add animation check later
    }


    @Override
    public String toString() {
        return "Attack{" +
                "user=" + user.name +
                ", move=" + move.getName() +
                '}';
    }
}
