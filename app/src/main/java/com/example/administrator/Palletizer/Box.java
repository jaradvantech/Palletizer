package com.example.administrator.Palletizer;

/**
 * Created by Administrator on 2018/4/27.
 */

public class Box extends BoxDesign {
    public Coord coords;

    public Box(Coord coords, int width, int height, int textureResource) {
        super(width, height, textureResource);
        this.coords = coords;
    }

}
