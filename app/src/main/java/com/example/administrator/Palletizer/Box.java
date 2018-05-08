package com.example.administrator.Palletizer;

/**
 * Created by Administrator on 2018/4/27.
 */

public class Box extends BoxPrototype{
    public Coord coords;

    public Box() {

    }

    public Box(Coord coords, int width, int height, int textureResource) {
        super(width, height, textureResource);
        this.coords = coords;
    }

    /*
     *  Another constructor without specifying coords
     */
    public Box(int width, int height, int textureResource) {
        super(width, height, textureResource);
    }

}
