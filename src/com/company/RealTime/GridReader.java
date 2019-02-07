package com.company.RealTime;

import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
import com.google.gson.Gson;
import javafx.application.Platform;

import java.io.IOException;

class GridReader implements  Runnable{
    NetworkConnection nc;
    Grid gridToUpdate;
    BattlePlayer player;
    BattlePlayer enemy;



    public GridReader(NetworkConnection nc,Grid gridToUpdate,BattlePlayer player,BattlePlayer enemy) {
        this.nc = nc;
        this.gridToUpdate = gridToUpdate;
        this.enemy = enemy;
        this.player = player;
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        try {
            while (true){
                System.out.println("ready to read");
                String readline = nc.readFromConnection.readLine();
                if(readline.startsWith(BattleProtocol.setIdMessageHeader)){
                    String jsonToParse = readline.substring(BattleProtocol.setIdMessageHeader.length());
                    System.out.println(jsonToParse);
                    setIdMessage idMesage = gson.fromJson(jsonToParse,setIdMessage.class);
                    player.setId(idMesage.playerID);
                    enemy.setId(idMesage.enemyID);
                    System.out.println("player " + player.getId() + "enemy" +  enemy.getId());
                }if(readline.startsWith(BattleProtocol.moveMessageHeader)){
                    String jsonToParse = readline.substring(BattleProtocol.moveMessageHeader.length());
                    System.out.println(jsonToParse);
                    moveMessage moveMessage = gson.fromJson(jsonToParse,moveMessage.class);

                    System.out.println("player #" + moveMessage.moveTargetId +"should move:" + moveMessage.dx + "," + moveMessage.dy);
                    if(player.getId() == moveMessage.moveTargetId){
                        Platform.runLater((()-> player.setMove(moveMessage.dx,moveMessage.dy)));
                    }else if(enemy.getId() == moveMessage.moveTargetId){
                        Platform.runLater((()-> enemy.setMove(moveMessage.dx,moveMessage.dy)));
                    }else{
                        System.out.println("invalid move id");
                    }
                }else{
                    System.out.println("wrong message format + "+ readline);
                }
            }
        }catch (IOException ioe){
            System.out.println("read fail on gridreader");
        }

    }
}

class moveMessage{
    int moveTargetId;
    int dx, dy;

    public moveMessage(int moveTargetId, int dx, int dy) {
        this.moveTargetId = moveTargetId;
        this.dx = dx;
        this.dy = dy;
    }
}

class setIdMessage{
    int playerID;
    int enemyID;

    public setIdMessage(int playerID, int enemyID) {
        this.playerID = playerID;
        this.enemyID = enemyID;
    }
}
