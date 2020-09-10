package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PEQualityMainChartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mContext;
    private List<KeyValue> dataList;
    private int loadState = 2;

    public PEQualityMainChartAdapter(Activity mContext) {
        this.mContext=mContext;
        this.dataList = new ArrayList<>();

    }

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        return TagFinal.TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TagFinal.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_e_main_chart_right_item, parent, false);
            return new ItemHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder){
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.index=position;
            iHolder.name.setText(iHolder.bean.getId());
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }


    private class ItemHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView name;
        private int index;
        KeyValue bean;
        public ItemHolder(View itemView) {
            super(itemView);
            name =  itemView.findViewById(R.id.p_e_main_right_item_name);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemOnc!=null){
                        itemOnc.onc(bean);
                    }
                }
            });
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
    public interface PEChartRightOnC {
        void onc(KeyValue bean);
    }
    public PEChartRightOnC itemOnc;
    public void setItemOnc(PEChartRightOnC itemOnc) {
        this.itemOnc = itemOnc;
    }
}
