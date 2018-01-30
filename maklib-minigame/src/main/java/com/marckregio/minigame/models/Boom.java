package com.marckregio.minigame.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.marckregio.minigame.R;

/**
 * Created by makregio on 30/01/2018.
 */

public class Boom {

    private Bitmap bitmap;

    private int x;
    private int y;

    public Boom(Context context) {
        //getting boom image from drawable resource
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.boom);

        //setting the coordinate outside the screen
        //so that it won't shown up in the screen
        //it will be only visible for a fraction of second
        //after collission
        x = -250;
        y = -250;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
