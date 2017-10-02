package com.marckregio.makunatlib;

import android.Manifest;
import android.app.Activity;
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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.Surface;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.marckregio.makunatlib.util.Animation;
import com.marckregio.makunatlib.util.Permissions;

/**
 * Created by makregio on 01/02/2017.
 */

public abstract class BaseFragment extends Fragment {
    public BroadcastReceiver refreshReceiver, downloadReceiver, errorReceiver, notificationReceiver;
    public Permissions permissions;
    public Preferences preferences;
    public Animation animation;


    public static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissions = new Permissions((Activity) getContext());
        preferences = new Preferences(getContext());
        animation = new Animation(getContext());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        if (getResources().getBoolean(R.bool.portrait_only)) {
            ((Activity)getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(refreshReceiver, new IntentFilter(getContext().getResources().getString(R.string.broadcast_refresh)));
        getContext().registerReceiver(downloadReceiver, new IntentFilter(getContext().getResources().getString(R.string.broadcast_download)));
        getContext().registerReceiver(errorReceiver, new IntentFilter(getContext().getResources().getString(R.string.broadcast_error)));
        getContext().registerReceiver(notificationReceiver, new IntentFilter(getContext().getResources().getString(R.string.broadcast_notification)));
    }

    @Override
    public void onPause() {
        if (refreshReceiver != null)
            getContext().unregisterReceiver(refreshReceiver);

        if (downloadReceiver != null)
            getContext().unregisterReceiver(downloadReceiver);

        if (errorReceiver != null)
            getContext().unregisterReceiver(errorReceiver);

        if (notificationReceiver != null)
            getContext().unregisterReceiver(notificationReceiver);


        super.onPause();
    }

    @Override
    public void onDestroy() {

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
        new AlertDialog.Builder(getContext())
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
