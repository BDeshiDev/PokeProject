package com.company.RealTime;

import com.company.networking.BattleProtocol;
import com.company.networking.JsonDataAble;

import java.util.List;

public class AttackMessage implements JsonDataAble {
    String attackName;
    int userID;
    int userPosX, userPosY;


    public AttackMessage(String attackName, String animName, int userID, int userPosX, int userPosY, int attackDuration, int damagePerHit, TargetPattern targetPattern, boolean shouldTargetOwnGrid, boolean shouldStopAfterCollision) {
        this.attackName = attackName;
        this.userID = userID;
        this.userPosX = userPosX;
        this.userPosY = userPosY;
    }

    public AttackMessage(MoveCardData rtmd, int userID, int userPosX, int userPosY){
        this(rtmd.attackName,rtmd.animName,userID,userPosX,userPosY,rtmd.attackDuration,rtmd.baseDamage,rtmd.targetPattern,rtmd.shouldTargetOwnGrid,rtmd.shouldStopAfterCollision);
    }

    @Override
    public String toJsonData() {
        return BattleProtocol.createMessage(this,BattleProtocol.attackMessageHeader);
    }

    public MoveCardData toMoveCard(){
        return MoveCardData.getCardByName(attackName);
    }
}
