package com.example.administrator.Palletizer;

import java.util.ArrayList;

public class Design {

    /*
     * Type 0: Prebuilt and tested by Lovalive
     * Type 1: Custom design
     */
    private int type;
    private String name;
    private ArrayList<Coord> steps;
    private ArrayList<Box> boxList;

    Design(){
    }

    public Design(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSteps(ArrayList<Coord> steps) {
        this.steps = steps;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Coord> getSteps() {
        return steps;
    }
}
