package com.marckregio.gallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.marckregio.gallery.adapter.RecyclerViewAdapter;
import com.marckregio.gallery.model.ImageItem;
import com.marckregio.gallery.util.Files;
import com.marckregio.gallery.util.RecyclerViewSettings;
import com.marckregio.ui.FontAwesome;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    
    //TODO : Setup Reusability
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<ImageItem> imageItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontAwesome.apply(); // apply first before any views
        setContentView(R.layout.activity_gallery);

        scanSD();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        RecyclerViewSettings.applySettings(recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(this, imageItems);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void scanSD(){
        Files.setOUTPUT("DCIM/Camera");
        List<String> imagePaths = Files.getImageFilePaths();
        imageItems = new ArrayList<>();
        for (String path : imagePaths){
            imageItems.add(new ImageItem(path));
        }

    }

    public void uncheckOthers(ImageItem selected){
        for (ImageItem item: imageItems){
            if (selected != item){
                item.setState(ImageItem.ImageState.unselected);
                item.stateChanged();
            }
        }
    }
}
