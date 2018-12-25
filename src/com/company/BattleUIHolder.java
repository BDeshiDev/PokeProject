package com.company;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import jdk.nashorn.internal.runtime.Source;

public class BattleUIHolder {
    private Label NameLabel;
    private ProgressBar HpBar;
    private Label HpLabel;
    private Label LvLabel;
    private Rectangle Indicator;
    private ImageView imageView;
    private boolean shoudUseFrontImage;
    public BattleUIHolder(Label nameLabel, ProgressBar hpBar, Label hpLabel, Label lvLabel, Rectangle indicator, ImageView imageView,boolean shoudUseFrontImage) {
        NameLabel = nameLabel;
        HpBar = hpBar;
        HpLabel = hpLabel;
        LvLabel = lvLabel;
        Indicator = indicator;
        this.imageView = imageView;
        this.shoudUseFrontImage = shoudUseFrontImage;

        Indicator.setVisible(false);
    }
    public void load(Pokemon pokemon){
        if(pokemon == null)
            System.out.println("poke null");
        HpBar.setProgress(pokemon.getHpRaio());
        HpLabel.setText(pokemon.getCurHp() + " / " + pokemon.maxHp);
        imageView.setImage( new Image(shoudUseFrontImage?pokemon.frontImage:pokemon.backImage));
        LvLabel.setText("LV. "+ pokemon.getLevel());
        NameLabel.setText(pokemon.name);
    }

    public void setIndicatorVisible(boolean shouldBeVisible){
        Indicator.setVisible(shouldBeVisible);
    }
}

