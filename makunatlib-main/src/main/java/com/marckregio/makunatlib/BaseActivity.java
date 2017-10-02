package com.marckregio.makunatlib;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.Surface;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.marckregio.makunatlib.util.Animation;
import com.marckregio.makunatlib.util.Permissions;

/**
 * Created by makregio on 01/02/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public BroadcastReceiver refreshReceiver, downloadReceiver, errorReceiver, notificationReceiver;
    public Permissions permissions;
    public Preferences preferences;
    public Animation animation;

    public static String FRESH = "fresh_install";

    public static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissions = new Permissions(this);
        preferences = new Preferences(this);
        animation = new Animation(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(refreshReceiver, new IntentFilter(getApplicationContext().getResources().getString(R.string.broadcast_refresh)));
        registerReceiver(downloadReceiver, new IntentFilter(getApplicationContext().getResources().getString(R.string.broadcast_download)));
        registerReceiver(errorReceiver, new IntentFilter(getApplicationContext().getResources().getString(R.string.broadcast_error)));
        registerReceiver(notificationReceiver, new IntentFilter(getApplicationContext().getResources().getString(R.string.broadcast_notification)));
    }

    @Override
    protected void onStart() {
        GoogleAnalytics.getInstance(this).dispatchLocalHits();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        if (refreshReceiver != null)
            unregisterReceiver(refreshReceiver);

        if (downloadReceiver != null)
            unregisterReceiver(downloadReceiver);

        if (errorReceiver != null)
            unregisterReceiver(errorReceiver);

        if (notificationReceiver != null)
            unregisterReceiver(notificationReceiver);


        GoogleAnalytics.getInstance(this).reportActivityStop(this);
        super.onDestroy();
    }

    public boolean isPlayStoreInstalled(Context context){
        try {
            context.getPackageManager()
                    .getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void showAlert(String title, String message){
        new AlertDialog.Builder(this)
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions[0] != Manifest.permission.WRITE_EXTERNAL_STORAGE && grantResults[0] != PackageManager.PERMISSION_GRANTED){
            showAlert("Storage Permission", "The app needs your storage to work.");
        } else {
            //showAlert("Storage Permission", "Download again.");
        }
    }
}
