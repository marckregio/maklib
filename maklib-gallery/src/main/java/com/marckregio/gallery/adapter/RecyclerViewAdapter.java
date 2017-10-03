package com.marckregio.gallery.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marckregio.gallery.R;
import com.marckregio.gallery.model.ImageItem;
import com.marckregio.gallery.views.ImageItemView;

import java.util.List;

/**
 * Created by makregio on 14/07/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<ImageItem> list;
    private Activity parentActivity;

    public RecyclerViewAdapter(Activity parentActivity, List<ImageItem> list){
        this.parentActivity = parentActivity;
        this.list = list;

        Log.v("LIST", list.size() + "");
    }

    public void setList(List<ImageItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public List<ImageItem> getList(){
        return this.list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        list.get(position).addObserver(holder.imageItemView);
        list.get(position).stateChanged();
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

        ImageItemView imageItemView;

        public ViewHolder(View cell){
            super(cell);

            imageItemView = (ImageItemView) cell.findViewById(R.id.image_view);
        }


    }

}
