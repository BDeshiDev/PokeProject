package com.company;

import com.company.Utilities.Animation.AnimationData;
import javafx.animation.Animation;

class  Move{
    private String name;
    public final Type type;
    public final int power;
    public final int priority;
    //currently unused
    public final int accuracy;
    public final int maxPp;
    private int curPp;
    //unused end
    public AnimationData animationData;

    public String getName(){
        return name;
    }

    public Move(String name, Type type, int power, int priority, int accuracy, int maxPp, AnimationData animationData) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.priority = priority;
        this.accuracy = accuracy;
        this.curPp = this.maxPp = maxPp;
        this.animationData = animationData;
    }

    public boolean canBeUsed(){
        return  curPp>0;
    }

    public void use(Pokemon user, BattleSlot target,LineStream streamToAppendTo){
        //System.out.println(user.name + " used " + this.name);
        streamToAppendTo.push(user.name + " used " + this.name);
        curPp--;
        double stabBonus = user.getStabBoost(this);
        target.takeHit(this,user.stats.attack.getCurVal(),stabBonus,streamToAppendTo);//#UNIMPLEMENTED certain moves should use sp attack in
    }
}
