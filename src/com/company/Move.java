package com.company;

class  Move{
    private String name;
    public final Type type;
    public final int power;
    public final int priority;
    //currently unused
    public final int accuracy;
    public final int maxPp;
    private int curPp;
    //unused end

    public String getName(){
        return name;
    }
    public Move(String _name, Type _type, int _power, int _priority, int _accuracy,int _maxPp){
        name = _name;
        type = _type;
        power = _power;
        priority = _priority;
        accuracy = _accuracy;
        curPp = maxPp = _maxPp;
    }

    public boolean canBeUsed(){
        return  curPp>0;
    }


    public void use(Pokemon user,Pokemon target){
        System.out.println(user.name + " used " + this.name + " on " + target.name +". " );
        curPp--;
        double stabBonus = user.getStabBoost(this);
        target.takeHit(this,user.attack,stabBonus);//#UNIMPLEMENTED certain moves should use sp attack instead of attack
    }

}
