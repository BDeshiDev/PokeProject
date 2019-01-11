package com.company.Exploration;

import com.company.Settings;
import com.company.Utilities.Debug.Debugger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CreditsController {

    @FXML
    private Button BackButton;

    Stage primaryStage;

    public void init(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void onBackButtonPressed(){
        if(primaryStage == null) {
            Debugger.out("Somebody didn't set the primary stage before going to the title screen");
            System.exit(-1);
        }
        try {
            //#TODO create a resource folder and write a class t odo all this instead of copy Paste
            Parent root =FXMLLoader.load(getClass().getResource("../StartScreen.fxml"));
            primaryStage.setScene(new Scene(root, Settings.windowWidth, Settings.windowLength));
            primaryStage.setTitle("Start Screen");
            primaryStage.show();
        }catch(IOException ioe){
            System.out.println("failed to load Start screen");
            System.exit(-1);//#TODO move to a creditScreen class
        }
    }
}
