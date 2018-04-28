package com.example.administrator.Palletizer;

/**
 * Created by Administrator on 2018/4/27.
 */

public class Box {
    public Coord coords;
    public int width;
    public int height;
    public int textureResource;

    public Box(Coord coords, int width, int height, int textureResource) {
        this.coords = coords;
        this.width = width;
        this.height = height;
        this.textureResource = textureResource;
    }

    public Coord getCoords() {
        return coords;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTextureResource() {
        return textureResource;
    }

    public void setCoords(Coord coords) {
        this.coords = coords;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTextureResource(int textureResource) {
        this.textureResource = textureResource;
    }
}
