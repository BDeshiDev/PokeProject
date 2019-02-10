package com.company.RealTime;

import java.util.List;

public class AttackDamageTimer {
    List<Tile> tilesToCheck;
    AttackMessage attackMessage;
    MoveCardData dataToUse;

    BattlePlayer user;
    FighterData userData;

    int curTimer =0;

    public AttackDamageTimer(List<Tile> tilesToCheck, AttackMessage attackMessage, MoveCardData dataToUse, BattlePlayer user) {
        this.tilesToCheck = tilesToCheck;
        this.attackMessage = attackMessage;
        this.dataToUse = dataToUse;
        this.user = user;
        this.userData = user.curFighter;
    }


    public void applyDamage(BattlePlayer p1, BattlePlayer p2, int ticksToAdd, List<DamageMessage> damageMessages,ServerSimulationLoop ssLoop){
        curTimer += ticksToAdd;
        for (int i = tilesToCheck.size()-1;i>=0;i--){
            Tile t = tilesToCheck.get(i);
            System.out.println("tile to damage: "  +t.x + ","+ t.y);
            if(t==p1.curtile){// if player is in one of the tiles we can damage
                applyDamage(p1, damageMessages, t,ssLoop);
            }if(t==p2.curtile){
                applyDamage(p2,damageMessages,t,ssLoop);
            }
        }
    }

    private void applyDamage(BattlePlayer p, List<DamageMessage> damageMessages, Tile t,ServerSimulationLoop ssLoop) {
        if(p.getId() == attackMessage.userID && !dataToUse.canDamageUser)
            return;
        if(p.getId() != attackMessage.userID && !dataToUse.canDamageEnemy)
            return;

        if(dataToUse.shouldStopAfterCollision && tilesToCheck.contains(t))
            tilesToCheck.remove(t);
        int calculatedDamage = p.curFighter == null? 0 :p.curFighter.calculateDamage(userData,dataToUse.elementType,dataToUse.damageType,dataToUse.baseDamage);
        p.takeDamage(calculatedDamage,false);

        double damageMod = p.curFighter.getMoveModifier(dataToUse.elementType);
        ssLoop.updateTurn(user,dataToUse.calculatePowerUpRecovery(damageMod));

        damageMessages.add(new DamageMessage(p.getId(),calculatedDamage,!p.curFighter.canFight(),damageMod));
    }


    public boolean shouldEnd(){
        return curTimer >= dataToUse.attackDuration;
    }

}
