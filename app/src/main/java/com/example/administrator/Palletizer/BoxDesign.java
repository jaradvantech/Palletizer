package com.example.administrator.Palletizer;

/*
 * These objects are used for the Right side menu on the pallet creator screen.
 * This menu shows a list of all the available box types for creating the pallets
 */

public class BoxDesign {

    public String name;
    public int width;
    public int height;
    public int textureResource;
    public int type;

    BoxDesign(){
    }

    BoxDesign(int width, int height, int textureResource) {
        this.width = width;
        this.height = height;
        this.textureResource = textureResource;
    }

    BoxDesign(int type, String name, int width, int height, int textureResource) {
        this.type = type;
        this.name = name;
        this.width = width;
        this.height = height;
        this.textureResource = textureResource;
    }

    public boolean equals(BoxDesign o) {
        if(o.name.equals(this.name)) {
            return true;
        } else {
            return false;
        }
    }

}
