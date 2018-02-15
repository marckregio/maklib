package com.marckregio.animation;

import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.marckregio.animation.interpolator.Bounce;
import com.marckregio.animation.spring.Spring;

public class SpringActivity extends AppCompatActivity implements View.OnTouchListener {

    private SpringAnimation xAnim, yAnim;
    private Spring spring;

    private ImageView logo;
    private float dX = 0f;
    private float dY = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        spring = new Spring();
        logo = (ImageView) findViewById(R.id.logo);

        logo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                xAnim = spring.createSpringAnimation(
                        logo, SpringAnimation.X, logo.getX(), SpringForce.STIFFNESS_MEDIUM, SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
                yAnim = spring.createSpringAnimation(
                        logo, SpringAnimation.Y, logo.getY(), SpringForce.STIFFNESS_MEDIUM, SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
                logo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        logo.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (view == logo){
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();

                    xAnim.cancel();
                    yAnim.cancel();

                case MotionEvent.ACTION_MOVE:
                    logo.animate()
                            .x(event.getRawX() + dX)
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();

                case MotionEvent.ACTION_UP:
                    xAnim.start();
                    yAnim.start();
            }
        }
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        isImmersive();
    }
}
