package com.marckregio.animation.interpolator;

import android.view.animation.Interpolator;

/**
 * Created by makregio on 29/01/2018.
 */

public class Bounce implements Interpolator {

    private double amplitude = 1;
    private double frequency = 10;

    public Bounce(double amplitude, double frequency){
        this.amplitude = amplitude;
        this.frequency = frequency;
    }
    @Override
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ amplitude) * Math.cos(frequency * time) + 1);
    }
}
