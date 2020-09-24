package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PEScoreMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Activity mContext;
    public List<KeyValue> dataList;
    public int loadState = 2;

    public PEScoreMainAdapter(Activity mContext) {
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TagFinal.TYPE_ITEM) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_e_score_item_layout, parent, false);
            return new ItemHolder(item);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.left_title.setText(iHolder.bean.getTitle());
            iHolder.left_sub.setText(iHolder.bean.getLeft_title());
            iHolder.right_score.setText(StringUtils.stringToGetTextJoint("得分:%1$s",iHolder.bean.getRight_name()));
            iHolder.right_weight.setText(StringUtils.stringToGetTextJoint("占比:%1$s",iHolder.bean.getRight_value()));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView left_title;
        TextView left_sub;
        TextView right_weight;
        TextView right_score;
        KeyValue bean;
        ItemHolder(View itemView) {
            super(itemView);
            left_title =  itemView.findViewById(R.id.p_e_score_title);
            left_sub =  itemView.findViewById(R.id.p_e_score_math);
            right_weight =  itemView.findViewById(R.id.p_e_score_weight);
            right_score =  itemView.findViewById(R.id.p_e_score_num);

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
