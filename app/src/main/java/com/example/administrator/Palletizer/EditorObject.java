package com.example.administrator.Palletizer;

import android.widget.ImageView;

public class EditorObject {

    private String name;
    private int sizeX;
    private int sizeY;
    private int imageResource;

    EditorObject(){
    }

    EditorObject(String name, int sizeX, int sizeY, int imageResource) {
        this.name = name;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return this.imageResource;
    }

    public boolean equals(EditorObject o) {
        if(o.name.equals(this.name)) {
            return true;
        } else {
            return false;
        }
    }

}
