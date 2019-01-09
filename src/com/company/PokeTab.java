package com.company;

import com.company.Pokemon.Pokemon;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PokeTab implements Initializable {
    @FXML
    TabPane tabPane;
    @FXML
    Tab pokeInfo;
    @FXML
    Tab pokeMove;
    @FXML
    Tab pokeSkill;
    @FXML
    PokeInfo pokeInfoPageController;
    @FXML
    PokeSkill pokeSkillPageController;
    @FXML
    PokeMove pokeMovePageController;

    public static Pokemon pokemon=PokemonFactory.getCharizard();

    public void init() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable,
                                                                        Tab oldValue, Tab newValue) -> {
            if (newValue==pokeInfo) {
                pokeInfoPageController.handle();
            }
            else if (newValue==pokeSkill) {
                pokeSkillPageController.handle();
            }
            else if (newValue==pokeMove) {
                pokeMovePageController.handle();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pokeInfoPageController.handle();
    }
}
