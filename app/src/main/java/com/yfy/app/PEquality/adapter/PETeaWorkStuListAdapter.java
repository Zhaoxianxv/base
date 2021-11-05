package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yfy.app.PEquality.bean.QualityStu;
import com.yfy.base.R;
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

public class PETeaWorkStuListAdapter extends BaseRecyclerAdapter {

    private List<QualityStu> dataList;

    public PETeaWorkStuListAdapter(Activity mContext) {
        super(mContext);
        this.dataList = new ArrayList<>();

    }

    public void setDataList(List<QualityStu> dataList) {
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
        QualityStu bean;
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
                        itemIntentStart.startIntentAdapter(intent);
                    }
                }
            });
        }
        public void setView(){
            tv_title.setText(bean.getStuname());
        }
    }




    private StartIntentInterface itemIntentStart;

    public void setItemIntentStart(StartIntentInterface itemIntentStart) {
        this.itemIntentStart = itemIntentStart;
    }
}
