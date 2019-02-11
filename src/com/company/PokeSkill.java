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
        expBar.setProgress(pokemon.getXpRatio());
        hpBar.setProgress(pokemon.getHpRatio());
        speed.setText("Speed    : "+pokemon.getSpeed());
        NextLevel.setText("Next Level : "+pokemon.stats.level.getXpToNext());
        hp.setText("Hp    : "+pokemon.getCurHp());
        attack.setText("Attack :  "+pokemon.getAtt());
        defence.setText("Defence :  "+pokemon.getDef());
        spAttack.setText("Sp.Attack :  "+pokemon.getSpAtt());
        spDefence.setText("Sp.Defence :  "+pokemon.getSpDef());
        NameAndLevel.setText(pokemon.name+"    Level: "+pokemon.getLevel());
        Exp.setText("Exp :   "+pokemon.getCurXp());
    }
}
