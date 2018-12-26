package com.company;


public class Level {
    private int curLevel;
    private int curXP;
    private int xpToNext;
    public static int maxLevel = Settings.maxLevel;

    public Level(int Level) {
        this.curLevel = Level > maxLevel? maxLevel:Level;
        xpToNext = xpReqForLevel(Level);
    }

    public int getCurLevel() {
        return curLevel;
    }

    public int getCurXP() {
        return curXP;
    }

    public int getXpToNext() {
        return xpToNext;
    }

    public static int xpReqForLevel(int level){
        return  (int)Math.ceil( level * level * level * 4.0 / 5 );
    }

    public void addXP(int amount){
        if(curLevel >= maxLevel)
            return;
        curXP += amount;
        if(curXP >=xpToNext){
            curXP -= amount;
            curLevel++;
            xpToNext = xpReqForLevel(curLevel);
        }
    }

    @Override
    public String toString() {
        return "Level{" +
                "curLevel=" + curLevel +
                ", curXP=" + curXP +
                ", xpToNext=" + xpToNext +
                '}';
    }
}
