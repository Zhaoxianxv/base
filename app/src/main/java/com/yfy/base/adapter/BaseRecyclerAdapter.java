package com.yfy.base.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<ReViewHolder> {
    public LayoutInflater inflater;
    public int loadState = 2;
    public Activity mContext;

    public BaseRecyclerAdapter(Activity context) {
        this.mContext=context;
        inflater=LayoutInflater.from(mContext);
    }


    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}
