package com.company;

import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Pokemon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static com.company.PokeTab.pokemon;

public class PokeMove {
    @FXML
    VBox vbox;
    @FXML
    Label nameAndLevel;
    @FXML
    ImageView pokemonImage;

    public void handle() {
//        attack1.setText(pokemon.getMove(1).type+"     "+pokemon.getMove(1).getName());
//        pp1.setText(pokemon.getMove(1).power+"/"+pokemon.getMove(1).maxPp);
//        attack2.setText(pokemon.getMove(2).type+"     "+pokemon.getMove(2).getName());
//        pp2.setText(pokemon.getMove(2).power+"/"+pokemon.getMove(2).maxPp);
//        attack3.setText(pokemon.getMove(3).type+"     "+pokemon.getMove(3).getName());
//        pp3.setText(pokemon.getMove(3).power+"/"+pokemon.getMove(3).maxPp);
//        attack4.setText(pokemon.getMove(4).type+"     "+pokemon.getMove(4).getName());
//        pp4.setText(pokemon.getMove(4).power+"/"+pokemon.getMove(4).maxPp);
//        nameAndLevel.setText(pokemon.name+"    Level: "+pokemon.getLevel());

        vbox.getChildren().clear();
        pokemonImage.setImage(new Image(pokemon.frontImage));

        for (Move m:
             pokemon.getMoves()) {
            Button button=ButtonFactory.getMoveButton(20,10,m);
            button.setMaxHeight(100);
            button.setMaxWidth(50);
            vbox.getChildren().add(button);
        }
    }
}
