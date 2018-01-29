package com.marckregio.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.marckregio.animation.interpolator.Bounce;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    private ImageView logo;
    private Animation anim, anim2, anim3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = (ImageView) findViewById(R.id.logo);
        anim = AnimationUtils.loadAnimation(this, R.anim.bounce_rotate);
        anim.setInterpolator(new Bounce(0.1, 10));
        anim.setAnimationListener(this);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.bounce_reverse);
        anim2.setAnimationListener(this);
        anim3 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        anim3.setInterpolator(new Bounce(0.1, 20));
        logo.startAnimation(anim);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == anim){
            logo.startAnimation(anim2);
        } else if (animation == anim2){
            logo.startAnimation(anim3);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
