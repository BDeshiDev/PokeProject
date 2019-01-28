package com.company.networking;
import com.company.AttackCommand;
import com.company.BattleCommand;
import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Pokemon;
import com.company.pcTrainer;

import java.io.PrintWriter;

public class NetworkedPlayer extends pcTrainer {
    PrintWriter writer;
    public NetworkedPlayer(TrainerData td,NetworkConnection connection) {
        super(td);
        writer = connection.writeToConnection;
    }

    @Override
    public void setCommand(Move m, Pokemon user) {
        AttackCommand selectedAttack = new AttackCommand(user,m,enemySlot);
        super.setCommand(selectedAttack);
        writer.println((BattleProtocol.createMessage(new AttackCommandData(selectedAttack),BattleProtocol.AttackCommandHeader)));
        //System.out.println(BattleProtocol.createMessage(new AttackCommandData(selectedAttack),BattleProtocol.AttackCommandHeader));
    }

    @Override
    public void endTurnPrep() {
        super.endTurnPrep();
        if(!waitingForSwap)
            writer.println(BattleProtocol.TurnEndOkay);
    }



    /*
    @Override
    public void setCommandToExecuteAtTurnEnd(BattleCommand commandToExecuteAtTurnEnd) {
        super.setCommandToExecuteAtTurnEnd(commandToExecuteAtTurnEnd);
        System.out.println(BattleProtocol.createMessage(new AttackCommandData(selectedAttack),BattleProtocol.AttackCommandHead
    }*/
}
