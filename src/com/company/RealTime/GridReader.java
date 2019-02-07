package com.company.RealTime;

import com.company.Utilities.Animation.AnimationFactory;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
import com.google.gson.Gson;
import javafx.application.Platform;

import java.io.IOException;
import java.util.List;

class GridReader implements  Runnable{
    NetworkConnection nc;
    Grid playerGrid;
    Grid enemyGrid;
    BattlePlayer player;
    BattlePlayer enemy;



    public GridReader(NetworkConnection nc,BattlePlayer player,BattlePlayer enemy) {
        this.nc = nc;
        this.enemy = enemy;
        this.player = player;
        this.playerGrid = player.grid;
        this.enemyGrid = enemy.grid;
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
                    //System.out.println(jsonToParse);
                    setIdMessage idMesage = gson.fromJson(jsonToParse,setIdMessage.class);
                    player.setId(idMesage.playerID);
                    enemy.setId(idMesage.enemyID);
                    System.out.println("player " + player.getId() + "enemy" +  enemy.getId());
                }else if(readline.startsWith(BattleProtocol.moveMessageHeader)){
                    String jsonToParse = readline.substring(BattleProtocol.moveMessageHeader.length());
                    //System.out.println(jsonToParse);
                    moveMessage moveMessage = gson.fromJson(jsonToParse,moveMessage.class);

                    System.out.println("player #" + moveMessage.moveTargetId +"should move:" + moveMessage.dx + "," + moveMessage.dy);

                    BattlePlayer moveTarget = getPlayerFromID(moveMessage.moveTargetId);
                    if(moveTarget != null){
                        Platform.runLater((()-> moveTarget.setMove(moveMessage.dx,moveMessage.dy)));
                    }else{
                        System.out.println("invalid move id");
                    }
                }else if(readline.startsWith(BattleProtocol.attackMessageHeader)){
                    String jsonToParse = readline.substring(BattleProtocol.attackMessageHeader.length());
                    //System.out.println(jsonToParse);
                    AttackMessage am = gson.fromJson(jsonToParse,AttackMessage.class);
                    //System.out.println("animating attack named " + am.attackName);
                    if(player.getId() == am.userID){
                        List<Tile> targets = am.getTargets(playerGrid,enemyGrid);
                        for (Tile t: targets) {
                            Platform.runLater((()->AnimationFactory.getAnimByName(am.animName).toSingleLoop(t.animationView).start()));
                        }
                    }else if(enemy.getId() == am.userID){
                        List<Tile> targets = am.getTargets(enemyGrid,playerGrid);
                        for (Tile t: targets) {
                            Platform.runLater((()->AnimationFactory.getAnimByName(am.animName).toSingleLoop(t.animationView).start()));
                        }
                    }else{
                        System.out.println("invalid attack user id ");
                    }
                }else if(readline.startsWith(BattleProtocol.DamageHeader)){
                    String jsonToParse = readline.substring(BattleProtocol.DamageHeader.length());
                    DamageMessage[] messages = gson.fromJson(jsonToParse,DamageMessage[].class);
                    //System.out.println(jsonToParse);
                    for (DamageMessage dm:messages) {
                        BattlePlayer damageTarget = getPlayerFromID(dm.damagedPlayerID);
                        Platform.runLater((()->damageTarget.takeDamage(dm.damageAmount)));
                    }
                }else{
                    System.out.println("wrong message format  "+ readline);
                }
            }
        }catch (IOException ioe){
            System.out.println("read fail on gridreader");
        }

    }

    public BattlePlayer getPlayerFromID(int id){
        if(player.getId() == id){
            return player;
        }else if(enemy.getId() == id){
            return enemy;
        }else{
            return  null;
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
