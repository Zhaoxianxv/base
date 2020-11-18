package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PEQualityStandardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mContext;
    private List<KeyValue> dataList;
    private int loadState = 2;

    public PEQualityStandardListAdapter(Activity mContext) {
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
        if (viewType == TagFinal.TYPE_TOP) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_item_singe_top_txt, parent, false);
            return new TopHolder(view);
        }
        if (viewType == TagFinal.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_e_stadard_item, parent, false);
            return new ItemHolder(view);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.left_title.setText(iHolder.bean.getTitle());
            iHolder.left_value.setText(iHolder.bean.getValue());
            iHolder.right.setText(iHolder.bean.getRight());
            iHolder.right_key.setText(iHolder.bean.getRight_key());
            iHolder.right_name.setText(iHolder.bean.getRight_name());
            iHolder.right_value.setText(iHolder.bean.getRight_value());


        }
        if (holder instanceof TopHolder){
            TopHolder topHolder = (TopHolder) holder;
            topHolder.bean = dataList.get(position);
            topHolder.top_title.setText(StringUtils.stringToGetTextJoint("%1$s:%2$s",topHolder.bean.getName(),topHolder.bean.getValue()));
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private class ItemHolder extends RecyclerView.ViewHolder {
        AppCompatTextView left_title;
        AppCompatTextView left_value;

        AppCompatTextView right_name;
        AppCompatTextView right_key;
        AppCompatTextView right_value;
        AppCompatTextView right;
        KeyValue bean;
        ItemHolder(View itemView) {
            super(itemView);
            left_title =  itemView.findViewById(R.id.p_e_standard_title);
            left_value =  itemView.findViewById(R.id.p_e_standard_value);
            right_key =  itemView.findViewById(R.id.p_e_standard_score);
            right_name =  itemView.findViewById(R.id.p_e_standard_weight);
            right_value =  itemView.findViewById(R.id.p_e_standard_end_score_value);
            right =  itemView.findViewById(R.id.p_e_standard_end_event_grade);

        }
    }


    private class TopHolder extends RecyclerView.ViewHolder {
        AppCompatTextView top_title;
        KeyValue bean;
        TopHolder(View itemView) {
            super(itemView);
            top_title =  itemView.findViewById(R.id.public_center_title);

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
