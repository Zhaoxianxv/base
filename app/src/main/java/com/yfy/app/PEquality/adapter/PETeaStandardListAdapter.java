package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.final_tag.stringtool.StringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by yfy on 2017/12/27.
 */

public class PETeaStandardListAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;

    public PETeaStandardListAdapter(Activity mContext) {
        super(mContext);
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
    public ReViewHolder initViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TagFinal.TYPE_TOP) {
            return new TopHolder(inflater.inflate(R.layout.public_item_singe_top_txt, parent, false));
        }
        if (viewType == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.p_e_tea_stadard_item, parent, false));
        }

        return new ErrorHolder(parent);
    }


    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.left_title.setText(iHolder.bean.getRight());
            if (iHolder.bean.getValue().equalsIgnoreCase("")){
                iHolder.left_sub.setText(StringUtils.stringToGetTextJoint("%1$s：%2$s",iHolder.bean.getTitle(),"暂未打分"));
                iHolder.left_title.setTextColor(ColorRgbUtil.getBaseColor());
            }else{
                iHolder.left_title.setTextColor(ColorRgbUtil.getForestGreen());
                iHolder.left_sub.setText(StringUtils.stringToGetTextJoint("%1$s：%2$s",iHolder.bean.getTitle(),iHolder.bean.getValue()));
            }
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


    private class ItemHolder extends ReViewHolder {
        AppCompatTextView left_title;
//        AppCompatTextView stu_name;
        AppCompatTextView left_sub;
        AppCompatTextView left_score;
        AppCompatTextView left_weight;
        AppCompatTextView right_title;
        AppCompatTextView right_value;
        AppCompatTextView event_grade;
        AppCompatTextView event_title;
        KeyValue bean;
        ItemHolder(View itemView) {
            super(itemView);
//            stu_name =  itemView.findViewById(R.id.p_e_standard_stu_name);
            left_title =  itemView.findViewById(R.id.p_e_standard_title);
            left_sub =  itemView.findViewById(R.id.p_e_standard_value);
            left_weight =  itemView.findViewById(R.id.p_e_standard_score);
            left_score =  itemView.findViewById(R.id.p_e_standard_weight);
            right_value =  itemView.findViewById(R.id.p_e_standard_end_score_value);
            event_grade =  itemView.findViewById(R.id.p_e_standard_end_event_grade);

        }
    }


    private class TopHolder extends ReViewHolder {
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
