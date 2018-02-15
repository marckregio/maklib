package com.marckregio.animation.rebound;

import android.view.View;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

/**
 * Created by makregio on 15/02/2018.
 */

public class ReboundAnimation implements SpringListener {

    private SpringSystem springSystem;
    private Spring spring;
    private SpringConfig config;
    private View view;

    public ReboundAnimation(){
        springSystem = SpringSystem.create();
        config = new SpringConfig(800, 20);
        spring = springSystem.createSpring();
        spring.setSpringConfig(config);
        spring.addListener(this);
    }

    public void animateView(final View view, float value){
        this.view = view;
        spring.setEndValue(value);
    }

    @Override
    public void onSpringUpdate(Spring spring) {
        float value = (float) spring.getCurrentValue();
        float scale = 1f - (value * 0.1f);
        view.setScaleX(scale);
        view.setScaleY(scale);

    }

    @Override
    public void onSpringAtRest(Spring spring) {

    }

    @Override
    public void onSpringActivate(Spring spring) {

    }

    @Override
    public void onSpringEndStateChange(Spring spring) {

    }
}
