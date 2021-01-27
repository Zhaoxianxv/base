package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfy.app.PEquality.PEAttitudeDetailActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.StringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PEQualityAttitudeAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;

    public PEQualityAttitudeAdapter(Activity mContext) {
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
        if (viewType == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.p_e_attitude_item, parent, false));
        }
        if (viewType == TagFinal.TYPE_DETAIL) {
            return new DetailH(inflater.inflate(R.layout.public_item_singe_top_txt_center, parent, false));
        }

        return new ErrorHolder(parent);
    }


    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.attitude_state.setText(iHolder.bean.getValue());
            iHolder.attitude_title.setText(iHolder.bean.getLeft_title());
            iHolder.attitude_content.setText(iHolder.bean.getContent());
            iHolder.attitude_sub.setText(iHolder.bean.getTitle());
            iHolder.attitude_tea.setVisibility(View.VISIBLE);
            iHolder.attitude_tea.setText(StringUtils.stringToGetTextJoint("记录人:%1$s",iHolder.bean.getRight()));
            iHolder.attitude_state.setTextColor(ColorRgbUtil.getBaseColor());
            if (StringUtils.arraysByteToStringAt0(iHolder.bean.getValue()).equalsIgnoreCase("+")){
                iHolder.attitude_state.setTextColor(ColorRgbUtil.getForestGreen());
            }else{
                iHolder.attitude_state.setTextColor(ColorRgbUtil.getBaseColor());
            }


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


    private class ItemHolder extends ReViewHolder {
        TextView attitude_state;
        TextView attitude_title;
        TextView attitude_sub;
        TextView attitude_tea;
        TextView attitude_content;
        CardView layout;
        KeyValue bean;

        public ItemHolder(View itemView) {
            super(itemView);
            attitude_state = itemView.findViewById(R.id.p_e_attitude_state);
            attitude_title = itemView.findViewById(R.id.p_e_attitude_title);
            attitude_content = itemView.findViewById(R.id.p_e_attitude_content);
            attitude_sub = itemView.findViewById(R.id.p_e_attitude_sub);
            attitude_tea = itemView.findViewById(R.id.p_e_attitude_tea);
            layout = itemView.findViewById(R.id.p_e_attitude_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,PEAttitudeDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public class DetailH extends ReViewHolder {
        TextView name;
        KeyValue bean;
        public DetailH(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.public_top_text);
        }
    }


}
