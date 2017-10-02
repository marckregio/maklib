package com.marckregio.gallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by makregio on 11/04/2017.
 */

public class Files {

    private static String OUTPUT = "";

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
        String extension = ".jpg";
        File[] listFile = new File(getMainDir()).listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    getImageFilePaths();
                } else {
                    if (listFile[i].getName().endsWith(extension)) {
                        images.add(listFile[i].getAbsolutePath());
                    }
                }
            }
        }

        return images;
    }
}
