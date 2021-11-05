package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by yfy on 2017/12/27.
 */

public class PEStuWorkMainAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;

    public PEStuWorkMainAdapter(Activity mContext) {
        super(mContext);
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
    public ReViewHolder initViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View

        if (viewType == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.public_people_list_item_layout, parent, false));
        }

        return new ErrorHolder(parent);
    }


    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.setView();
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private class ItemHolder extends ReViewHolder {
        AppCompatTextView tv_title;
        AppCompatTextView tv_title_sub;
        AppCompatTextView tv_content;
        AppCompatTextView tv_state;
        AppCompatImageView iv_head;
        RelativeLayout layout_relative;
        KeyValue bean;
        ItemHolder(View itemView) {
            super(itemView);
            tv_title =  itemView.findViewById(R.id.title_tv_people_public);
            layout_relative =  itemView.findViewById(R.id.relative_layout_people_public);
            tv_title_sub =  itemView.findViewById(R.id.sub_title_tv_people_public);
            tv_content =  itemView.findViewById(R.id.content_tv_people_public);
            tv_state =  itemView.findViewById(R.id.state_tv_people_public);
            iv_head =  itemView.findViewById(R.id.head_iv_people_public);
            layout_relative.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View view) {

                    if (itemIntentStart!=null){
                        Intent intent=new Intent();
                        itemIntentStart.startIntentAdapter(intent,bean.getId());
                    }
                }
            });
        }
        public void setView(){
            iv_head.setVisibility(View.GONE);
            tv_title.setText(bean.getName());
            tv_title_sub.setText("管理员");
            tv_content.setText(bean.getValue());

            if (bean.getId().equalsIgnoreCase(TagFinal.TRUE)){
                tv_state.setTextColor(ColorRgbUtil.getResourceColor(mContext,R.color.ForestGreen));
                tv_state.setText("已完成");
            }else{
                tv_state.setTextColor(ColorRgbUtil.getResourceColor(mContext,R.color.OrangeRed));
                tv_state.setText("未完成");
            }
        }
    }




    private StartIntentInterface itemIntentStart;

    public void setItemIntentStart(StartIntentInterface itemIntentStart) {
        this.itemIntentStart = itemIntentStart;
    }
}
