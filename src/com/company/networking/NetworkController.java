package com.company.networking;

import com.company.*;
import com.google.gson.Gson;
import javafx.application.Platform;
import java.net.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NetworkController {
    Socket clientSocket;
    NetworkConnection clientConnection;

    @FXML
    private ComboBox<String> SlotCheckBox1;

    @FXML
    private ComboBox<String> SlotCheckBox2;

    @FXML
    private ComboBox<String> SlotCheckBox3;

    @FXML
    private ComboBox<String> SlotCheckBox4;

    @FXML
    private ComboBox<String> SlotCheckBox5;

    @FXML
    private ComboBox<String> SlotCheckBox6;

    @FXML
    private TextField playerNameField;


    private Stage primaryStage;


    AtomicBoolean wantsToBattle = new AtomicBoolean(false);

    TrainerData enemyData = null;
    TrainerData selectedTrainer = null;

    BattleController bc = new BattleController();;

    @FXML
    public void initialize() {
        Collection<String> nameStrings = PokemonFactory.getAllMonsByName();
        SlotCheckBox1.getItems().addAll(nameStrings);
        SlotCheckBox2.getItems().addAll(nameStrings);
        SlotCheckBox3.getItems().addAll(nameStrings);
        SlotCheckBox4.getItems().addAll(nameStrings);
        SlotCheckBox5.getItems().addAll(nameStrings);
        SlotCheckBox6.getItems().addAll(nameStrings);

        SlotCheckBox1.getSelectionModel().selectFirst();
        SlotCheckBox2.getSelectionModel().selectFirst();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void startBattle(){
        if(enemyData == null){
            System.out.println("Can't start battle yet");
        }else{
            wantsToBattle.set(false);
            System.out.println("setting stage");
            sendMessage(BattleProtocol.battleStartSignal);
            bc.begin(primaryStage,new NetworkedPlayer(selectedTrainer, clientConnection),new NetworkedEnemy(enemyData,clientConnection));
            enemyData = null;
            System.out.println("set stage");
        }
    }

    public void sendMessage(String messageToSend){
        if(clientConnection == null){
            System.out.println("connect first");
            return;
        }
        try {
            clientConnection.writeToConnection.println(messageToSend);
            System.out.println("message sent");
        }catch (Exception e){
            System.out.println("send fail");
        }
    }

    public void findHost(){
            try {
                clientSocket = new Socket(InetAddress.getLocalHost(),ServerThread.portToUse);
                clientConnection  = new NetworkConnection(clientSocket);
                Gson gson = new Gson();

                selectedTrainer = new TrainerData(playerNameField.getText(),SlotCheckBox1.getValue(),SlotCheckBox2.getValue(),SlotCheckBox3.getValue()
                        ,SlotCheckBox4.getValue(),SlotCheckBox5.getValue(),SlotCheckBox6.getValue());

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
                            /*
                            if(readString.equals(BattleProtocol.battleStartSignal))// this is risky confirm if we have also have pressed the start button
                                break;
                            else
                                System.out.println("wrong message" + readString);*/
                        return null;
                    }
                };
                Thread rt = new Thread(readTask);
                rt.setDaemon(true);
                rt.start();
            }catch(Exception e) {
                e.printStackTrace();
                System.out.println("couldn't find host");
            }
    }
}
