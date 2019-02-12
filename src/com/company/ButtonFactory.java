package com.company;

import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Pokemon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
//        Button b = new Button(p.name);
//        b.setPrefWidth(width);
//        b.setPrefHeight(height);
//        b.setStyle("    -fx-background-color: #b3d57a;\n" +
//                "    -fx-background-radius: 30;\n" +
//                "    -fx-background-insets: .5;");
        FXMLLoader loader=new FXMLLoader(ButtonFactory.class.getResource("swapbutton.fxml"));

        Button button=null;
        try {
            button = loader.load();
            button.setPrefHeight(height);
            button.setPrefWidth(width);
            AnchorPane anchorPane =(AnchorPane) button.getGraphic();
            ImageView imageView=(ImageView)anchorPane.getChildren().get(0);
            imageView.setImage(new Image(p.frontImage));
            Label label2=(Label)anchorPane.getChildren().get(1);
            label2.setText(p.name);
        }catch (IOException e){
            e.printStackTrace();
        }

        return button;
    }

    public static Button getSwapButton(int width, int height, String name,Image image){
        FXMLLoader loader=new FXMLLoader(ButtonFactory.class.getResource("swapbutton.fxml"));
        Button button=null;
        try {
            button = loader.load();
            button.setPrefHeight(height);
            button.setPrefWidth(width);
            AnchorPane anchorPane =(AnchorPane) button.getGraphic();
            ImageView imageView=(ImageView)anchorPane.getChildren().get(0);
            imageView.setImage(image);
            Label label2=(Label)anchorPane.getChildren().get(1);
            label2.setText(name);
        }catch (IOException e){
            e.printStackTrace();
        }

        return button;
    }


    public static Button getMoveButton(int width, int height, Move m){

        FXMLLoader loader=new FXMLLoader(ButtonFactory.class.getResource("Buttons.fxml"));

        Button button=null;
        try {
            button=loader.load();
            button.setPrefHeight(height);
            button.setPrefWidth(width);
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
