package com.company.Exploration;

import com.company.*;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;
import com.company.BattleResult;
import com.company.Utilities.TextHandler.LineStreamExecutable;
import com.google.gson.Gson;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.io.*;


public class PostBattleController implements PokeScreen {

    @FXML
    private VBox TextParentPane;
    @FXML
    private Button BackButton;

    Scene myScene;
    private MediaPlayer mediaPlayer;

    Stage primaryStage;
    SaveData curSave;
    PokeScreen prevScreen;

    String victoryBGM = new String("src/Assets/victoryBGM.mp3");
    LineStreamExecutable lineSource  = new LineStreamExecutable();;
    boolean readyToExit = false;

    @Override
    public void begin(Stage primaryStage, SaveData s, PokeScreen prevScreen) {
        Media media=new Media(new File(victoryBGM).toURI().toString());
        mediaPlayer=new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);


        this.prevScreen = prevScreen;this.primaryStage =primaryStage;this.curSave =s;
        Gson gson = new Gson();
        try {
            BattleResult br =gson.fromJson(new FileReader("BattleResult.txt"),BattleResult.class);
            if(br == null)
                throw new IOException();
            applyResult(primaryStage,br,s.pcTrainer);
        }catch (IOException  ioe ){
            ioe.printStackTrace();
            System.out.println("Battle result file not be read");
            exitScreen();
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

    public PostBattleController(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PostBattleScreen.fxml"));
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

    public void applyResult(Stage primaryStage, BattleResult battleResult, pcTrainer player) {
        //continue here
        readyToExit = false;
        TextParentPane.getChildren().clear();

        if(battleResult.hasRun)
            lineSource.push("Successfully ran from battle");
        else
            player.applyXp(battleResult,lineSource);//cowards don't get xp

        for (Pokemon p : battleResult.caughtMons) {
            lineSource.push(p.name + " was caught");
            if(player.isPartyFull()){
                lineSource.push("Party full. " + p.name + " was sent to storage...");
                PokemonStorage.addMon(p);
            }else {
                player.addMonToParty(p);
                lineSource.push(p.name + " was added to "+player.name+"'s party.");
            }
        }
        primaryStage.setScene(myScene);

        new AnimationTimer(){
            long now,prevNow;
            @Override
            public void start() {
                super.start();
                now = prevNow = System.nanoTime();
            }

            @Override
            public void handle(long time) {
                now = System.nanoTime();
                double delta =(now - prevNow)/1e9;
                prevNow = now;
                if(lineSource.isComplete()){
                    Debugger.out("Ready to exit post battle screen");
                    //readyToExit = true;
                    stop();
                }
                lineSource.continueExecution(delta,TextParentPane);
            }
        }.start();
    }

    public boolean isComplete(){
        return  readyToExit;
    }
}
