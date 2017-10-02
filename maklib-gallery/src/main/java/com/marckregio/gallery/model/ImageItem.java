package com.marckregio.gallery.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by eCopy on 10/2/2017.
 */

public class ImageItem {

    private String path;

    public ImageItem(String path){
        this.path = path;
    }


    public String getPath(){
        return this.path;
    }
}
