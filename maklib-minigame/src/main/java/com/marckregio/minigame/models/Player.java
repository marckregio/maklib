package com.marckregio.minigame.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.marckregio.minigame.R;

/**
 * Created by makregio on 30/01/2018.
 */

public class Player {

    private final int GRAVITY = -10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private Bitmap bitmap;
    private int x;
    private int y;
    private int minY;
    private int maxY;

    private int speed = 0;
    private boolean boosting;

    private Rect detectCollision;

    public Player(Context context, int screenX, int screenY){
        this.x = 75;
        this.y = 50;
        this.speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);

        maxY = screenY - bitmap.getHeight();
        minY = 0;
        boosting = false;

        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void setBoosting() {
        boosting = true;
    }

    public void stopBoosting() {
        boosting = false;
    }

    public void update(){
        if (boosting){
            speed += 2;
        } else {
            speed -= 5;
        }

        if (speed > MAX_SPEED){
            speed = MAX_SPEED;
        }

        if (speed < MIN_SPEED){
            speed = MIN_SPEED;
        }

        y -= speed + GRAVITY;

        if (y < minY){
            y = minY;
        }

        if (y > maxY){
            y = maxY;
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getSpeed(){
        return this.speed;
    }
}
