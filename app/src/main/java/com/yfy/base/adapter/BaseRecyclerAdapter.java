package com.yfy.base.adapter;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<ReViewHolder> {
    public LayoutInflater inflater;
    public int loadState = 2;
    public Activity mContext;

    public BaseRecyclerAdapter(Activity context) {
        this.mContext=context;
        inflater=LayoutInflater.from(mContext);
    }



    @Override
    public ReViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(ReViewHolder reViewHolder, int i) {

    }


    public class ErrorHolder extends ReViewHolder{
        public ErrorHolder(View itemView) {
            super(itemView);
        }
    }


    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}
