package com.company;

public class BattleSlot {
    private Pokemon pokemon=null;
    private BattleUIHolder slotUI=null;

    public Pokemon getCurPokemon(){
        return pokemon;
    }
    public void setPokemon(Pokemon p){
        pokemon = p;
        if(slotUI != null){
            slotUI.load(pokemon);
        }else{
            System.out.println("slot ui is null for" + pokemon.name);
        }
    }
    public void setSlotUI(BattleUIHolder slotUI){
        System.out.println("slot ui set");
        this.slotUI = slotUI;
    }
    public  boolean isEmpty(){
        return pokemon == null;
    }

    public void takeHit(Move m , int userAttack, double stabBonus){
        pokemon.takeHit(m,userAttack,stabBonus);
        slotUI.setHealth(pokemon.getCurHp(),pokemon.stats.maxHp.getCurVal());
    }

    public BattleSlot(Pokemon pokemon) {
        setPokemon(pokemon);
    }
    public BattleSlot(){
        //everything is set to null alreadu
    }
}
