package com.yfy.app;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.yfy.app.bean.StuBean;
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

public class SelectStuGroupAdapter extends BaseRecyclerAdapter {

    private List<StuBean> dataList;



    SelectStuGroupAdapter(Activity mContext) {
        super(mContext);
        this.dataList = new ArrayList<>();

    }

    public List<StuBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<StuBean> dataList) {
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
            return new ItemHolder(inflater.inflate(R.layout.public_stu_gridview_item, parent, false));
        }
        return new ErrorHolder(parent);
    }


    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof ItemHolder){
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.index=position;


            iHolder.setView();
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }


    public class ItemHolder extends ReViewHolder {

        AppCompatTextView user_name;
        AppCompatTextView user_score;
        AppCompatImageView user_head;
        AppCompatImageView select_state;
        RelativeLayout layout;
        private int index;
        StuBean bean;
        public ItemHolder(View itemView) {
            super(itemView);
            select_state =  itemView.findViewById(R.id.stu_grid_select);
            user_score =  itemView.findViewById(R.id.stu_grid_state);
            user_name =  itemView.findViewById(R.id.stu_grid_name);
            user_head =  itemView.findViewById(R.id.stu_grid_head);
            layout = itemView.findViewById(R.id.stu_grid_layout);
            layout.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View v) {
                    bean.setIs_selected(!bean.isIs_selected());
                    notifyItemChanged(index,bean);

                    if (intentStart!=null){
                        Intent intent=new Intent();
                        intent.putExtra(Base.state,!bean.isIs_selected());
                        intentStart.startIntentAdapter(intent,bean.getStuid());
                    }

                }
            });
        }



        public void setView(){
            user_name.setText(bean.getStuname());
            user_score.setText(bean.getStusex());
            GlideTools.chanCircle(mContext,bean.getHeadpic(),user_head,R.mipmap.head);
            if (bean.isIs_selected()){
                select_state.setVisibility(View.VISIBLE);
            }else{
                select_state.setVisibility(View.GONE);
            }

        }

    }







    public StartIntentInterface intentStart;

    public void setIntentStart(StartIntentInterface intentStart) {
        this.intentStart = intentStart;
    }

}
