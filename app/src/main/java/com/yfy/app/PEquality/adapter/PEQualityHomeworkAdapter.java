package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yfy on 2017/12/27.
 */

public class PEQualityHomeworkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Activity mContext;
    public List<KeyValue> dataList;
    public int loadState = 2;

    public PEQualityHomeworkAdapter(Activity mContext) {
        this.mContext = mContext;
        this.dataList = new ArrayList<>();

    }

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {


        return dataList.get(position).getView_type();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TagFinal.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_type_txt, parent, false);
            return new ItemHolder(view);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.key.setText(iHolder.bean.getKey());
            iHolder.value.setText(iHolder.bean.getValue());
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
        public TextView key;
        public TextView value;
        KeyValue bean;

        public ItemHolder(View itemView) {
            super(itemView);
            key = itemView.findViewById(R.id.public_type_txt_key);
            value = itemView.findViewById(R.id.public_type_txt_value);
        }
    }


    /**
     * 设置上拉加载状态
     *
     * @param loadState 1.正在加载 2.加载完成 3.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
}
