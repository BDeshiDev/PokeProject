package com.company;

import com.company.Pokemon.Pokemon;
import com.company.Utilities.Debug.Debugger;
import com.company.Utilities.TextHandler.LineHolder;
import javafx.scene.image.ImageView;

public class BattleSlot{
    private Pokemon pokemon=null;
    private BattleUIHolder slotUI=null;
    private ImageView animationViewer=null;
    private boolean isCatchDisabled = true;

    public Pokemon getCurPokemon(){
        return pokemon;
    }
    public void setPokemon(Pokemon p, boolean isCatchDisabled){
        this.isCatchDisabled = isCatchDisabled;
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

    public Pokemon tryCatch(){
        if(isCatchDisabled || pokemon == null || !pokemon.canCatch())
            return  null;
        else{
            Pokemon temp = pokemon;
            pokemon.forceKo();
            return temp;
        }
    }

    public void setAnimationViewer(ImageView animationViewer) {
        this.animationViewer = animationViewer;
    }

    public  boolean isEmpty(){
        return pokemon == null;
    }


    public void takehit(int damage, LineHolder lineStreamToAppendTo){
        pokemon.takeDamage(damage,lineStreamToAppendTo);
        slotUI.setHealth(pokemon.getCurHp(),pokemon.stats.maxHp.getCurVal());
    }

    public BattleSlot(BattleUIHolder slotUI, ImageView animationViewer) {
        this.slotUI = slotUI;
        this.animationViewer = animationViewer;
    }


}
