package com.company.Pokemon.Stats;

import com.company.Pokemon.Stats.Level;
import com.company.Pokemon.Stats.Stat;

public class StatsComponent {//data holder for storing all stats necessary for a pokemon
    public final Level level;
    public final Stat maxHp;
    public final Stat attack;
    public final Stat defence;
    public final Stat spAttack;
    public final Stat spDefence;
    public final Stat speed;
    //curHp isn't included

    public StatsComponent(Level level, Stat maxHp, Stat attack, Stat defence, Stat spAttack, Stat spDefence, Stat speed) {
        this.level = level;
        this.maxHp = maxHp;
        this.attack = attack;
        this.defence = defence;
        this.spAttack = spAttack;
        this.spDefence = spDefence;
        this.speed = speed;
    }
    public StatsComponent(int curLevel,int baseHp,int hpAtMaxLvl,
                          int baseAtt,int attAtMaxLvl,
                          int baseDef,int defAtMaxLvl,
                          int baseSpAtt,int spAttAtMaxLvl,
                          int baseSpDef,int spDefAtMaxLvl,
                          int baseSpeed,int speedAtMaxLvl
                          ) {
        this.level = new Level(curLevel);
        this.maxHp = new Stat(baseHp,hpAtMaxLvl,this.level);
        this.attack = new Stat(baseAtt,attAtMaxLvl,this.level);
        this.defence = new Stat(baseDef,defAtMaxLvl,this.level);
        this.spAttack = new Stat(baseSpAtt,spAttAtMaxLvl,this.level);
        this.spDefence = new Stat(baseSpDef,spDefAtMaxLvl,this.level);
        this.speed = new Stat(baseSpeed,speedAtMaxLvl,this.level);
    }

    public StatsComponent(StatsComponent statsToCopy) {
        this.level = new Level(statsToCopy.level.getCurLevel());
        this.maxHp = new Stat(statsToCopy.maxHp,this.level);
        this.attack = new Stat(statsToCopy.attack,this.level);
        this.defence = new Stat(statsToCopy.defence,this.level);
        this.spAttack = new Stat(statsToCopy.spAttack,this.level);
        this.spDefence = new Stat(statsToCopy.spDefence,this.level);
        this.speed = new Stat(statsToCopy.speed,this.level);
    }
    public StatsComponent(StatsComponent statsToCopy,int level) {
        this.level = new Level(level);
        this.maxHp = new Stat(statsToCopy.maxHp,this.level);
        this.attack = new Stat(statsToCopy.attack,this.level);
        this.defence = new Stat(statsToCopy.defence,this.level);
        this.spAttack = new Stat(statsToCopy.spAttack,this.level);
        this.spDefence = new Stat(statsToCopy.spDefence,this.level);
        this.speed = new Stat(statsToCopy.speed,this.level);
    }

    public void  addXp(int amount){
        level.addXP(amount);
        attack.lerp(level);
        defence.lerp(level);
        spAttack.lerp(level);
        spDefence.lerp(level);
        speed.lerp(level);
        maxHp.lerp(level);
    }

    @Override
    public String toString() {
        return "StatsComponent{" +
                "level=" + level +
                ", maxHp=" + maxHp +
                ", attack=" + attack +
                ", defence=" + defence +
                ", spAttack=" + spAttack +
                ", spDefence=" + spDefence +
                ", speed=" + speed +
                '}';
    }
}
