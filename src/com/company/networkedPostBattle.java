package com.company;

import com.company.Exploration.PokemonStorage;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineStreamExecutable;
import com.google.gson.Gson;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class networkedPostBattle implements PokeScreen {

    @FXML
    private Button BackButton;
    @FXML
    private Label battleResultHeader;

    Scene myScene;
    private MediaPlayer mediaPlayer;

    Stage primaryStage;
    SaveData curSave;
    PokeScreen prevScreen;

    String victoryBGM = new String("src/Assets/victoryBGM.mp3");
    LineStreamExecutable lineSource  = new LineStreamExecutable();;
    boolean readyToExit = false;
    int battleResult;

    public void setBattleResult(int battleResult) {
        this.battleResult = battleResult;
    }


    @Override
    public void begin(Stage primaryStage, SaveData s, PokeScreen prevScreen) {
        Media media=new Media(new File(victoryBGM).toURI().toString());
        mediaPlayer=new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);


        this.prevScreen = prevScreen;this.primaryStage =primaryStage;this.curSave =s;
        primaryStage.setScene(myScene);
        if(battleResult == 1){
            battleResultHeader.setText("VICTORY!");
        }else if(battleResult == -1){
            battleResultHeader.setText("YOU LOST");
        }else{
            battleResultHeader.setText("BATTLE cancelled");
        }

    }

    @Override
    public void exitScreen() {
        mediaPlayer.stop();
        if(prevScreen == null){
            System.out.println("Prev screen is null");
            System.exit(-111);
        }else{
            prevScreen.begin(primaryStage,curSave,prevScreen);
        }
    }

    public void onExitPressed(){
        readyToExit = true;
    }

    public networkedPostBattle(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("networkedPostBattle.fxml"));
        loader.setController(this);
        try {
            Parent root = loader.load();
            myScene = new Scene(root,Settings.windowWidth,Settings.windowLength);
            BackButton.setOnAction(event -> exitScreen());
        }catch (IOException ioe){
            System.out.println("post battle Screen load fail");
            System.exit(-1);
        }
    }



    public boolean isComplete(){
        return  readyToExit;
    }
}
