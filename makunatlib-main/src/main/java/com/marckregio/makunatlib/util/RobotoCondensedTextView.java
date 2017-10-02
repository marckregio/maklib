package com.marckregio.makunatlib.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.*;


/**
 * Created by makregio on 27/01/2017.
 */

public class RobotoCondensedTextView extends AppCompatTextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public RobotoCondensedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public RobotoCondensedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {

        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        switch (textStyle) {
            case Typeface.BOLD:
                return setBoldTypeFace(context);
            case Typeface.ITALIC:
                return setThinTypeFace(context);
            case Typeface.BOLD_ITALIC:
                return setNormalTypeFace(context);
            case Typeface.NORMAL:
                return setNormalTypeFace(context);
            default:
                return setThinTypeFace(context);
        }
    }

    private Typeface setNormalTypeFace(Context context){
        String fontPath = "fonts/RobotoCondensed-Regular.ttf";
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }

    private Typeface setThinTypeFace(Context context){
        String fontPath = "fonts/RobotoCondensed-Light.ttf";
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }


    private Typeface setBoldTypeFace(Context context){
        String fontPath = "fonts/RobotoCondensed-Bold.ttf";
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }

}
