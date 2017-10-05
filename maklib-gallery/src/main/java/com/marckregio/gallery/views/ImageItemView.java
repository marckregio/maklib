package com.marckregio.gallery.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.marckregio.gallery.GalleryActivity;
import com.marckregio.gallery.R;
import com.marckregio.gallery.model.ImageItem;
import com.marckregio.gallery.model.ImageState;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by eCopy on 10/3/2017.
 */

public class ImageItemView extends LinearLayout implements Observer, View.OnClickListener {

    private Activity parentActivity;
    private ProgressBar circleProgress;
    private ImageView image;
    private TextView imageName;
    private TextView checkIcon;

    private ImageItem mainObject;
    private ImageState defaultState = ImageState.loading;

    public ImageItemView(Context context) {
        super(context);
        initView();
    }

    public ImageItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        parentActivity = (Activity) getContext();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_item, this, true);

        view.setOnClickListener(this);

        circleProgress = (ProgressBar) view.findViewById(R.id.circle_progress);
        image = (ImageView) view.findViewById(R.id.image);
        imageName = (TextView) view.findViewById(R.id.image_name);
        checkIcon = (TextView) view.findViewById(R.id.check);
    }


    private synchronized void updateView(){
        if (mainObject == null) return;
        if (parentActivity == null) return;

        final ImageState state = mainObject.getState();

        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (state){
                    case selected:
                        checkIcon.setVisibility(VISIBLE);
                        break;
                    case unselected:
                        checkIcon.setVisibility(INVISIBLE);
                        break;
                    case loading:
                        circleProgress.setVisibility(VISIBLE);
                        break;
                    default:
                        break;
                }

                if (defaultState != state){
                    defaultState = state;
                }

                changeViewState();
            }
        });
    }

    private void changeViewState(){
        imageName.setText(mainObject.getFileName());

        if (image.getDrawable() == null) {
            Glide.with(parentActivity)
                    .load(mainObject.getPath())
                    .into(new DrawableImageViewTarget(image) {
                        @Override
                        public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            super.onResourceReady(resource, transition);
                            circleProgress.setVisibility(View.GONE);
                            mainObject.setState(ImageState.unselected);
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        if (mainObject == null) return;

        if (view == this){
            if (mainObject.getState() == ImageState.unselected){
                mainObject.setState(ImageState.selected);
                ((GalleryActivity) parentActivity).uncheckOthers(mainObject); //HACK only. Find cleaner way.
            } else {
                mainObject.setState(ImageState.unselected);
                ((GalleryActivity) parentActivity).uncheckOthers(null); //HACK only. Find cleaner way.
            }
            mainObject.stateChanged();

        }
    }

    @Override
    public void update(Observable observable, Object object) {
        mainObject = (ImageItem) observable;
        updateView();
    }

}
