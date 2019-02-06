package com.company.Pokemon;

import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Stats.StatsComponent;
import com.company.Settings;

import java.util.ArrayList;
/*
* Data holder class for Pokemons.
* Created because we need to load Data from another pokemon after evolving
* */
public class PokemonData {
    public final String name;
    public final Type t1,t2;
    public final StatsComponent stats;
    public final EvolutionData evoData;
    public final String frontImage;
    public final ArrayList<Move> moves;

    public PokemonData(String _name,int hpAtMaxLevel, int hpBase,
                   int attAtMaxLevel, int attBase, int defAtMaxLevel, int defBase,
                   int spAttAtMaxLevel, int spAttBase, int spDefAtMaxLevel, int spDefBase, int speedAtMaxLevel, int speedBase,
                   Type _t1,String _frontImg,String _backImg ,EvolutionData evoData,ArrayList<Move> _moves){
        this(_name,hpBase,hpAtMaxLevel,attAtMaxLevel,attBase, defAtMaxLevel, defBase,
                spAttAtMaxLevel,spAttBase, spDefAtMaxLevel,spDefBase, speedAtMaxLevel,speedBase
                ,_t1, Type.None,_frontImg,evoData,_moves);
    }


    public PokemonData(String _name,int baseHp,int hpAtMaxLvl,
                   int baseAtt,int attAtMaxLvl, int baseDef,int defAtMaxLvl,
                   int baseSpAtt,int spAttAtMaxLvl, int baseSpDef,int spDefAtMaxLvl, int baseSpeed,int speedAtMaxLvl,
                   Type _t1, Type _t2,String _frontImg,EvolutionData evoData,ArrayList<Move> _moves){
        this.evoData = evoData;
        name = _name;
        stats = new StatsComponent(Settings.minLevel,baseHp,hpAtMaxLvl, baseAtt,attAtMaxLvl, baseDef,defAtMaxLvl,
                baseSpAtt,spAttAtMaxLvl, baseSpDef,spDefAtMaxLvl, baseSpeed,speedAtMaxLvl);

        t1 = _t1;
        t2 = _t2;

        moves = _moves;

        frontImage = _frontImg;
    }

    public Pokemon toPokemon(int level){
        return  new Pokemon(this,level);
    }
    public Pokemon toPokemon(){ return  this.toPokemon(Settings.maxLevel); }
}
