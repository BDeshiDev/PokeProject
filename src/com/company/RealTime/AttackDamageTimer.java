package com.company.RealTime;

import java.util.ArrayList;
import java.util.List;

public class AttackDamageTimer {
    int maxDuration;//in milliseconds
    int curTimer;
    int damagePerHit;
    List<Tile> tilesToCheck;
    boolean shouldStopAfterCollision = true;

    public AttackDamageTimer(int maxDuration,  int damagePerHit, List<Tile> tilesToCheck, boolean shouldStopAfterCollision) {
        this.maxDuration = maxDuration;
        this.curTimer = 0;
        this.damagePerHit = damagePerHit;
        this.tilesToCheck = tilesToCheck;
        this.shouldStopAfterCollision = shouldStopAfterCollision;
    }

    public void applyDamage(BattlePlayer p1, BattlePlayer p2, int ticksToAdd, List<DamageMessage> damageMessages){
        curTimer += ticksToAdd;
        for (int i = tilesToCheck.size()-1;i>=0;i--){
            Tile t = tilesToCheck.get(i);
            if(t==p1.curtile){// if player is in one of the tiles we can damage
                applyDamage(p1, damageMessages, t);
            }if(t==p2.curtile){
                applyDamage(p2,damageMessages,t);
            }
        }
    }

    private void applyDamage(BattlePlayer p, List<DamageMessage> damageMessages, Tile t) {
        if(shouldStopAfterCollision && tilesToCheck.contains(t))
            tilesToCheck.remove(t);
        p.takeDamage(damagePerHit,false);
        damageMessages.add(new DamageMessage(p.getId(),damagePerHit,!p.curFighter.canFight()));
    }


    public boolean shouldEnd(){
        return curTimer >= maxDuration;
    }

}
