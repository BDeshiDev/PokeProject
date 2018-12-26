package com.company;

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

    public String getName(){
        return name;
    }
    public Move(String _name, Type _type, int _power, int _priority, int _accuracy,int _maxPp){
        name = _name;
        type = _type;
        power = _power;
        priority = _priority;
        accuracy = _accuracy;
        curPp = maxPp = _maxPp;
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
