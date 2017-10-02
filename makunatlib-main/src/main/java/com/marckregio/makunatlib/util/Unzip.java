package com.marckregio.makunatlib.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by makregio on 01/02/2017.
 */

public class Unzip extends AsyncTask<String, Integer, Boolean> {

    private String location;

    public Unzip(String location){
        this.location = location;
    }


    public boolean unzip(String zipFile) {
        String retFilePath = null;
        byte[] buffer = new byte[1024];

        try {

            //create output directory is not exists
            File folder = new File(location);
            if (!folder.exists()) {
                folder.mkdir();
            }


            //get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(location + File.separator + fileName);

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                if (newFile.getName().contains(".")) {
//                    if (!newFile.isDirectory()) {
                    if (newFile.getName().toLowerCase(Locale.getDefault()).endsWith(".pdf")) {
                        retFilePath = newFile.getParent();
                    }
                    FileOutputStream fos = new FileOutputStream(newFile);

                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }

                    fos.flush();
                    fos.close();
                } else {
                    newFile.mkdir();
                }
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            retFilePath = null;
            return false;
        } finally {
            deletezip(zipFile);
        }

        return true;
    }

    private void deletezip(String zipfile){
        File zip = new File(zipfile);
        if (zip.exists()){
            zip.delete();
        }
    }


    @Override
    protected Boolean doInBackground(String... strings) {
        return unzip(strings[0]);
    }
}
