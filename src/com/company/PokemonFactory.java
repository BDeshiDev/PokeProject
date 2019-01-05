package com.company;

import java.util.ArrayList;

public class PokemonFactory {//temp class for producing pokemon for testing replace with database
    public static Pokemon getCharizard(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getFlameThrower());
        moves.add(MoveFactory.getAerialAce());

        return new Pokemon("Charizard", Level.maxLevel,6,153,
                6,104,6,98,
                7,128,7,128,
                7,120,
                Type.Fire, Type.Flying, "Assets/PokemonImages/charz3.png", "Assets/PokemonImages/backCharz.png",
                moves);

    }
    public static Pokemon getVenasaur(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getRazorLeaf());
        moves.add(MoveFactory.getSlam());
        return  new Pokemon("Venasaur", Level.maxLevel,12,155,
                6,102,6,103,
                7,120,7,120,
                6,100,
                Type.Grass, Type.Poison, "Assets/PokemonImages/venasaurFront.png", "Assets/PokemonImages/venasaurBack.png",
                moves);
    }

    public static Pokemon getBlastoise(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getSurf());
        moves.add(MoveFactory.getSlam());

        return new Pokemon("Blastoise", Level.maxLevel,12,154,
                6,103,7,120,
                7,105,7,125,
                6,98,
                Type.Water, Type.None, "Assets/PokemonImages/BlastoiseFront.png", "Assets/PokemonImages/BlastoiseBack.png",
                moves);
    }

    public static Pokemon getPidgeot(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getAerialAce());
        moves.add(MoveFactory.getSlam());

        return new Pokemon("Pidgeot", Level.maxLevel,6,158,
                6,100,6,95,
                3,90,6,90,
                7,121,
                Type.Normal, Type.Flying, "Assets/PokemonImages/pidgeotFront.png", "Assets/PokemonImages/pidgeotBack.png",
                moves);
    }
}
