package com.company.RealTime;

import com.company.networking.BattleProtocol;
import com.company.networking.JsonDataAble;

public class TurnChargeMessage {
    double chargeAmount;
    int targetId;
    boolean startTurn;

    public TurnChargeMessage(double chargeAmount, int targetId, boolean startTurn) {
        this.chargeAmount = chargeAmount;
        this.targetId = targetId;
        this.startTurn = startTurn;
    }

    public static String convertUpdateToMessage(TurnChargeMessage[] updates){
        return BattleProtocol.createMessage(updates,BattleProtocol.TurnChargeHeader);
    }
}
