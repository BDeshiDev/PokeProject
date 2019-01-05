package com.company;

import com.company.Utilities.Debug.Debugger;
import javafx.scene.image.ImageView;

public class BattleSlot{
    private Pokemon pokemon=null;
    private BattleUIHolder slotUI=null;
    private ImageView animationViewer=null;

    public Pokemon getCurPokemon(){
        return pokemon;
    }
    public void setPokemon(Pokemon p){
        pokemon = p;
        if(slotUI != null){
            slotUI.load(pokemon);
        }else{
            Debugger.out("slot ui is null for" + pokemon.name);
        }
    }
    public void setSlotUI(BattleUIHolder slotUI){
        Debugger.out("slot ui set");
        this.slotUI = slotUI;
    }

    public ImageView getAnimationViewer() {
        return animationViewer;
    }

    public void setAnimationViewer(ImageView animationViewer) {
        this.animationViewer = animationViewer;
    }

    public  boolean isEmpty(){
        return pokemon == null;
    }

    public void takeHit(Move m , int userAttack, double stabBonus,LineHolder streamToAppendTo){
        pokemon.takeHit(m,userAttack,stabBonus,streamToAppendTo);
        slotUI.setHealth(pokemon.getCurHp(),pokemon.stats.maxHp.getCurVal());
    }

    public BattleSlot(Pokemon pokemon) {
        setPokemon(pokemon);
    }

    public BattleSlot(BattleUIHolder slotUI, ImageView animationViewer) {
        this.slotUI = slotUI;
        this.animationViewer = animationViewer;
    }


}
