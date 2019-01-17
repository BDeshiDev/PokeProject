package com.company.Utilities.TextHandler;

public interface LineHolder {
    boolean streamComplete();

    boolean hasLine();

    String tryGetLine(double delta);

    String pop();

    void push(String s);
}
