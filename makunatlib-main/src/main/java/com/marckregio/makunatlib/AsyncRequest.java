package com.marckregio.makunatlib;

import android.content.Context;
import android.os.AsyncTask;

import com.marckregio.makunatlib.http.RESTClient;

import org.json.JSONObject;

/**
 * Created by makregio on 12/08/16.
 */
public abstract class AsyncRequest extends AsyncTask<Object, Void, JSONObject> {

    private static final long TIME_OUT_RETRIEVE_TASK = 20 * 1000; //20 Seconds
    protected String request;
    protected RESTClient client;

    public AsyncRequest(Context context, String request){
        this.request = request;
        client = new RESTClient(context);
    }

    @Override
    protected JSONObject doInBackground(Object... params) {
        Object locker = new Object();
        Object jsonParams = params[0];
        requestObject(jsonParams, request, locker);
        synchronized (locker){
            try{
                locker.wait(TIME_OUT_RETRIEVE_TASK); // -- use this to detect if there's no server response
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return client.getResultJson();
    }


    protected abstract void requestObject(Object params, String request, Object locker);
}
