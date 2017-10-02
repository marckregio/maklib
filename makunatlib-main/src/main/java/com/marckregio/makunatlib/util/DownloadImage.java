package com.marckregio.makunatlib.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by makregio on 30/01/2017.
 */

public class DownloadImage extends AsyncTask<String, Void, Bitmap>{

    public DownloadImage(){

    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String thumb_url = strings[0];
        Bitmap bitmap = null;

        try {
            InputStream inputStream = new URL(thumb_url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
