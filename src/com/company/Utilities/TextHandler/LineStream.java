package com.company.Utilities.TextHandler;
import com.company.Settings;

import java.util.ArrayDeque;
/*
* Class to get strings after time delay, use for dialog
* */
public class LineStream implements LineHolder {
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

    @Override
    public boolean streamComplete(){
        return remainingLines.size() <= 0 && curTime > delayPerLine;
    }

    @Override
    public boolean hasLine(){
        return  curTime >= delayPerLine && remainingLines.size() >0;
    }

    @Override
    public String tryGetLine(double delta){//remember to call this in battle loop
        curTime+= delta;
        if(hasLine())
            return this.pop();
        else
            return null;
    }

    @Override
    public String pop(){
        curTime -= delayPerLine;
        return remainingLines.removeLast();
    }
    @Override
    public void push(String s){
        remainingLines.push(s);
    }
}


