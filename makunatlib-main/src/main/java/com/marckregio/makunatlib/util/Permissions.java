package com.marckregio.makunatlib.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

/**
 * Created by makregio on 11/04/2017.
 */

public class Permissions  {

    Activity activity;

    public final static int SETTINGS_REQUEST_CODE = 655;

    public Permissions(Activity activity){
        this.activity = activity;
    }

    public boolean checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(activity)) {
                /** if not construct intent to request permission */
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + activity.getPackageName()));
                /** request permission via start activity for result */
                activity.startActivityForResult(intent, SETTINGS_REQUEST_CODE);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }


    }

    public Boolean checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) ||
                    ContextCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                activity.requestPermissions(new String [] {
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                }, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    public Boolean checkStoragePermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            if ((ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) ||
                    ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                activity.requestPermissions(new String [] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    public Boolean checkNetworkControlPermission(){
        if (Build.VERSION.SDK_INT >= 23){
            if ((ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) ||
                ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                activity.requestPermissions(new String [] {
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE
                }, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    public Boolean checkCameraPermission(){
        if (Build.VERSION.SDK_INT >= 23){
            if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                activity.requestPermissions(new String [] {
                        Manifest.permission.CAMERA
                }, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    //REFERENCE ONLY
    private void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions[0] != android.Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] != PackageManager.PERMISSION_GRANTED){

        } else if (permissions[0] != Manifest.permission.WRITE_EXTERNAL_STORAGE &&
                grantResults[0] != PackageManager.PERMISSION_GRANTED){
        }
    }
}
