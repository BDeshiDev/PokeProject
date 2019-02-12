package com.company;

import com.company.Pokemon.*;
import com.company.Pokemon.Moves.Move;
import com.company.Pokemon.Moves.MoveFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PokemonFactory {//temp class for producing pokemon for testing replace with database

    static HashMap<String, PokemonData> pokeMap = new HashMap<>();
    static {
        PokemonData p = getCharizard();pokeMap.put(p.name,p);
        p = getBlastoise();pokeMap.put(p.name,p);
        p = getVenasaur();pokeMap.put(p.name,p);
        p = getCharmander();pokeMap.put(p.name,p);
        p = getPidgeot();pokeMap.put(p.name,p);
        p = getCharmeleon();pokeMap.put(p.name,p);

        p = getPidgeotto();pokeMap.put(p.name,p);
        p = getPidgey();pokeMap.put(p.name,p);
        p = getPsyDuck();pokeMap.put(p.name,p);
        p = getTauros();pokeMap.put(p.name,p);
        p = getButterfree();pokeMap.put(p.name,p);
        p = getBeedrill();pokeMap.put(p.name,p);
        p = getKingler();pokeMap.put(p.name,p);
        p = getPikachu();pokeMap.put(p.name,p);
        p = getRaichu();pokeMap.put(p.name,p);

//
//        JsonWriter jw=null;
//        Gson gson=new Gson();
//        try {
//            JsonWriter writer = new JsonWriter(new FileWriter("src/com/company/Pokemon/Moves/pokemonFactory.txt"));
//            writer.setIndent("  ");
//            gson.toJson(pokeMap.values(),pokeMap.values().getClass(), writer);
//            writer.flush();
//            writer.close();
//        }catch (IOException ioe){
//            System.out.println("write failed...");
//        }
/*
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

*/
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

        return new PokemonData("Charmander",12,114,
                6,72,6,63,
                7,83,7,70,
                7,85,
                Type.Fire, Type.None, "Assets/PokemonImages/HdImages/CharmanderHD.png",new EvolutionData("Charmeleon",16),
                moves);

    }

    public static PokemonData getPidgey(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getAerialAce());

        return new PokemonData("Pidgey",12,115,
                6,65,6,60,
                6,65,6,60,
                6,76,
                Type.Normal, Type.Flying, "Assets/PokemonImages/HdImages/Pidgey.png",new EvolutionData("Pidgeoto",16),
                moves);

    }
    public static PokemonData getPidgeotto(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getAerialAce());
        moves.add(MoveFactory.getSlam());

        return new PokemonData("Pidgeoto",12,138,
                6,80,6,75,
                6,70,6,70,
                6,91,
                Type.Normal, Type.Flying, "Assets/PokemonImages/HdImages/Pidgeotto.png",new EvolutionData("Pidgeot",36),
                moves);

    }

    public static PokemonData getPsyDuck(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getSlam());
        moves.add(MoveFactory.getSurf());
        moves.add(MoveFactory.getMoveByName("Ice Beam"));

        return new PokemonData("PsyDuck",12,125,
                6,72,6,68,
                6,85,6,70,
                6,75,
                Type.Water, Type.None, "Assets/PokemonImages/HdImages/Psyduck.png",EvolutionData.finalEvo,
                moves);

    }

    public static PokemonData getPikachu(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getThunder());
        moves.add(MoveFactory.getMoveByName("Iron tail"));


        return new PokemonData("Pikachu",12,110,
                6,75,6,60,
                6,75,6,70,
                7,110,
                Type.Electric, Type.None, "Assets/PokemonImages/HdImages/Pikachu.png",new EvolutionData("Raichu",36),
                moves);

    }

    public static PokemonData getRaichu(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getThunder());
        moves.add(MoveFactory.getMoveByName("Iron tail"));
        moves.add(MoveFactory.getMoveByName("Slam"));

        return new PokemonData("Raichu",12,135,
                6,110,6,75,
                6,110,6,100,
                7,130,
                Type.Electric, Type.None, "Assets/PokemonImages/HdImages/Raichu.png",EvolutionData.finalEvo,
                moves);

    }

    public static PokemonData getBeedrill(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getAerialAce());
        moves.add(MoveFactory.getSlam());
        moves.add(MoveFactory.getMoveByName("Poison Needle"));
        moves.add(MoveFactory.getMoveByName("Cross Poison"));

        return new PokemonData("Beedrill",12,140,
                7,110,6,60,
                6,65,6,100,
                7,95,
                Type.Bug, Type.Poison, "Assets/PokemonImages/HdImages/Beedrill.png",EvolutionData.finalEvo,
                moves);

    }

    public static PokemonData getButterfree(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getThunder());

        return new PokemonData("ButterFree",12,135,
                7,60,6,70,
                6,110,6,100,
                7,90,
                Type.Bug, Type.Flying, "Assets/PokemonImages/HdImages/Butterfree.png",EvolutionData.finalEvo,
                moves);

    }

    public static PokemonData getKingler(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getSlam());
        moves.add(MoveFactory.getSurf());
        moves.add(MoveFactory.getMoveByName("Cross Poison"));
        moves.add(MoveFactory.getMoveByName("Ice Beam"));
        return new PokemonData("Kingler",12,140,
                7,150,7,135,
                6,70,6,70,
                7,95,
                Type.Water, Type.None, "Assets/PokemonImages/HdImages/Kingler.png",EvolutionData.finalEvo,
                moves);

    }

    public static PokemonData getTauros(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getSlam());
        moves.add(MoveFactory.getSurf());
        moves.add(MoveFactory.getMoveByName("Iron tail"));

        return new PokemonData("Tauros",12,150,
                7,120,7,115,
                6,60,6,90,
                7,130,
                Type.Water, Type.None, "Assets/PokemonImages/HdImages/Tauros.png",EvolutionData.finalEvo,
                moves);

    }

    public static PokemonData getCharmeleon(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getFlameThrower());

        return new PokemonData("Charmeleon",12,133,
                6,84,6,78,
                7,100,7,85,
                7,100,
                Type.Fire, Type.None, "Assets/PokemonImages/HdImages/CharmeleonHD.png",new EvolutionData("Charizard",36),
                moves);

    }
    public static PokemonData getCharizard(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(MoveFactory.getFlameThrower());
        moves.add(MoveFactory.getAerialAce());
        moves.add(MoveFactory.getMoveByName("Fire Blast"));
        moves.add(MoveFactory.getMoveByName("Wing Attack"));

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
        moves.add(MoveFactory.getMoveByName("Wing Attack"));

        return new PokemonData("Pidgeot",6,158,
                6,100,6,95,
                3,90,6,90,
                7,121,
                Type.Normal, Type.Flying, "Assets/PokemonImages/HdImages/PidgeotHD.png",EvolutionData.finalEvo,
                moves);
    }
}
