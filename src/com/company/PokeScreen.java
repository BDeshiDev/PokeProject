package com.company;

import com.company.SaveData;
import javafx.stage.Stage;
/*
* interface for mutually independent screens that require a scene and a savedata to start working
* */
public interface PokeScreen {
    void begin(Stage primaryStage, SaveData s,PokeScreen prevScreen);
    void exitScreen();
}
