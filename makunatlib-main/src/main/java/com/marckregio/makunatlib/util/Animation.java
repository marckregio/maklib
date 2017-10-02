package com.marckregio.makunatlib.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import static android.view.View.GONE;

/**
 * Created by makregio on 23/01/2017.
 */

public class Animation {

    private Context context;

    public Animation(Context context){
        this.context = context;
    }

    public void fadeIn(View view){
        if (view.getVisibility() == GONE){
            android.view.animation.Animation in = AnimationUtils.makeInAnimation(context, true);
            view.startAnimation(in);
            view.setVisibility(View.VISIBLE);
        }
    }

    public void fadeOut(View view){
        if (view.getVisibility() == View.VISIBLE){
            android.view.animation.Animation out = AnimationUtils.makeOutAnimation(context, true);
            view.startAnimation(out);
            view.setVisibility(GONE);
        }
    }

    public void showhideAnimation(final View onTopOf, final View view){
        if (view.getVisibility() == View.INVISIBLE || view.getVisibility() == View.GONE) {
            view.setTranslationY(onTopOf.getHeight());
            view.setVisibility(View.VISIBLE);

            view.animate()
                    .translationY(0)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            view.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            onTopOf.setVisibility(GONE);
                        }
                    });
        } else {
            view.animate()
                    .translationY(view.getHeight())
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            onTopOf.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(View.GONE);
                        }
                    });
        }
    }

    public void leftRightAnimation(final View onTopOf, final View view){
        if (view.getVisibility() == View.INVISIBLE || view.getVisibility() == View.GONE) {
            view.setTranslationX(onTopOf.getWidth());
            view.setVisibility(View.VISIBLE);

            view.animate()
                    .translationX(0)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            view.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            onTopOf.setVisibility(GONE);
                        }
                    });
        } else {
            view.animate()
                    .translationX(view.getWidth())
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            onTopOf.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(View.GONE);
                        }
                    });
        }
    }

    public boolean moveUpandDownAnimation(final View toHide, final View toMove){
        boolean hidden = false;
        final ViewGroup.LayoutParams params = toMove.getLayoutParams();
        if (toHide.getVisibility() == View.INVISIBLE) {
            toMove.animate()
                    .translationY(0)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationEnd(animation);
                            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        }
                    });
            hidden = true;
        } else {
            toMove.animate()
                    .translationY(toHide.getHeight() - (toHide.getHeight() * 2))
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationEnd(animation);
                            params.height = toMove.getHeight() + toHide.getHeight();
                            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        }
                    });
            hidden = false;
        }



        toMove.setLayoutParams(params);
        toMove.requestLayout();

        //showhideAnimation(toHide);

        return hidden;
    }


    public boolean moveToLeftFromRightAnimation(final View toHide, final View toMove){
        boolean hidden = false;
        final ViewGroup.LayoutParams params = toMove.getLayoutParams();
        if (toMove.getVisibility() == GONE){
            toMove.setTranslationX(toHide.getWidth());
            toMove.setVisibility(View.VISIBLE);
            toMove.animate()
                    .translationX(0)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        }
                    });
            hidden = true;
        }

        toMove.setLayoutParams(params);
        toMove.requestLayout();

        return hidden;
    }

    public boolean moveToRightFromLeftAnimation(final View toShow, final View toMove){
        boolean hidden = false;
        if (toMove.getVisibility() == View.VISIBLE){
            toMove.animate()
                    .translationX(toShow.getWidth())
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            toMove.setVisibility(GONE);
                        }
                    });
            hidden = true;
        }

        return hidden;
    }
}
