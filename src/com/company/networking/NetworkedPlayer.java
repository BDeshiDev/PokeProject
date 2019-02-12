package com.company.networking;
import com.company.*;
import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Pokemon;
import com.company.Pokemon.PokemonSaveData;
import com.company.Pokemon.Stats.Level;
import com.company.RealTime.*;
import com.google.gson.Gson;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;

public class NetworkedPlayer extends pcTrainer {
    PrintWriter writer;
    public NetworkedPlayer(TrainerData td,NetworkConnection connection) {
        super(td);
        writer = connection.writeToConnection;
    }
    //#TODO where are we sending swap events that happen before turn ENDS??? Perhaps that's the source of the bug

    @Override
    public void setCommand(Move m, Pokemon user) {
        AttackCommand selectedAttack = new AttackCommand(user,m,enemySlot);
        super.setCommand(selectedAttack);
        writer.println(selectedAttack.toJsonData());
    }

    @Override
    public void endBattle() {
        super.endBattle();
        writer.println(canFight()? BattleProtocol.WinSignal:BattleProtocol.LoseSignal);
    }

    @Override
    public void endTurnPrep() {
        super.endTurnPrep();
        if(!waitingForSwap)
            writer.println(BattleProtocol.TurnEndOkay);
    }


    @Override
    public void setCommand(BattleCommand battleCommand) {
        super.setCommand(battleCommand);
        if(battleCommand != null)
            writer.println(battleCommand.toJsonData());
    }
}

