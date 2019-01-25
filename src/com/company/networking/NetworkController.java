package com.company.networking;

import com.sun.jmx.snmp.ThreadContext;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

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

    int selectedButton;


    @FXML
    public void initialize() {
        button1.setOnAction(event -> sendMessage(1));
        button2.setOnAction(event -> sendMessage(2));
        button3.setOnAction(event -> sendMessage(3));
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

    public void sendMessage(int buttonNo){
        if(clientConnection == null){
            System.out.println("connect first");
            return;
        }
        try {
            clientConnection.writeToConnection.println("button " + buttonNo);
            clientConnection.writeToConnection.flush();
        }catch (Exception e){
            System.out.println("send fail");
        }
    }

    public void findHost(){
        Platform.runLater(()->{
            try {
                clientSocket = new Socket(InetAddress.getLocalHost(),ServerThread.portToUse);
                clientConnection  = new NetworkConnection(clientSocket);
                Task readTask =new Task() {
                    @Override
                    protected Object call() throws Exception {
                        while(true) {
                            String readString = clientConnection.readFromConnection.readLine();
                            Platform.runLater(()->textDisplayer.setText(readString));
                            if(readString.equals("WIN"))
                                break;
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
        });
    }
}
