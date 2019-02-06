package com.company;

import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Animation.SingleLoopAnimation;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineStreamExecutable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import com.company.networking.AttackCommandData;

import java.io.File;

public class AttackCommand extends  BattleCommand{
    private final Pokemon user;
    private BattleSlot targettedSlot;
    public final Move move;
    //private LineStream linesSource;
    private SingleLoopAnimation animation;
    private boolean executionFailed = false;
    private LineStreamExecutable lineSetter;
    private MediaPlayer mediaPlayer;

    public AttackCommand(Pokemon user, Move m, BattleSlot targetedSlot) {//used for holding move data once moves are finalized
        this.user = user;
        this.targettedSlot = targetedSlot;
        this.move = m;
        this.mediaPlayer = new MediaPlayer(new Media(new File(move.sfxName).toURI().toString()));
        lineSetter = new LineStreamExecutable();
    }

    @Override
    public void start() {
        animation = move.animationData.toSingleLoop(targettedSlot.getAnimationViewer());
        mediaPlayer.setAutoPlay(true);
        if(user.isDead()){
            executionFailed = true;
        }else {
            move.use(user, targettedSlot, lineSetter);
            animation.play();
        }
    }

    @Override
    public boolean isComplete() {
        if(animation == null || executionFailed) {
            Debugger.out("anim null");// temporary fix
            return  true;
        }
        return  lineSetter.isComplete() && animation.isComplete();//add animation check later
    }

    @Override
    public int getSpeed() {
        return user.stats.speed.getCurVal();
    }

    @Override
    public int getPriority() {
        return move.priority;
    }

    public void continueExecution(double delta, Text dialogueTarget){
       // System.out.println("continue execution" + this);
        if(isComplete())
            return;
        else{
            if(!lineSetter.isComplete()){
                lineSetter.continueExecution(delta,dialogueTarget);
            }
        }
    }

    public void end(){
        animation.end();
        mediaPlayer.stop();
    }

    @Override
    public String toString() {
        return "Attack command{" +
                "user=" + user.name +
                ", move=" + move.getName() +
                "anim = " + move.animationData.imagePath+
                '}';
    }

    @Override
    public String toJsonData() {
        return new AttackCommandData(this).toJsonData();
    }
}
