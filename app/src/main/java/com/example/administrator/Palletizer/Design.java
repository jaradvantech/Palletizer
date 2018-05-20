package com.example.administrator.Palletizer;

import java.util.ArrayList;

import javax.crypto.Cipher;

public class Design {

    /*
     * Type 0: Prebuilt and tested by Lovalive
     * Type 1: Custom design
     */
    private int type;
    private String name;
    public ArrayList<Box> boxList;

    Design(int type, String name){
        boxList = new ArrayList<Box>();
        this.type = type;
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Box> getSteps() {
        return boxList;
    }
}
