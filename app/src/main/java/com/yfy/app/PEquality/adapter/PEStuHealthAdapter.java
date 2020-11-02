package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.PEquality.tea.PEQualityTeaSuggestActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.final_tag.stringtool.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PEStuHealthAdapter extends BaseRecyclerAdapter {


    private String type;

    public void setType(String type) {
        this.type = type;
    }

    private List<KeyValue> dataList;
    public void setDataList(List<KeyValue> dataList) {

        this.dataList = dataList;
    }

    public PEStuHealthAdapter(Activity mContext) {
        super(mContext);
        this.dataList = new ArrayList<>();

    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TagFinal.TYPE_FOOTER;
        } else {
            return dataList.get(position).getView_type();
        }
    }

    @Override
    public ReViewHolder initViewHolder(ViewGroup parent, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TagFinal.TYPE_ITEM) {
            return new HealthH(inflater.inflate(R.layout.p_e_stu_health_item, parent, false));
        }
        if (position == TagFinal.TYPE_FOOTER) {
            return new FootViewHolder(inflater.inflate(R.layout.recyclerview_refresh_footer, parent, false));
        }
        return new ErrorHolder(parent);
    }

    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof HealthH) {
            HealthH healthH = (HealthH) holder;
            healthH.bean=dataList.get(position);
            healthH.title.setText(healthH.bean.getName());
            healthH.value.setText(StringUtils.stringToGetTextJoint("%1$s\t%2$s",healthH.bean.getValue(),healthH.bean.getKey()));
        }

        if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case TagFinal.LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case TagFinal.LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case TagFinal.LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }


    private class HealthH extends ReViewHolder {


        TextView title;
        TextView value;
        KeyValue bean;
        HealthH(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.p_e_health_title);
            value =  itemView.findViewById(R.id.p_e_health_value);
        }
    }

    private class FootViewHolder extends ReViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;
        RelativeLayout allEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading =  itemView.findViewById(R.id.pb_loading);
            tvLoading =  itemView.findViewById(R.id.tv_loading);
            llEnd =  itemView.findViewById(R.id.ll_end);
            allEnd =  itemView.findViewById(R.id.recycler_bottom);

        }
    }


}
