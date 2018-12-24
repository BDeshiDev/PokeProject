package com.company;

class pcTrainer extends Trainer {

    private Move selectedMove = null;

    @Override
    public Boolean hasCommand() {
        return selectedMove != null;
    }

    public void setCommand(int index){
        selectedMove = curPokemon.getMove(index) ;
    }

    @Override
    public void prepTurn() {
        selectedMove = null;
    }

    public pcTrainer(String _name, Pokemon... pokemons){
        super(_name,pokemons);//... used for quickness,use list or something better
    }

    //needs a better name
    @Override
    public Attack getCommand( Pokemon target){//returns the attack chosen this turn
        return new Attack(curPokemon,target,selectedMove);
    }
}
