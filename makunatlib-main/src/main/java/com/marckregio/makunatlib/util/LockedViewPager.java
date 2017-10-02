package com.marckregio.makunatlib.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by makregio on 19/07/2017.
 */

public class LockedViewPager extends ViewPager {
    private Boolean disable = false;

    public LockedViewPager(Context context) {
        super(context);
    }


    public LockedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return disable ? false : super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return disable ? false : super.onTouchEvent(event);
    }

    public void disableScroll(Boolean disable){
        //When disable = true not work the scroll and when disble = false work the scroll
        this.disable = disable;
    }
}
