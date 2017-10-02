package com.marckregio.gallery.adapter;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.bumptech.glide.Glide;
import com.marckregio.gallery.R;
import com.marckregio.gallery.model.ImageItem;

import java.util.List;

/**
 * Created by makregio on 14/07/2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{

    private List<ImageItem> list;
    private Activity parentActivity;

    public RecycleViewAdapter(Activity parentActivity, List<ImageItem> list){
        this.parentActivity = parentActivity;
        this.list = list;

        Log.v("LIST", list.size() + "");
    }

    public void setList(List<ImageItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.circleProgress.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(parentActivity).load(list.get(position).getPath()).into(holder.image);
                holder.circleProgress.setVisibility(View.GONE);
            }
        }, 2000);

    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }


    @Override
    public int getItemViewType(int position) {
    return position;
}

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ProgressBar circleProgress;
        ImageView image;

        public ViewHolder(View cell){
            super(cell);

            circleProgress = (ProgressBar) cell.findViewById(R.id.circle_progress);
            image = (ImageView) cell.findViewById(R.id.image);
        }
    }
}
