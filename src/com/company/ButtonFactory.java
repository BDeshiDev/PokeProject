package com.company;

import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Pokemon;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.beans.EventHandler;
import java.io.IOException;

public class ButtonFactory {

    private static Button moveButton;

    public static Button getSwapButton(int width, int height, Pokemon p){
        //load the fxml file here
        Button b = new Button(p.name);
        b.setPrefWidth(width);
        b.setPrefHeight(height);
        return b;
    }

    public static Button getMoveButton(int width, int height, Move m){
        //load the fxml file here
        FXMLLoader loader=new FXMLLoader(ButtonFactory.class.getResource("Buttons.fxml"));

        try {
            moveButton = loader.load();
            moveButton.setText(m.getName());
        } catch (Exception e) {
            System.out.println("button load failed");
        }

        moveButton.setPrefSize(width,height);
        return moveButton;
    }
}
