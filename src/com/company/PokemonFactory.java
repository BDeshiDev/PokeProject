package com.company;

import com.company.Pokemon.*;
import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Moves.MoveFactory;
import com.company.Pokemon.Stats.Level;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class PokemonFactory {//temp class for producing pokemon for testing replace with database

    static HashMap<String, PokemonData> pokeMap = new HashMap<>();
    static {
        PokemonData p = getCharizard();pokeMap.put(p.name,p);
//        p = getBlastoise();pokeMap.put(p.name,p);
//        p = getVenasaur();pokeMap.put(p.name,p);
//        p = getCharmander();pokeMap.put(p.name,p);
//        p = getPidgeot();pokeMap.put(p.name,p);
//        p = getCharmeleon();pokeMap.put(p.name,p);

//
//        JsonWriter jw=null;
//        try {
//            jw=new JsonWriter(new FileWriter("C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\com\\company\\Pokemon\\Moves\\moveFactory.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Gson gson=new Gson();
//        try {
//            JsonWriter writer = new JsonWriter(new FileWriter("C:\\Users\\USER\\IdeaProjects\\PokeProject\\src\\com\\company\\Pokemon\\Moves\\pokemonFactory.txt"));
//            writer.setIndent("  ");
//            gson.toJson(pokeMap.values(),pokeMap.values().getClass(), writer);
//            writer.flush();
//            writer.close();
//        }catch (IOException ioe){
//            System.out.println("save failed");
//        }

        FileReader fr=null;
        try {
            fr=new FileReader(new File("src/com/company/Pokemon/Moves/pokemonFactory.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Cant load file");
        }

        Gson gson=new Gson();
        PokemonData datas[]=gson.fromJson(fr,PokemonData[].class);
        System.out.println(pokeMap.values().getClass());
        for (PokemonData m:
                datas) {
            pokeMap.put(m.name,m);
        }


    }
    public static Pokemon getMonByName(String name,int level){
        if(pokeMap.containsKey(name))
            return pokeMap.get(name).toPokemon(level);
        else
            return null;
    }
    public static Pokemon getMonByName(String name){
        if(pokeMap.containsKey(name))
            return pokeMap.get(name).toPokemon();
        else
            return null;
    }


    public static Collection<String> getAllMonsByName(){
        return pokeMap.keySet();
    }


    public static PokemonData getCharmander(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getFlameThrower());

        return new PokemonData("Charmander",6,153,
                6,104,6,98,
                7,128,7,128,
                7,120,
                Type.Fire, Type.Flying, "Assets/PokemonImages/HdImages/CharmanderHD.png",new EvolutionData("Charmeleon",16),
                moves);

    }
    public static PokemonData getCharmeleon(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getFlameThrower());

        return new PokemonData("Charmeleon",6,153,
                6,104,6,98,
                7,128,7,128,
                7,120,
                Type.Fire, Type.Flying, "Assets/PokemonImages/HdImages/CharmeleonHD.png",new EvolutionData("Charizard",36),
                moves);

    }
    public static PokemonData getCharizard(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getFlameThrower());
        moves.add(MoveFactory.getAerialAce());

        return new PokemonData("Charizard",6,153,
                6,104,6,98,
                7,128,7,128,
                7,120,
                Type.Fire, Type.Flying, "Assets/PokemonImages/HdImages/CharizardHD.png",EvolutionData.finalEvo,
                moves);

    }
    public static PokemonData getVenasaur(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getRazorLeaf());
        moves.add(MoveFactory.getSlam());
        return  new PokemonData("Venasaur",12,155,
                6,102,6,103,
                7,120,7,120,
                6,100,
                Type.Grass, Type.Poison, "Assets/PokemonImages/HdImages/venasaurHD.png",EvolutionData.finalEvo,
                moves);
    }

    public static PokemonData getBlastoise(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getSurf());
        moves.add(MoveFactory.getSlam());

        return new PokemonData("Blastoise",12,154,
                6,103,7,120,
                7,105,7,125,
                6,98,
                Type.Water, Type.None, "Assets/PokemonImages/HdImages/BlastoiseHD.png",EvolutionData.finalEvo,
                moves);
    }

    public static PokemonData getPidgeot(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getAerialAce());
        moves.add(MoveFactory.getSlam());

        return new PokemonData("Pidgeot",6,158,
                6,100,6,95,
                3,90,6,90,
                7,121,
                Type.Normal, Type.Flying, "Assets/PokemonImages/HdImages/PidgeotHD.png",EvolutionData.finalEvo,
                moves);
    }
}
