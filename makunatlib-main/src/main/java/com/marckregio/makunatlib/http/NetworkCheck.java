package com.marckregio.makunatlib.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

/**
 * Created by makregio on 26/08/16.
 */
public class NetworkCheck extends AsyncTask<Boolean, Void, Boolean> {

    private Context context;

    public NetworkCheck(Context context){
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Boolean... params) {
        boolean returnValue = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            boolean isWifi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            boolean isMobile = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();


            if (isMobile && isConnected) {
                returnValue = true;
            } else if (isWifi && isConnected) {
                returnValue = true;
            } else {
                returnValue = false;
            }
        }

        return returnValue;
    }
}
