package com.marckregio.gallery.model;


import java.io.File;
import java.io.Serializable;
import java.util.Observable;

/**
 * Created by eCopy on 10/2/2017.
 */

public class ImageItem extends Observable implements Serializable{

    private String path;
    private ImageState state = ImageState.loading;

    public ImageItem(String path){
        this.path = path;
    }


    public String getPath(){
        return this.path;
    }

    public String getFileName(){
        File file = new File(path);
        return file.getName();
    }

    public void setState(ImageState state){
        this.state = state;
    }

    public ImageState getState(){
        return this.state;
    }

    public void stateChanged() {
        setChanged();
        notifyObservers();
    }

    public enum ImageState{
        selected,
        unselected,
        loading
    }
}
