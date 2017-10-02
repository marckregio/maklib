package com.marckregio.makunatlib;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by makregio on 23/01/2017.
 */

public class Preferences {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private Broadcast broadcast;


    public Preferences(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(context.getPackageName(), context.MODE_PRIVATE);
    }

    public void putString(String field, String value){
        editor = preferences.edit();
        editor.putString(field, value);
        editor.apply();
    }

    public void putInt(String field, int value){
        editor = preferences.edit();
        editor.putInt(field, value);
        editor.apply();
    }

    public void putBoolean(String field, boolean value){
        editor = preferences.edit();
        editor.putBoolean(field, value);
        editor.apply();
    }


    public String checkString(String field){
        return preferences.getString(field, "");
    }

    public boolean checkBoolean(String field){
        return preferences.getBoolean(field, false);
    }

    public int checkInt(String field){
        return preferences.getInt(field, 0);
    }


}
