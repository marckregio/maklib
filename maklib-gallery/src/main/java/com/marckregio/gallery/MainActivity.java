package com.marckregio.gallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.marckregio.gallery.adapter.RecycleViewAdapter;
import com.marckregio.gallery.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //TODO : Add Listeners
    //TODO : Setup Reusability
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager; //use for 2 or more columns in Recyclerview
    private LinearLayoutManager linearLayoutManager; //use for 1 column list
    private RecycleViewAdapter recycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(5);
        recyclerView.setDrawingCacheEnabled(true);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recycleViewAdapter = new RecycleViewAdapter(this, scanSD());
        recyclerView.setAdapter(recycleViewAdapter);
    }

    private List<ImageItem> scanSD(){
        Files.setOUTPUT("DCIM/Camera");
        List<String> imagePaths = Files.getImageFilePaths();
        List<ImageItem> imageItems = new ArrayList<>();
        for (String path : imagePaths){
            imageItems.add(new ImageItem(path));
        }

        return imageItems;
    }
}
