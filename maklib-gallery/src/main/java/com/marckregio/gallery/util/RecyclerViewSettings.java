package com.marckregio.gallery.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by eCopy on 10/3/2017.
 */

public class RecyclerViewSettings {

    private static GridLayoutManager gridLayoutManager; //use for 2 or more columns in Recyclerview
    private static LinearLayoutManager linearLayoutManager; //use for 1 column list

    public static void applySettings(RecyclerView recyclerView){
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(5);
        recyclerView.setDrawingCacheEnabled(true);
        gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}
