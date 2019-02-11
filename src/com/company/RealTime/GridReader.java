package com.company.RealTime;

import com.company.Utilities.Animation.AnimationFactory;
import com.company.Utilities.Animation.SpriteAnimation;
import com.company.networking.BattleProtocol;
import com.company.networking.NetworkConnection;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GridReader implements  Runnable{
    NetworkConnection nc;
    Grid playerGrid;
    Grid enemyGrid;
    BattlePlayer player;
    BattlePlayer enemy;

    List<SpriteAnimation> currentlyPlayingAnimations = new ArrayList<>();// use this to stop playing animations when needed


    public GridReader(NetworkConnection nc,BattlePlayer player,BattlePlayer enemy) {
        this.nc = nc;
        this.enemy = enemy;
        this.player = player;
        this.playerGrid = player.grid;
        this.enemyGrid = enemy.grid;

        player.init();
        enemy.init();
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        try {
            while (true){
                //System.out.println("ready to read");
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
                }else if(readline.startsWith(BattleProtocol.TurnChargeHeader)){
                    String jsonToParse = readline.substring(BattleProtocol.TurnChargeHeader.length());
                    //System.out.println("turn updates:"+jsonToParse );
                    TurnChargeMessage[] messages = gson.fromJson(jsonToParse,TurnChargeMessage[].class);
                    for (TurnChargeMessage tcm:messages) {
                        BattlePlayer targetPlayer = getPlayerFromID(tcm.targetId);
                        if(targetPlayer!= null)
                            Platform.runLater(()->targetPlayer.updateTurn(tcm));
                    }
                }else if(readline.startsWith(BattleProtocol.attackMessageHeader)){
                    String jsonToParse = readline.substring(BattleProtocol.attackMessageHeader.length());
                    //System.out.println(jsonToParse);
                    AttackMessage am = gson.fromJson(jsonToParse,AttackMessage.class);
                    //System.out.println("animating attack named " + am.attackName);
                    if(player.getId() == am.userID){
                        MoveCardData mcd = am.toMoveCard();
                        List<Tile> targets = mcd.getTargets(playerGrid,am,getPlayerFromID(am.userID));
                        for (Tile t: targets) {
                            Platform.runLater(()->{
                                AnimationFactory.getAnimByName(mcd.animName).toSingleLoop(t.animationView).start();
                                new MediaPlayer(new Media(new File(mcd.sfxName).toURI().toString())).setAutoPlay(true);
                            });
                        }
                    }else if(enemy.getId() == am.userID){
                        MoveCardData mcd = am.toMoveCard();
                        List<Tile> targets = mcd.getTargets(enemyGrid,am,getPlayerFromID(am.userID));
                        for (Tile t: targets) {
                            Platform.runLater(()->{
                                AnimationFactory.getAnimByName(mcd.animName).toSingleLoop(t.animationView).start();
                                new MediaPlayer(new Media(new File(mcd.sfxName).toURI().toString())).setAutoPlay(true);
                            });
                        }
                    }else{
                        System.out.println("invalid attack userData id ");
                    }
                }else if(readline.startsWith(BattleProtocol.DamageHeader)){
                    String jsonToParse = readline.substring(BattleProtocol.DamageHeader.length());
                    DamageMessage[] messages = gson.fromJson(jsonToParse,DamageMessage[].class);
                    //System.out.println(jsonToParse);
                    for (DamageMessage dm:messages) {
                        BattlePlayer damageTarget = getPlayerFromID(dm.damagedPlayerID);
                        Platform.runLater(()->{
                            damageTarget.takeDamage(dm);
                            Media hitSound=null;
                            if(dm.damageMod >= 1.5)
                                hitSound = new Media(new File("src/Assets/SFX/SuperHitSFX.mp3").toURI().toString());
                            else if(dm.damageMod <= .5)
                                hitSound = new Media(new File("src/Assets/SFX/weakHitSFX.mp3").toURI().toString());
                            else
                                hitSound = new Media(new File("src/Assets/SFX/NormalHItSFX.mp3").toURI().toString());
                            new MediaPlayer(hitSound).setAutoPlay(true);
                        });
                    }
                }else if(readline.startsWith(BattleProtocol.PauseOrderMessge)){
                    Platform.runLater(()-> player.disableActions(true));
                }else if(readline.startsWith(BattleProtocol.ResumeOrderHeader)){
                    Platform.runLater(()-> player.disableActions(false));
                }else if(readline.startsWith(BattleProtocol.SwapRequestHeader)){
                    String jsonToParse = readline.substring(BattleProtocol.SwapRequestHeader.length());
                    SwapMessage sm = gson.fromJson(jsonToParse,SwapMessage.class);
                    Platform.runLater(()->player.handleSwapRequest(sm.isCancellable));//we assume that the swap message already reefers to the correct player
                }else if(readline.startsWith(BattleProtocol.SwapEventHeader)){
                    String jsonToParse = readline.substring(BattleProtocol.SwapEventHeader.length());
                    SwapMessage sm = gson.fromJson(jsonToParse,SwapMessage.class);
                    BattlePlayer swapper = getPlayerFromID(sm.swapperID);
                    if(swapper != null)
                        Platform.runLater(()->swapper.handleSwap(sm.idToSwapWith));
                }
                else if(readline.startsWith(BattleProtocol.WinSignal)){
                    System.out.println("you win");
                    break;
                }else if(readline.startsWith(BattleProtocol.LoseSignal)){
                    System.out.println("you lose");
                    break;
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
