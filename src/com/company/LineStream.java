package com.company;
import java.util.ArrayDeque;

public class LineStream {
    private ArrayDeque<String> remainingLines = new ArrayDeque<>();
    private double delayPerLine;
    private  double curTime;


    public LineStream(){
        this(Settings.timeBetweenLines);
    }
    public LineStream( double delayPerLine) {
        this.delayPerLine = delayPerLine;
        this.curTime = 0;
    }

    public boolean streamComplete(){
        return remainingLines.size() <= 0 && curTime > delayPerLine;
    }

    public boolean hasLine(){
        return  curTime >= delayPerLine && remainingLines.size() >0;
    }

    public void addDelta(double delta){//remember to call this in battle loop
        curTime+= delta;
    }

    public String pop(){
        curTime -= delayPerLine;
        return remainingLines.removeLast();
    }
    public void push(String s){
        remainingLines.push(s);
    }
}
