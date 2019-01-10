package com.company;

import com.company.Pokemon.Pokemon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import static com.company.PokeTab.pokemon;

public class PokeSkill{
    @FXML
    Label spAttack;
    @FXML
    Label NameAndLevel;
    @FXML
    Label defence;
    @FXML
    Label attack;
    @FXML
    Label spDefence;
    @FXML
    Label hp;
    @FXML
    Label Exp;
    @FXML
    Label NextLevel;
    @FXML
    Label speed;
    @FXML
    ProgressBar hpBar;
    @FXML
    ProgressBar expBar;
    @FXML
    ImageView pokemonImage;


    public void handle() {
        pokemonImage.setImage(new Image(pokemon.frontImage));
        expBar.setProgress(.5);
        hpBar.setProgress(pokemon.getHpRatio());
        speed.setText("Speed    : "+"Ko to ko to??");
        NextLevel.setText("Next Level : "+"ko to ko to??");
        hp.setText("Hp    : "+pokemon.getCurHp());
        attack.setText("Attack :  "+"ko to ko to??");
        defence.setText("Defence :  "+"ko to ko to??");
        spAttack.setText("Sp.Attack :  "+"ko to ko to??");
        spDefence.setText("Sp.Defence :  "+"ko to ko to??");
        NameAndLevel.setText(pokemon.name+"    Level: "+pokemon.getLevel());
        Exp.setText("Exp :   "+"ko to ko to??");
    }
}
