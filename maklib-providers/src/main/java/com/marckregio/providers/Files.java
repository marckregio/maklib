package com.marckregio.providers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makregio on 11/04/2017.
 */

public class Files {

    private static String OUTPUT = "maklib-default";

    private static void checkAndMkdir(String dirName) {
        File dirFile = new File(dirName);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            dirFile.mkdirs();
        }
    }

    public static String getMainDir() {
        String retVal = Environment.getExternalStorageDirectory().getAbsolutePath();
        retVal = retVal + "/" + OUTPUT+ "/";
        checkAndMkdir(retVal);
        return retVal;
    }

    public static void cleanDirectory(){
        for(File file: new File(getMainDir()).listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    public static void setOUTPUT(String folder){
        OUTPUT = folder;
    }

    public static List<String> getImageFilePaths(){
        List<String> images = new ArrayList<>();
        String JPEG = ".jpg";
        String JPEG2 = ".jpeg";
        String PNG = ".png";
        File[] listFile = new File(getMainDir()).listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    getImageFilePaths();
                } else {
                    if (listFile[i].getName().endsWith(JPEG) ||
                            listFile[i].getName().endsWith(JPEG2) ||
                            listFile[i].getName().endsWith(PNG)) {
                        images.add(listFile[i].getAbsolutePath());
                    }
                }
            }
        }

        return images;
    }

    public static String saveImage(String img_name, Bitmap bitmap) {
        if (bitmap == null) {
            return "";
        }

        String img_file_name = getMainDir() + img_name + ".png";
        File img_file = new File(img_file_name);
        if (img_file.exists()) {
            img_file.delete();
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(img_file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img_file_name;
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
}
