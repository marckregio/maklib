package com.marckregio.animation.spring;

import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.View;

/**
 * Created by makregio on 15/02/2018.
 */

public class Spring {


    public SpringAnimation createSpringAnimation(
            View view,
            DynamicAnimation.ViewProperty property,
            float finalPosition,
            float stiffness,
            float dampingRatio){
        SpringAnimation animation = new SpringAnimation(view, property);
        SpringForce springForce = new SpringForce(finalPosition);
        springForce.setStiffness(stiffness);
        springForce.setDampingRatio(dampingRatio);
        animation.setSpring(springForce);

        return animation;
    }
}
