package com.marckregio.minigame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.marckregio.minigame.models.Boom;
import com.marckregio.minigame.models.Enemy;
import com.marckregio.minigame.models.Player;
import com.marckregio.minigame.models.Star;

import java.util.ArrayList;

/**
 * Created by makregio on 30/01/2018.
 */

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;
    private Boom boom;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private Enemy[] enemies;
    private int enemyCount = 3;

    private ArrayList<Star> stars = new ArrayList<>();

    public GameView(Context context, int screenX, int screenY){
        super(context);

        player = new Player(context, screenX, screenY);
        surfaceHolder = getHolder();
        paint = new Paint();

        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            Star s  = new Star(screenX, screenY);
            stars.add(s);
        }

        enemies = new Enemy[enemyCount];
        for(int i=0; i<enemyCount; i++){
            enemies[i] = new Enemy(context, screenX, screenY);
        }

        boom = new Boom(context);
    }

    @Override
    public void run() {
        while(playing){
            update();
            draw();
            control();
        }
    }

    private void update(){
        player.update();

        boom.setX(-250);
        boom.setY(-250);

        for (Star s : stars) {
            s.update(player.getSpeed());
        }
        for(int i=0; i<enemyCount; i++){
            enemies[i].update(player.getSpeed());

            //if collision occurrs with player
            if (Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision())) {
                boom.setX(enemies[i].getX());
                boom.setY(enemies[i].getY());
                enemies[i].setX(-200);
            }
        }
    }

    private void draw(){
        if (this.surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);

            //drawing all stars
            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(
                        enemies[i].getBitmap(),
                        enemies[i].getX(),
                        enemies[i].getY(),
                        paint
                );
            }

            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try{
            gameThread.sleep(17);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void pause(){
        playing = false;
        try{
            gameThread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }
}
