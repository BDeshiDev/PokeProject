package com.company;

public class Stat {
    private double realVal;
    private int baseVal;
    private int CurVal;
    private int MaxVal;

    public void lerp(Level lvl){
        int curLevel = lvl.getCurLevel();
        int maxLevel = Level.maxLevel;
        realVal =baseVal + ((double)(MaxVal - baseVal))* curLevel / maxLevel;
        CurVal = (int) Math.floor(realVal);
    }
    public double getRealVal() {
        return realVal;
    }
    public int getCurVal() {
        return CurVal;
    }
    public int getMaxVal() {
        return MaxVal;
    }

    public Stat(){
        this(1,100);
    }
    public Stat(int _baseVal, int maxVal) {
        realVal= CurVal = baseVal = _baseVal;
        MaxVal = maxVal;
    }
    public Stat(int _baseVal, int maxVal,Level lvl) {
        this(_baseVal,maxVal);
        this.lerp(lvl);
    }

    @Override
    public String toString() {
        return "Stat{" +
                "realVal=" + realVal +
                ", baseVal=" + baseVal +
                ", CurVal=" + CurVal +
                ", MaxVal=" + MaxVal +
                '}';
    }
}
