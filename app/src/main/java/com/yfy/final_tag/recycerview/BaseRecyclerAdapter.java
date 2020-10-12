package com.yfy.final_tag.recycerview;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yfy.base.R;

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<ReViewHolder> {
    public LayoutInflater inflater;
    public int loadState = 2;
    public Activity mContext;

    public BaseRecyclerAdapter(Activity context) {
        this.mContext=context;
        inflater=LayoutInflater.from(mContext);
    }



    @Override
    public ReViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return initViewHolder(viewGroup,position);
    }

    public abstract ReViewHolder initViewHolder(ViewGroup viewGroup, int position);

    @Override
    public void onBindViewHolder(ReViewHolder reViewHolder, int position) {
        bindHolder(reViewHolder,position);
    }
    public abstract void bindHolder(ReViewHolder reViewHolder, int position);

    public class ErrorHolder extends ReViewHolder{

        public ErrorHolder(ViewGroup parent) {
            super(inflater.inflate(R.layout.public_type_error, parent, false));
        }

        public ErrorHolder(View itemView) {
            super(itemView);
        }
    }


    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}
