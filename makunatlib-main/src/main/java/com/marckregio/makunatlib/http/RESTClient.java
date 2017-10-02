package com.marckregio.makunatlib.http;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by makregio on 28/07/16.
 */
public class RESTClient {

    private Context context = null;
    private JSONObject resultJson = null;

    public RESTClient(Context context){
        this.context = context;
    }

    public Context getContext(){
        return context;
    }

    public synchronized JSONObject getResultJson(){
        return resultJson;
    }

    public synchronized void requestJSONObj(JSONObject jsonObject, String url, final Object lockObj) {

        REST.postOkHttp(url, jsonObject, new REST.OkHttp() {
            @Override
            public void onFailure(Request request, IOException e) {
                //failed
                Log.v("JSONResult","Failed");
                synchronized (lockObj){
                    lockObj.notifyAll();
                }
            }

            @Override
            public void onResponse(Response response) throws Exception {
                Log.v("JSONResult","Success");
                if (response != null) {
                    resultJson = new JSONObject(response.body().string());
                }
                synchronized (lockObj){
                    lockObj.notifyAll();
                }
            }
        });

    }

}
