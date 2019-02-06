package com.company;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import pokemap.Position;


public class Main extends  Application {
    @Override
    public void start(Stage primaryStage) throws  Exception{
        PokemonFactory.getBlastoise();
        TitleController titleController = new TitleController();
        titleController.begin(primaryStage,SaveData.newGameData(),null);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


