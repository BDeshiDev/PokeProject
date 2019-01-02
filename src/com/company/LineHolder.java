package com.company;

public interface LineHolder {
    boolean streamComplete();

    boolean hasLine();

    void addDelta(double delta);

    String pop();

    void push(String s);
}
