package com.company.Utilities.TextHandler;

import com.company.BattleExecutable;
import com.company.Settings;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LineStreamExecutable implements BattleExecutable,LineHolder{
//helper class to handle line streams over time by implementing the interface
    LineHolder lineStream;


    public LineStreamExecutable(String... stringsToPush) {
        this();
        lineStream.push("");//so that dialog box is clear at end
        for (String s:stringsToPush) {
            lineStream.push(s);
        }
    }

    public LineStreamExecutable() {
        this.lineStream =new LineStream();
    }

    @Override
    public void start() { }

    @Override
    public void continueExecution(double delta, Text executionOutputText) {
        if(!lineStream.streamComplete()){//if we have lines to show, do that first or do else statement
            String newString = tryGetLine(delta);//update timer on lineSource
            if(newString != null){
                executionOutputText.setText(newString);
            }
        }
    }

    public void continueExecution(double delta, Pane TextParentPane) {
        if(!lineStream.streamComplete()){//if we have lines to show, do that first or do else statement
            String newString = tryGetLine(delta);//update timer on lineSource
            if(newString != null){
                Text newText = new Text(newString);
                newText.setFont(Settings.defFont);
                TextParentPane.getChildren().add(newText);
            }
        }
    }


    //annoying interface implementations go here
    @Override
    public boolean streamComplete() {
        return lineStream.streamComplete();
    }

    @Override
    public boolean hasLine() {
        return lineStream.hasLine();
    }

    @Override
    public String tryGetLine(double delta) {
        return lineStream.tryGetLine(delta);
    }

    @Override
    public String pop() {
        return lineStream.pop();
    }

    @Override
    public void push(String s) {
        lineStream.push(s);
    }

    @Override
    public boolean isComplete() {
        return lineStream.streamComplete();
    }

    @Override
    public void end() {

    }
}
