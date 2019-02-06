package com.company;

import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;
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
    private boolean shouldFlipImage;
    public BattleUIHolder(Label nameLabel, ProgressBar hpBar, Label hpLabel, Label lvLabel, ImageView imageView,boolean shoudUseFrontImage) {
        NameLabel = nameLabel;
        HpBar = hpBar;
        HpLabel = hpLabel;
        LvLabel = lvLabel;
        this.imageView = imageView;
        this.shouldFlipImage = shoudUseFrontImage;

    }
    public void load(Pokemon pokemon){
        if(pokemon == null)
            Debugger.out("poke null when loading UI");
        HpBar.setProgress(pokemon.getHpRatio());
        HpLabel.setText(pokemon.getCurHp() + " / " + pokemon.stats.maxHp.getCurVal());
        imageView.setImage( new Image(pokemon.frontImage));
        imageView.setScaleX(shouldFlipImage?1:-1);
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

