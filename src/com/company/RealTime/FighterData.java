package com.company.RealTime;

public class FighterData {
    public int att,def,spAtt,spDef,speed,stamina,maxHp;
    public int curHp = maxHp;
    public String Name;
    public  String imageName;

    public FighterData(int att, int def, int spAtt, int spDef, int speed, int stamina, int maxHp, String name, String imageName) {
        this.att = att;
        this.def = def;
        this.spAtt = spAtt;
        this.spDef = spDef;
        this.speed = speed;
        this.stamina = stamina;
        this.maxHp = maxHp;
        Name = name;
        this.imageName = imageName;

        reset();
    }



    public static FighterData getDummy1(){
        return  new FighterData(4,3,3,3,4,100,150,"Charizard","Assets/charizardOoverWorld.png");
    }

    public static FighterData getDummy2(){
        return  new FighterData(3,3,3,3,2,100,150,"Charizard","Assets/charizardOoverWorld.png");
    }
    public void reset(){
        curHp = maxHp;
    }
}
