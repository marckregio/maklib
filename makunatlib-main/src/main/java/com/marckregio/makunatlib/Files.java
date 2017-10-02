package com.marckregio.makunatlib;

import android.content.Context;
import android.os.Environment;
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

/**
 * Created by makregio on 11/04/2017.
 */

public abstract class Files {

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

    public static void createObjectFile(Context context, Object object, String filename){
        ObjectOutputStream objectOutput = null;
        FileOutputStream fileOutput = null;

        if (object != null){
            try {
                fileOutput = context.openFileOutput(filename, Context.MODE_PRIVATE);
                OutputStream buffer = new BufferedOutputStream(fileOutput);
                objectOutput = new ObjectOutputStream(buffer);
                objectOutput.writeObject(object);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (objectOutput != null && fileOutput != null){
                    try {
                        Log.v("ObjectFile", "created");
                        objectOutput.close();
                        fileOutput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Object readObjectFromFile(Context context, String filename) {
        ObjectInputStream objectIn = null;
        FileInputStream fileIn = null;
        Object retObj = null;
        try {
            fileIn = context.getApplicationContext().openFileInput(filename);
            objectIn = new ObjectInputStream(fileIn);
            retObj = objectIn.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectIn != null && fileIn != null) {
                try {
                    objectIn.close();
                    fileIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return retObj;
    }
}
