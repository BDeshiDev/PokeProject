package com.company;

import java.util.ArrayList;
import java.util.List;

public class PokemonFactory {//temp class for producing pokemon for testing replace with database
    public static Pokemon getCharizard(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getFlameThrower());
        moves.add(MoveFactory.getAerialAce());

        return  new Pokemon("Charizard",220,80,80,110,80,100,
                Type.Fire, Type.Flying,"Assets/charz3.png","Assets/backCharz.png",
                moves);
    }
    public static Pokemon getVenasaur(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getRazorLeaf());
        moves.add(MoveFactory.getSlam());

        return  new Pokemon("Venasaur",240,80,80,100,80,80,
                Type.Grass, Type.Poison,"Assets/venasaurFront.png","Assets/venasaurBack.png",
                moves);
    }

    public static Pokemon getBlastoise(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getSurf());
        moves.add(MoveFactory.getSlam());

        return  new Pokemon("Blastoise",280,85,90,105,90,70,
                Type.Grass, Type.Poison,"Assets/BlastoiseFront.png","Assets/BlastoiseBack.png",
                moves);
    }
}
