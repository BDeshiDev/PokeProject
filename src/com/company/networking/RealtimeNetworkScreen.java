package com.company.networking;

import com.company.Pokemon.PokemonSaveData;
import com.company.Pokemon.Stats.Level;
import com.company.RealTime.*;
import com.company.Settings;
import com.company.TitleController;
import com.company.networkedPostBattle;
import com.google.gson.Gson;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;

public  class RealtimeNetworkScreen extends NetWorkController  {
    networkedPostBattle postBattleScreen;
    public RealtimeNetworkScreen(TitleController titleController, networkedPostBattle postBattleScreen) {
        super(titleController,false);
        this.postBattleScreen = postBattleScreen;
    }

    @Override
    public void launchServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(Settings.realTimePort, 10, InetAddress.getByName(getServerIPLabel().getText()));
            ServerRealTime.createServers(serverSocket);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    public void initialize() {
        Collection<String> nameStrings = FighterData.getAll();
        getSlotCheckBox1().getItems().addAll(nameStrings);
        getSlotCheckBox2().getItems().addAll(nameStrings);
        getSlotCheckBox3().getItems().addAll(nameStrings);
        getSlotCheckBox4().getItems().addAll(nameStrings);
        getSlotCheckBox5().getItems().addAll(nameStrings);
        getSlotCheckBox6().getItems().addAll(nameStrings);

        getSlotCheckBox1().getSelectionModel().selectFirst();
        getSlotCheckBox2().getSelectionModel().select(1);
    }

    @Override
    public void startBattle() {

    }

    public void transitionToResults(int battleResult){
        if(postBattleScreen != null){
        postBattleScreen.setBattleResult(battleResult);
        postBattleScreen.begin(primaryStage,curSave,titleController);
        }else {
            System.out.println("no post battle");
            System.exit(-1);
        }
    }

    @Override
    public void findHost() {
        TrainerData trainerData = new TrainerData(getPlayerNameField().getText(),
                new PokemonSaveData(getSlotCheckBox1().getValue(), Level.maxLevel), new PokemonSaveData(getSlotCheckBox2().getValue(), Level.maxLevel),
                new PokemonSaveData(getSlotCheckBox3().getValue(), Level.maxLevel), new PokemonSaveData(getSlotCheckBox4().getValue(), Level.maxLevel)
                ,new PokemonSaveData(getSlotCheckBox5().getValue(), Level.maxLevel), new PokemonSaveData(getSlotCheckBox6().getValue(), Level.maxLevel));
        TrainerData enemyData = null;
        Gson gson = new Gson();
        try {
            Socket socket = new Socket(getServerIPLabel().getText(), Settings.realTimePort);
            System.out.println("in ");
            NetworkConnection nc = new NetworkConnection(socket);
            String readLine = nc.readFromConnection.readLine();

            if(readLine.startsWith(BattleProtocol.TrainerInfoRequest)){
                nc.writeToConnection.println(trainerData.toJsonData());
                enemyData = ServerRealTime.getTrainerData(nc,gson);
            }

            List<FighterData> playerParty = FighterData.convertTrainerData(trainerData);
            List<FighterData> enemyParty = FighterData.convertTrainerData(enemyData);

            BattleScreenController bsc= new BattleScreenController();
            Grid grid = new Grid(bsc.getPlayerGridParent());
            NetworkedGridPlayer player  = new NetworkedGridPlayer(new ImageView(),grid,true,bsc.battleScene,bsc.getPlayerDisplay(),playerParty,new NetworkConnection(socket),bsc);
            BattlePlayer enemy = new BattlePlayer(new ImageView(),grid,false,bsc.getEnemyDisplay(),enemyParty);
            GridReader reader = new GridReader(nc,player,enemy,this);
            bsc.begin(primaryStage,getCurSave(),getTitleController());

            new Thread(reader).start();
        }catch (UnknownHostException uhe){
            System.out.println("host name wrong");
        }catch (IOException ioe){
            System.out.println("couldn't connect");
        }

    }
}
