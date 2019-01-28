package com.company.networking;

import com.company.*;
import com.google.gson.Gson;
import javafx.application.Platform;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NetworkController {
    Socket clientSocket;
    NetworkConnection clientConnection;
    @FXML
    private Label networkTextLabel;

    @FXML
    private Label textDisplayer;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button1;

    private Stage primaryStage;


    AtomicBoolean wantsToBattle = new AtomicBoolean(false);

    TrainerData td1 = new TrainerData("Ash","Charizard","Venasaur");
    TrainerData td2 = new TrainerData("Gary","Venasaur","Charizard");

    TrainerData selectedTrainer = td1;
    TrainerData enemyData = null;

    BattleController bc = new BattleController();;

    @FXML
    public void initialize() {
        button1.setOnAction(event -> selectedTrainer = td1);
        button2.setOnAction(event -> selectedTrainer = td2);
        button3.setOnAction(event -> sendMessage("button " + 3));
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void startBattle(){
        if(enemyData == null){
            System.out.println("Can't start battle yet");
        }else{
            wantsToBattle.set(false);
            sendMessage(BattleProtocol.battleStartSignal);
            bc.begin(primaryStage,new NetworkedPlayer(selectedTrainer,clientConnection),new NetworkedEnemy(enemyData,clientConnection));
        }
    }


    public void startHosting(){
        try {

            ServerThread serverThread = new ServerThread();
            Thread st =new Thread(serverThread);
            st.setDaemon(true);
            st.start();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("couldn't host");
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
                Task readTask =new Task() {
                    @Override
                    protected Object call() throws Exception {
                        while(!wantsToBattle.get()) {
                            String readString = clientConnection.readFromConnection.readLine();
                            //System.out.println("read " + readString);
                            if(readString.equals(BattleProtocol.TrainerInfoRequest)){
                                System.out.println("sending trainer info");
                                sendMessage(BattleProtocol.createMessage(selectedTrainer,BattleProtocol.TrainerInfoHeader));
                            }else if(readString.startsWith(BattleProtocol.TrainerInfoHeader)){
                                System.out.println("reading trainer info : "+readString);
                                String jsonToRead = readString.substring(BattleProtocol.TrainerInfoHeader.length());
                                System.out.println("reading trainer info : "+jsonToRead);
                                enemyData = gson.fromJson(jsonToRead,TrainerData.class);;
                                Platform.runLater(()->
                                        textDisplayer.setText(enemyData.name));
                            }
                            else if(readString.equals(BattleProtocol.battleStartSignal))// this is risky confirm if we have also have pressed the start button
                                break;
                            else
                                System.out.println("wrong message" + readString);
                        }
                        return null;
                    }
                };
                Thread rt = new Thread(readTask);
                rt.setDaemon(true);
                rt.start();
                networkTextLabel.setText("connected");
            }catch(Exception e) {
                e.printStackTrace();
                System.out.println("couldn't find host");
            }
    }
}
