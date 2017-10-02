package com.marckregio.makunatlib;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


import com.marckregio.makunatlib.http.NetworkCheck;
import com.marckregio.makunatlib.AsyncRequest;

import org.json.JSONObject;

/**
 * Created by makregio on 08/12/2016.
 */

public abstract class API {

    Context context;

    public API(Context context){
        this.context = context;
    }

    public Context getContext(){
        return context;
    }

    public void task(final AsyncRequest async, final JSONObject jsonObject){
        new NetworkCheck(context){
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean){
                    async.execute(jsonObject);
                } else {
                    showAlert("No Internet", "Please Check Internet Connectivity.");
                }
                super.onPostExecute(aBoolean);
            }
        }.execute();
    }


    public void showAlert(String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    public void showInfo(String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }
}
