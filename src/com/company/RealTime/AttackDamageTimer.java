package com.company.RealTime;

import com.company.Pokemon.Type;

import java.util.ArrayList;
import java.util.List;

public class AttackDamageTimer {
    List<Tile> tilesToCheck;
    AttackMessage attackMessage;
    MoveCardData dataToUse;

    FighterData user;

    int curTimer =0;

    public AttackDamageTimer(List<Tile> tilesToCheck,FighterData user, AttackMessage attackMessage, MoveCardData dataToUse) {
        this.tilesToCheck = tilesToCheck;
        this.attackMessage = attackMessage;
        this.dataToUse = dataToUse;
        this.user = user;
    }


    public void applyDamage(BattlePlayer p1, BattlePlayer p2, int ticksToAdd, List<DamageMessage> damageMessages){
        curTimer += ticksToAdd;
        for (int i = tilesToCheck.size()-1;i>=0;i--){
            Tile t = tilesToCheck.get(i);
            System.out.println("tile to damage: "  +t.x + ","+ t.y);
            if(t==p1.curtile){// if player is in one of the tiles we can damage
                applyDamage(p1, damageMessages, t);
            }if(t==p2.curtile){
                applyDamage(p2,damageMessages,t);
            }
        }
    }

    private void applyDamage(BattlePlayer p, List<DamageMessage> damageMessages, Tile t) {
        if(p.getId() == attackMessage.userID && !dataToUse.canDamageUser)
            return;
        if(p.getId() != attackMessage.userID && !dataToUse.canDamageEnemy)
            return;

        if(dataToUse.shouldStopAfterCollision && tilesToCheck.contains(t))
            tilesToCheck.remove(t);
        int calculatedDamage = p.curFighter == null? 0 :p.curFighter.calculateDamage(user,dataToUse.elementType,dataToUse.damageType,dataToUse.baseDamage);
        p.takeDamage(calculatedDamage,false);
        damageMessages.add(new DamageMessage(p.getId(),calculatedDamage,!p.curFighter.canFight()));
    }


    public boolean shouldEnd(){
        return curTimer >= dataToUse.attackDuration;
    }

}
