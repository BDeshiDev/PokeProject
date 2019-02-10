package com.company.RealTime;

import com.company.Pokemon.Moves.Move;
import com.company.networking.BattleProtocol;
import com.company.networking.JsonDataAble;

import java.util.List;

public class TurnConfirmMessage implements JsonDataAble {
    int id;
    List<MoveCardData> selectedMoves;

    public TurnConfirmMessage(int id, List<MoveCardData> selectedMoves) {
        this.id = id;
        this.selectedMoves = selectedMoves;
    }

    @Override
    public String toJsonData() {
        return BattleProtocol.createMessage(this,BattleProtocol.turnConfirmHeader);
    }
}
