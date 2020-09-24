package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PETeaMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Activity mContext;
    public List<KeyValue> dataList;
    public int loadState = 2;

    public PETeaMainAdapter(Activity mContext) {
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


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TagFinal.TYPE_ITEM) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_e_tea_main_item, parent, false);
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
            iHolder.left_sub.setVisibility(View.GONE);
            iHolder.line_layout.setVisibility(View.GONE);
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
        LinearLayout line_layout;
        LinearLayout item_layout;
        KeyValue bean;
        ItemHolder(View itemView) {
            super(itemView);
            item_layout =  itemView.findViewById(R.id.p_e_score_item_layout);
            line_layout =  itemView.findViewById(R.id.p_e_score_layout);
            left_title =  itemView.findViewById(R.id.p_e_score_title);
            left_sub =  itemView.findViewById(R.id.p_e_score_math);
            right_weight =  itemView.findViewById(R.id.p_e_score_weight);
            right_score =  itemView.findViewById(R.id.p_e_score_num);

            item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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


    public interface ItemOnc{
        void onc(KeyValue bean);
    }
    public ItemOnc itemOnc;
    public void setItemOnc(ItemOnc itemOnc) {
        this.itemOnc = itemOnc;
    }
}
