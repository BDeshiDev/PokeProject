package com.company;

import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Pokemon;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.beans.EventHandler;

public class ButtonFactory {
    public static Button getSwapButton(int width, int height, Pokemon p){
        //load the fxml file here
        Button b = new Button(p.name);
        b.setPrefWidth(width);
        b.setPrefHeight(height);
        return b;
    }

    public static Button getMoveButton(int width, int height, Move m){
        //load the fxml file here
        Button b = new Button(m.getName());
        b.setPrefSize(width,height);
        return b;
    }
}
