package com.marckregio.makunatlib;

import android.content.Context;



/**
 * Created by makregio on 02/09/16.
 */
public abstract class Broadcast {

    public static String REFRESH_KEY = "REFRESH";
    public static String JSON_KEY = "JSON";
    public static String SENDER_KEY = "SENDER";
    public static String RECEIVER_KEY = "RECEIVER";
    private Context context;

    public Broadcast(Context context){
        this.context = context;
    }

    public Context getContext(){
        return context;
    }

}
