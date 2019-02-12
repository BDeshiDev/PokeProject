package com.company.networking;

import com.company.*;
import com.company.Utilities.Debug.Debugger;
import com.company.networking.NetworkConnection;
import com.company.networking.TrainerData;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import sun.security.x509.AVA;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;

public abstract class NetWorkController implements PokeScreen {
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

    @FXML
    private TextField serverIPLabel;

    @FXML
    private ImageView iconView;

    @FXML
    private ImageView iconView1;

    @FXML
    private ImageView iconView5;

    @FXML
    private ImageView iconView4;

    @FXML
    private ImageView iconView3;

    @FXML
    private ImageView iconView2;

    @FXML
    private Button exitScreenButton;
    @FXML
    private Button StartButton;
    @FXML
    private Button serverLaunchButton;

    @FXML
    private Button localButton;

    @FXML
    private CheckBox AiLabel;


    MediaPlayer mediaPlayer;

    public ComboBox<String> getSlotCheckBox1() {
        return SlotCheckBox1;
    }

    public ComboBox<String> getSlotCheckBox2() {
        return SlotCheckBox2;
    }

    public ComboBox<String> getSlotCheckBox3() {
        return SlotCheckBox3;
    }

    public ComboBox<String> getSlotCheckBox4() {
        return SlotCheckBox4;
    }

    public ComboBox<String> getSlotCheckBox5() {
        return SlotCheckBox5;
    }

    public ComboBox<String> getSlotCheckBox6() {
        return SlotCheckBox6;
    }

    public TextField getPlayerNameField() {
        return playerNameField;
    }

    public TextField getServerIPLabel() {
        return serverIPLabel;
    }

    public ImageView getIconView() {
        return iconView;
    }

    public ImageView getIconView1() {
        return iconView1;
    }

    public ImageView getIconView5() {
        return iconView5;
    }

    public ImageView getIconView4() {
        return iconView4;
    }

    public ImageView getIconView3() {
        return iconView3;
    }

    public ImageView getIconView2() {
        return iconView2;
    }

    public Button getExitScreenButton() {
        return exitScreenButton;
    }

    public Button getStartButton() {
        return StartButton;
    }

    public Button getServerLaunchButton() {
        return serverLaunchButton;
    }

    public Button getLocalButton() {
        return localButton;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public NetworkConnection getClientConnection() {
        return clientConnection;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getNetworkScene() {
        return networkScene;
    }

    public PokeScreen getPrevScreen() {
        return prevScreen;
    }

    public SaveData getCurSave() {
        return curSave;
    }

    public TitleController getTitleController() {
        return titleController;
    }

    public TrainerData getEnemyData() {
        return enemyData;
    }

    public TrainerData getSelectedTrainer() {
        return selectedTrainer;
    }

    public CheckBox getAiLabel() {
        return AiLabel;
    }

    public Stage primaryStage;
    Scene networkScene;
    PokeScreen prevScreen;
    SaveData curSave;

    TitleController titleController;

    public NetWorkController(TitleController titleController) {
        this(titleController,true);
    }

    public NetWorkController(TitleController titleController, boolean launchOnStart) {
        this.titleController= titleController;


        FXMLLoader loader = new FXMLLoader(getClass().getResource("NetworkScreen.fxml"));
        loader.setController(this);
        try {
            networkScene =new Scene(loader.load(), Settings.windowWidth,Settings.windowLength);
            Debugger.out("networkscene constructed");
            exitScreenButton.setOnAction(event -> exitScreen());
            serverLaunchButton.setOnAction(event -> launchServer());
            StartButton.setOnAction(event -> findHost());
            localButton.setOnAction(event -> serverIPLabel.setText("127.0.0.1"));
        }catch (IOException ioe){
            System.out.println("couldn't create networkscene screen");
            System.exit(-1);
        }
        if(launchOnStart)
            launchServer();
    }

    public void begin(Stage primaryStage, SaveData s, PokeScreen prevScreen) {
        this.primaryStage = primaryStage;
        this.curSave =s;
        this.prevScreen = prevScreen;

        Media media=new Media(new File("src/Assets/The Arrival (BATTLE II).mp3").toURI().toString());
        mediaPlayer=new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        primaryStage.setTitle("networkScreen");
        primaryStage.setScene(networkScene);
    }

    public abstract void launchServer();

    public void exitScreen() {
        System.out.println( "exiting");
        mediaPlayer.stop();
        if(titleController == null){
            System.out.println("no prev screen");
            System.exit(-1);
        }else{
            titleController.begin(primaryStage,curSave,null);
        }
    }

    TrainerData enemyData = null;
    TrainerData selectedTrainer = null;


    @FXML
    public abstract void initialize();
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public abstract void startBattle();

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

    public abstract void findHost();
}
