package com.company.RealTime;

import com.company.networking.BattleProtocol;
import com.company.networking.JsonDataAble;

import java.util.List;

public class AttackMessage implements JsonDataAble {
    final String attackName;
    final boolean wasUsedOnLeft;//was used by someone from left side
    final int userID;
    final int userPosX, userPosY;

    public AttackMessage(String attackName, boolean wasUsedOnLeft, int userID, int userPosX, int userPosY) {
        this.attackName = attackName;
        this.wasUsedOnLeft = wasUsedOnLeft;
        this.userID = userID;
        this.userPosX = userPosX;
        this.userPosY = userPosY;
    }

    public AttackMessage(MoveCardData cardData, int userID, boolean wasUsedOnLeft,int userPosX, int userPosY){
        this(cardData.attackName,wasUsedOnLeft,userID,userPosX,userPosY);
    }

    @Override
    public String toJsonData() {
        return BattleProtocol.createMessage(this,BattleProtocol.attackMessageHeader);
    }

    public MoveCardData toMoveCard(){
        return MoveCardData.getCardByName(attackName);
    }
}
