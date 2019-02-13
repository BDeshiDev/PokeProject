package com.company;

import com.company.Exploration.PokemonStorage;
import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineStreamExecutable;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class BattleRecordsController implements PokeScreen {
        @FXML
        private VBox name2Box;

        @FXML
        private VBox name1Box;

        @FXML
        private Button exitButton;

        @FXML
        private AnchorPane endBattleScreen;

        @FXML
        private VBox TimeBox;

        @FXML
        private VBox WinnerBox;



    Scene myScene;
    Stage primaryStage;
    SaveData curSave;
    PokeScreen prevScreen;

    @Override
    public void begin(Stage primaryStage, SaveData s, PokeScreen prevScreen) {

        this.prevScreen = prevScreen;this.primaryStage =primaryStage;this.curSave =s;
        primaryStage.setScene(myScene);
        Gson gson = new Gson();


        try {
            BattleRecord[] br =gson.fromJson(new FileReader("src/BattleRecords"),BattleRecord[].class);
            if(br == null)
                throw new IOException();
            applyResult(br);
        }catch (IOException  ioe ){
            ioe.printStackTrace();
            System.out.println("Battle result file not be read");
            exitScreen();
        }
    }

    public void applyResult(BattleRecord[] records){
        name1Box.getChildren().clear();
        name2Box.getChildren().clear();

        WinnerBox.getChildren().clear();
        TimeBox.getChildren().clear();

        for (BattleRecord br: records) {
            name1Box.getChildren().add(new Label(br.p1));
            name2Box.getChildren().add(new Label(br.p2));
            WinnerBox.getChildren().add(new Label(br.winner));
            TimeBox.getChildren().add(new Label(br.elapsedTime+""));
        }
    }

    @Override
    public void exitScreen() {
        if(prevScreen == null){
            System.out.println("Prev screen is null");
            System.exit(-111);
        }else{
            prevScreen.begin(primaryStage,curSave,prevScreen);
        }
    }

    public void onExitPressed(){
        exitScreen();
    }

    public BattleRecordsController(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BattleRecordsScreen.fxml"));
        loader.setController(this);
        try {
            Parent root = loader.load();
            myScene = new Scene(root,Settings.windowWidth,Settings.windowLength);
            exitButton.setOnAction(event -> exitScreen());
        }catch (IOException ioe){
            System.out.println("post battle Screen load fail");
            System.exit(-1);
        }
    }
}
