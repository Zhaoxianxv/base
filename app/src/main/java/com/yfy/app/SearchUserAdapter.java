package com.yfy.app;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class SearchUserAdapter extends BaseRecyclerAdapter {


    private List<KeyValue> dataList;
    private String select_type;
    private int index_position;

    public void setIndex_position(int index_position) {
        this.index_position = index_position;
    }

    public void setSelect_type(String select_type) {
        this.select_type = select_type;
    }

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    public List<KeyValue> getDataList() {
        return dataList;
    }

    public SearchUserAdapter(Activity mContext){
        super(mContext);
        this.dataList = new ArrayList<>();
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
            View view = inflater.inflate(R.layout.search_user_item, parent, false);
            return new SearchItemH(view);
        }
        return new ErrorHolder(parent);
    }

    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof SearchItemH) {
            SearchItemH reHolder = (SearchItemH) holder;
            reHolder.bean=dataList.get(position);
            reHolder.index=position;
            reHolder.setView();

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class SearchItemH extends ReViewHolder {

        public AppCompatTextView user_name;
        public AppCompatImageView user_head;
        public AppCompatImageView checkBox;
        public RelativeLayout relativeLayout;
        KeyValue bean;
        int index;

        public SearchItemH(View itemView) {
            super(itemView);
            user_name =  itemView.findViewById(R.id.search_user_name);
            user_head =  itemView.findViewById(R.id.search_user_head);
            checkBox = itemView.findViewById(R.id.search_user_check);
            relativeLayout =  itemView.findViewById(R.id.search_user_layout);
            relativeLayout.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View view) {
                    if (select_type.equalsIgnoreCase(TagFinal.TRUE)){
                        Intent intent=new Intent();
                        intent.putExtra(Base.data,bean);
                        intent.putExtra(Base.index,index_position);
                        if (intentStart!=null){
                            intentStart.startIntentAdapter(intent,"type");
                        }
                    }else{
                        bean.setRequired(!bean.isRequired());
                        notifyItemChanged(index);
                    }

                }
            });
        }

        public void setView(){
            user_name.setText(bean.getName());
            GlideTools.chanCircle(mContext,bean.getKey(),user_head,R.drawable.ic_parent_head);
            if (select_type.equalsIgnoreCase(TagFinal.TRUE)){
                checkBox.setVisibility(View.GONE);
            }
            if (bean.isRequired()){
//                checkBox.setImageResource(R.drawable.ic_radio_button_checked_black_24dp);
            }else{
//                checkBox.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
            }
        }

    }






    public StartIntentInterface intentStart;

    public void setIntentStart(StartIntentInterface intentStart) {
        this.intentStart = intentStart;
    }






}
