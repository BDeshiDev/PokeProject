package com.company;
import java.util.ArrayDeque;

public class LineStream {
    private ArrayDeque<String> remainingLines = new ArrayDeque<>();
    private double delayPerLine = .5;
    private  double curTime;

    public boolean streamComplete(){
        return remainingLines.size() <= 0;
    }

    public boolean hasString(){
        return  curTime >= delayPerLine;
    }

    public String getLine(){
        return remainingLines.pop();
    }
    public void push(String s){
        remainingLines.push(s);
    }
}
