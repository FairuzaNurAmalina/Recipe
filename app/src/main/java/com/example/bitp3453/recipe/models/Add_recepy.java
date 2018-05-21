package com.example.bitp3453.recipe.models;

import android.graphics.Bitmap;

/**
 * Created by optimum2 on 14/5/18.
 */

public class Add_recepy {
    String ID,Name;
    Bitmap bitmap;


    public Add_recepy() {
    }


    public Add_recepy(String ID, String name, Bitmap bitmap) {
        this.ID = ID;
        Name = name;
        this.bitmap = bitmap;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
