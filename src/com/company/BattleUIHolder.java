package com.company;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class BattleUIHolder {
    private Label NameLabel;
    private ProgressBar HpBar;
    private Label HpLabel;
    private Label LvLabel;
    private Rectangle Indicator;
    private ImageView imageView;
    private boolean shoudUseFrontImage;
    public BattleUIHolder(Label nameLabel, ProgressBar hpBar, Label hpLabel, Label lvLabel, ImageView imageView,boolean shoudUseFrontImage) {
        NameLabel = nameLabel;
        HpBar = hpBar;
        HpLabel = hpLabel;
        LvLabel = lvLabel;
        this.imageView = imageView;
        this.shoudUseFrontImage = shoudUseFrontImage;

    }
    public void load(Pokemon pokemon){
        if(pokemon == null)
            System.out.println("poke null");
        HpBar.setProgress(pokemon.getHpRatio());
        HpLabel.setText(pokemon.getCurHp() + " / " + pokemon.stats.maxHp.getCurVal());
        imageView.setImage( new Image(shoudUseFrontImage?pokemon.frontImage:pokemon.backImage));
        LvLabel.setText("LV. "+ pokemon.getLevel());
        NameLabel.setText(pokemon.name);
    }

    public void setHealth(int curHealth, int maxHealth){
        if(curHealth > 0) {
            HpLabel.setText(curHealth + " / " + maxHealth);
            HpBar.setProgress(((double) curHealth) / maxHealth);
        }else{
            HpLabel.setText(0 + " / " + maxHealth);
            HpBar.setProgress(((double) 0) / maxHealth);
        }
    }

}

