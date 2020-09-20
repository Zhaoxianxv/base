package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.PEquality.PEQualityAttenListActivity;
import com.yfy.app.PEquality.PEQualityAttitudeDetailActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PEQualityAttitudeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mContext;
    private List<KeyValue> dataList;
    private int loadState = 2;

    public PEQualityAttitudeAdapter(Activity mContext) {
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

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_e_attitude_item, parent, false);
            return new ItemHolder(view);
        }
        if (viewType == TagFinal.TYPE_DETAIL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_item_singe_top_txt_center, parent, false);
            return new DetailH(view);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.attitude_state.setText(iHolder.bean.getRight());
            iHolder.attitude_title.setText(iHolder.bean.getTitle());
            iHolder.attitude_content.setText(iHolder.bean.getContent());
            iHolder.attitude_sub.setVisibility(View.GONE);
        }
        if (holder instanceof DetailH) {
            DetailH detailH = (DetailH) holder;
            detailH.bean = dataList.get(position);
            detailH.name.setText(detailH.bean.getName());
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private class ItemHolder extends RecyclerView.ViewHolder {
        public TextView attitude_state;
        public TextView attitude_title;
        public TextView attitude_sub;
        public TextView attitude_content;
        private RelativeLayout layout;
        KeyValue bean;

        public ItemHolder(View itemView) {
            super(itemView);
            attitude_state = itemView.findViewById(R.id.p_e_attitude_state);
            attitude_title = itemView.findViewById(R.id.p_e_attitude_title);
            attitude_sub = itemView.findViewById(R.id.p_e_attitude_sub);
            attitude_content = itemView.findViewById(R.id.p_e_attitude_content);
            layout = itemView.findViewById(R.id.p_e_attitude_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,PEQualityAttitudeDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private class DetailH extends RecyclerView.ViewHolder {
        public TextView name;
        KeyValue bean;
        public DetailH(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.public_top_text);
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
