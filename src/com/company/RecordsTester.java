package com.company;

import javafx.application.Application;
import javafx.stage.Stage;

public class RecordsTester extends Application {
    @Override
    public void start(Stage primaryStage) throws  Exception{
        BattleRecordsController brc = new BattleRecordsController();
        brc.begin(primaryStage,SaveData.newGameData(),null);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
