package com.marckregio.providers;

import android.graphics.Bitmap;
import android.os.Environment;

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
}
