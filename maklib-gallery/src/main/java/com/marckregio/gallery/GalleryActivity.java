package com.marckregio.gallery;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.marckregio.gallery.adapter.RecyclerViewAdapter;
import com.marckregio.gallery.model.ImageItem;
import com.marckregio.gallery.model.ImageState;
import com.marckregio.gallery.util.RecyclerViewSettings;
import com.marckregio.providers.Files;
import com.marckregio.ui.DialogView;
import com.marckregio.ui.FontAwesome;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener {

    //TODO : Setup Reusability
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<ImageItem> imageItems;
    private TextView delete, done;
    private ImageItem imageItem;

    private static OnImageSelectListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontAwesome.apply(); // apply first before any views
        setContentView(R.layout.activity_gallery);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        RecyclerViewSettings.applySettings(recyclerView);
        scanSD();

        delete = (TextView) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        done = (TextView) findViewById(R.id.done);
        done.setOnClickListener(this);
    }

    public static void setOnImageSelectListener(OnImageSelectListener l){
        listener = l;
    }

    private void scanSD(){
        List<String> imagePaths = Files.getImageFilePaths();
        imageItems = new ArrayList<>();
        for (String path : imagePaths){
            imageItems.add(new ImageItem(path));
        }

        recyclerView.setAdapter(null);
        recyclerViewAdapter = new RecyclerViewAdapter(this, imageItems);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        scanSD();
    }

    public void uncheckOthers(ImageItem selected){

        imageItem = selected;
        if (selected != null) {
            for (ImageItem item : imageItems) {
                if (selected != item) {
                    item.setState(ImageState.unselected);
                    item.stateChanged();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == delete){
            if (imageItem == null) return;
            new DialogView(this)
                    .showAlert("Confirm", "Are you sure to remove " + imageItem.getFileName() + "?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            imageItem.deleteObservers();
                            File file = new File(imageItem.getPath());
                            file.delete();
                            imageItem = null;
                            scanSD();
                        }
                    })
                    .show();
        } else if(view == done){
            if (listener != null) {
                listener.selectedImage(imageItem.getPath());
            }
            Log.v("IMAGES",imageItem.getPath());
            finish();
        }
    }
}
