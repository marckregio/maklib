package com.marckregio.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;


/**
 * Created by makregio on 16/08/2017.
 */

public class DialogView{

    private Context context;

    public DialogView(Context context){
        this.context = context;
    }

    public Dialog showSigninDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(R.layout.dialog_alert);
        dialog.show();
        ((TextView) dialog.findViewById(R.id.exit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }


    public AlertDialog showAlert(String title, String message, DialogInterface.OnClickListener listener){
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", listener)
                .create();
    }

}
