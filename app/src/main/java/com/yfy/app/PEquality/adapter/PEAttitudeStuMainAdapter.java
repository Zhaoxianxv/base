package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfy.app.PEquality.PEAttitudeDetailActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.stringtool.StringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;

/**
 * Created by yfy on 2017/12/27.
 */

public class PEAttitudeStuMainAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;

    public PEAttitudeStuMainAdapter(Activity mContext) {
        super(mContext);
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
    public ReViewHolder initViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.p_e_attitude_item, viewGroup, false));
        }
        return new ErrorHolder(viewGroup);
    }


    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.attitude_state.setText(StringUtils.stringToGetTextJoint("%1$s",iHolder.bean.getLeft_title()));
            iHolder.attitude_title.setText(StringUtils.stringToGetTextJoint("时间:%1$s",iHolder.bean.getTitle()));
            iHolder.attitude_content.setText(StringUtils.stringToGetTextJoint("扣分项目:%1$s",iHolder.bean.getContent()));
            iHolder.attitude_sub.setText(StringUtils.stringToGetTextJoint("记录人:%1$s",iHolder.bean.getRight()));
            iHolder.attitude_state.setTextColor(ColorRgbUtil.getBaseColor());
            iHolder.attitude_sub.setTextColor(ColorRgbUtil.getBaseText());
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
        TextView attitude_content;
        CardView layout;
        KeyValue bean;

        public ItemHolder(View itemView) {
            super(itemView);
            attitude_state = itemView.findViewById(R.id.p_e_attitude_state);
            attitude_title = itemView.findViewById(R.id.p_e_attitude_title);
            attitude_sub = itemView.findViewById(R.id.p_e_attitude_sub);
            attitude_content = itemView.findViewById(R.id.p_e_attitude_content);
            layout = itemView.findViewById(R.id.p_e_attitude_layout);
            layout.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View v) {
                    Intent intent=new Intent(mContext,PEAttitudeDetailActivity.class);
                    if (intentStart!=null){
                        intentStart.startIntentAdapter(intent);
                    }

                }
            });
        }
    }


    public StartIntentInterface intentStart;

    public void setIntentStart(StartIntentInterface intentStart) {
        this.intentStart = intentStart;
    }
}
