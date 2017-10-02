package com.marckregio.makunatlib.util;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.marckregio.makunatlib.R;


/**
 * Created by makregio on 02/08/16.
 */
public class RecycleViewOnClickListener {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;
    private OnItemTouchListener mOnItemTouchListener;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };
    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };
    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mOnItemTouchListener != null){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemTouchListener.onItemTouched(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };
    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    private RecycleViewOnClickListener(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }



    public static RecycleViewOnClickListener addTo(RecyclerView view) {
        RecycleViewOnClickListener support = (RecycleViewOnClickListener) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new RecycleViewOnClickListener(view);
        }
        return support;
    }

    public static RecycleViewOnClickListener removeFrom(RecyclerView view) {
        RecycleViewOnClickListener support = (RecycleViewOnClickListener) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public RecycleViewOnClickListener setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public RecycleViewOnClickListener setOnItemTouchListener(OnItemTouchListener listener) {
        mOnItemTouchListener = listener;
        return this;
    }

    public RecycleViewOnClickListener setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    public RecycleViewOnClickListener setOnItemSelectedListener(OnItemSelectedListener listener) {
        mOnItemSelectedListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }

    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemTouchListener {
        void onItemTouched(RecyclerView recyclerView, int position, View v);
    }
}
