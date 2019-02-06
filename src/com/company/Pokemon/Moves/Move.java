package com.company.Pokemon.Moves;

import com.company.BattleSlot;
import com.company.Pokemon.Pokemon;
import com.company.Pokemon.Type;
import com.company.Utilities.Animation.AnimationData;
import com.company.Utilities.TextHandler.LineHolder;

public class  Move{
    private String name;
    public final Type type;
    public final DamageType damageType;
    public final int power;
    public final int priority;
    //currently unused
    public final int accuracy;
    public final int maxPp;
    private int curPp;
    //unused end
    public AnimationData animationData;
    public final String sfxName;
    public int getCurPp() {
        return curPp;
    }

    public String getName(){
        return name;
    }

    public Move(String name, Type type,DamageType damageType, int power, int priority, int accuracy, int maxPp, AnimationData animationData,String sfxName) {
        this.name = name;
        this.type = type;
        this.damageType = damageType;
        this.power = power;
        this.priority = priority;
        this.accuracy = accuracy;
        this.curPp = this.maxPp = maxPp;
        this.sfxName = sfxName;
        this.animationData = animationData;
    }

    public boolean canBeUsed(){
        return  curPp>0;
    }

    public void use(Pokemon user, BattleSlot target, LineHolder streamToAppendTo){
        //System.out.println(user.name + " used " + this.name);
        streamToAppendTo.push(user.name + " used " + this.name);
        curPp--;
        double stabBonus = user.getStabBoost(this);

        Pokemon targetMon = target.getCurPokemon();

        double moveMod = targetMon.getMoveModifier(this);
        String effectString;
        if(moveMod > 1)
            effectString ="It's SUPER effective!";
        else if(moveMod < 1)
            effectString ="It's not very effective... ";
        else
            effectString =  "... ";
        streamToAppendTo.push(effectString);
        //actual formula
        int damage = Math.max((int)(((2*user.getLevel()/5+2) * power
                * this .damageType.getAttackBonus(user)/this .damageType.getDefenceBonus(targetMon) /50.0+2)*moveMod*stabBonus),0);
        //((m.power + damageBonus - stats.defence.getCurVal())*moveMod * stabBonus),0);
        target.takehit(damage,streamToAppendTo);
    }

    public  void resetPp(){
        curPp = maxPp;
    }
}
