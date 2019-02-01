package com.company.Exploration;

import com.company.Pokemon.Pokemon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.beans.EventHandler;

public class StorageViewUnit {

    @FXML
    private Label lvLabel;

    @FXML
    private Button TransferButton;

    @FXML
    private Label TypeLabel;

    @FXML
    private Button StatusButton;

    @FXML
    private Label nameLabel;

    Pokemon pokemon;

    public boolean pokemonMatches(Pokemon p){
        return pokemon.equals(p);
    }

    public void setPokemon(Pokemon pokemon){
        this.pokemon = pokemon;
        nameLabel.setText(pokemon.name);
        TypeLabel.setText(pokemon.t1 + "," + pokemon.t2 );
        lvLabel.setText(Integer.toString(pokemon.getLevel()));
    }

    public Button getTransferButton() {
        return TransferButton;
    }

    public Button getStatusButton() {
        return StatusButton;
    }
}
