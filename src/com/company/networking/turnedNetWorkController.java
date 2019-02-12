package com.company.networking;

import com.company.*;
import com.google.gson.Gson;
import javafx.application.Platform;

import java.io.IOException;
import java.net.*;
import java.util.Collection;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class turnedNetWorkController extends  NetWorkController{
    Socket clientSocket;
    NetworkConnection clientConnection;


    public  Stage primaryStage;
    Scene networkScene;
    PokeScreen prevScreen;
    SaveData curSave;

    TitleController titleController;

    public turnedNetWorkController(TitleController titleController) {
        super(titleController);;
    }



    public void launchServer(){
        System.out.println("launch server");
        new Thread(()->{
        try {
            ServerThread.createServer(true);
            System.out.println("server completed");
        }catch (IOException sfe){
            System.out.println("server fail on this client");
        }
        }).start();
    }


    TrainerData enemyData = null;
    TrainerData selectedTrainer = null;

    BattleController bc = new BattleController();;

    @FXML
    @Override
    public void initialize() {
        Collection<String> nameStrings = PokemonFactory.getAllMonsByName();
        getSlotCheckBox1().getItems().addAll(nameStrings);
        getSlotCheckBox2().getItems().addAll(nameStrings);
        getSlotCheckBox3().getItems().addAll(nameStrings);
        getSlotCheckBox4().getItems().addAll(nameStrings);
        getSlotCheckBox5().getItems().addAll(nameStrings);
        getSlotCheckBox6().getItems().addAll(nameStrings);

        getSlotCheckBox1().getSelectionModel().selectFirst();
        getSlotCheckBox2().getSelectionModel().selectFirst();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void startBattle(){
        if(enemyData == null){
            System.out.println("Can't start battle yet");
        }else{
            System.out.println("setting stage");
            sendMessage(BattleProtocol.battleStartSignal);
            bc.begin(primaryStage,new NetworkedPlayer(selectedTrainer, clientConnection),new NetworkedEnemy(enemyData,clientConnection),this,null,curSave);
            enemyData = null;
            System.out.println("set stage");
        }
    }


    public void findHost(){
        System.out.println("finding host");
            try {
                clientSocket = new Socket(getServerIPLabel().getText(),ServerThread.portToUse);
                clientConnection  = new NetworkConnection(clientSocket);
                Gson gson = new Gson();

                selectedTrainer = new TrainerData(getPlayerNameField().getText(),getSlotCheckBox1().getValue(),getSlotCheckBox2().getValue(),getSlotCheckBox3().getValue()
                        ,getSlotCheckBox4().getValue(),getSlotCheckBox5().getValue(),getSlotCheckBox6().getValue());

                Task readTask =new Task() {
                    @Override
                    protected Object call() throws Exception {
                        //System.out.println("read " + readString);
                        boolean hasGotInfo = false;
                        while (!hasGotInfo){
                            String readString = clientConnection.readFromConnection.readLine();
                            if(readString.equals(BattleProtocol.TrainerInfoRequest)){
                                System.out.println("sending trainer info");
                                sendMessage(BattleProtocol.createMessage(selectedTrainer,BattleProtocol.TrainerInfoHeader));
                            }else if(readString.startsWith(BattleProtocol.TrainerInfoHeader)){
                                System.out.println("reading trainer info : "+readString);
                                String jsonToRead = readString.substring(BattleProtocol.TrainerInfoHeader.length());
                                System.out.println("reading trainer info : "+jsonToRead);
                                enemyData = gson.fromJson(jsonToRead,TrainerData.class);;;
                                hasGotInfo = true;
                            }
                        }
                        Platform.runLater(()-> {
                            startBattle();
                        });
                        return null;
                    }
                };
                Thread rt = new Thread(readTask);
                rt.setDaemon(true);
                rt.start();
            }catch(UnknownHostException uoe) {
                uoe.printStackTrace();
                System.out.println("couldn't find host");
            }catch(IOException ioe) {
                ioe.printStackTrace();
                System.out.println("client io error");
            }
    }
}
