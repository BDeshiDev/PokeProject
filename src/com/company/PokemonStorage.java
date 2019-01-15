package com.company;

import com.company.Pokemon.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonStorage {

    private static List<Pokemon> storedMonList = new ArrayList<>();

    private  PokemonStorage(){};//To prevent instantiation

    public static void addMon(Pokemon p){
        storedMonList.add(p);
    }

    public static Pokemon getMon(int monIndex){
        if(monIndex >= storedMonList.size()){
            System.out.println("Trying to access pokemon out of storage bounds");
            return  null;
        }else{
            Pokemon retVal = storedMonList.get(monIndex);
            storedMonList.remove(monIndex);
            return  retVal;
        }
    }
}
