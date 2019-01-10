package com.company;

import com.company.PokeTab;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.company.PokeTab.pokemon;

public class PokeInfo {
    @FXML
    Label pokemonName;
    @FXML
    Label pokemonType;
    @FXML
    Label nameAndLevel;
    @FXML
    Label pokemonNo;
    @FXML
    ImageView pokemonImage;

    public void handle() {
        pokemonName.setText("Pokemon Name:  "+pokemon.name);
        pokemonType.setText("Type:  "+pokemon.t1+"    "+pokemon.t2);
        nameAndLevel.setText(pokemon.name+"    Level: "+pokemon.getLevel());
        pokemonImage.setImage(new Image(pokemon.frontImage));
    }
}
