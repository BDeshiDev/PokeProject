package com.company;

import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Pokemon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.beans.EventHandler;
import java.io.IOException;

public class ButtonFactory {
    @FXML
    private Button moveButton;
    @FXML
    private Label moveName;
    @FXML
    private Label moveType;
    @FXML
    private Label power;

    public static Button getSwapButton(int width, int height, Pokemon p){
        //load the fxml file here
        Button b = new Button(p.name);
        b.setPrefWidth(width);
        b.setPrefHeight(height);
        return b;
    }

    public static Button getMoveButton(int width, int height, Move m){

        FXMLLoader loader=new FXMLLoader(ButtonFactory.class.getResource("Buttons.fxml"));

        Button button=null;
        try {
            button=loader.load();
            AnchorPane anchorPane =(AnchorPane) button.getGraphic();
            Label label1=(Label)anchorPane.getChildren().get(0);
            label1.setText(m.getName());
            Label label2=(Label)anchorPane.getChildren().get(1);
            label2.setText("Type: "+m.type.name());
            Label label3=(Label)anchorPane.getChildren().get(2);
            label3.setText("Power: "+String.valueOf(m.power));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return button;
    }
}
