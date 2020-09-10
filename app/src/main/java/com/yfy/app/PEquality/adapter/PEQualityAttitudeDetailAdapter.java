package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.PEquality.PEQualityAttitudeDetailActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PEQualityAttitudeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mContext;
    private List<KeyValue> dataList;
    private int loadState = 2;

    public PEQualityAttitudeDetailAdapter(Activity mContext) {
        this.mContext = mContext;
        this.dataList = new ArrayList<>();

    }

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        return dataList.get(position).getView_type();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
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


    private class ItemHolder extends RecyclerView.ViewHolder {
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
