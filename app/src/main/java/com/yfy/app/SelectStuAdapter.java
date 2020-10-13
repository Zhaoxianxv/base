package com.yfy.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yfy.app.PEquality.PEHonorMainActivity;
import com.yfy.app.PEquality.tea.PEQualityAttenListActivity;
import com.yfy.app.PEquality.PEQualityAttitudeActivity;
import com.yfy.app.PEquality.PEQualityHomeworkActivity;
import com.yfy.app.PEquality.PERecipeActivity;
import com.yfy.app.PEquality.tea.PEQualityTeaSuggestActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.GlideTools;

import java.util.ArrayList;
import java.util.List;

public class SelectStuAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;
    private int index_pos;

    public void setIndex(int index) {
        this.index_pos = index;
    }
    public String type;

    public void setType(String type) {
        this.type = type;
    }

    SelectStuAdapter(Activity mContext) {
        super(mContext);
        this.dataList = new ArrayList<>();

    }

    public List<KeyValue> getDataList() {
        return dataList;
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
            return new ItemHolder(inflater.inflate(R.layout.public_stu_gridview_item, parent, false));
        }
        return null;
    }


    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof ItemHolder){
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.index=position;
            iHolder.user_name.setText(iHolder.bean.getName());
            iHolder.user_score.setText(iHolder.bean.getRight_value());
            GlideTools.chanCircle(mContext,iHolder.bean.getValue(),iHolder.user_head,R.drawable.ic_parent_head);
            if (iHolder.bean.isIs_selected()){
                iHolder.select_state.setVisibility(View.VISIBLE);
            }else{
                iHolder.select_state.setVisibility(View.GONE);
            }
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
        private RelativeLayout layout;
        private int index;
        KeyValue bean;
        public ItemHolder(View itemView) {
            super(itemView);
            select_state =  itemView.findViewById(R.id.stu_grid_select);
            user_score =  itemView.findViewById(R.id.stu_grid_state);
            user_name =  itemView.findViewById(R.id.stu_grid_name);
            user_head =  itemView.findViewById(R.id.stu_grid_head);
            layout = itemView.findViewById(R.id.stu_grid_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    type=bean.getType();
                    switch (type){
                        case "select_stu":
                            bean.setIs_selected(!bean.isIs_selected());
                            notifyItemChanged(index,bean);
                            break;
                        case "select_stu_one":
                            intent=new Intent();
                            intent.putExtra(Base.data,bean.getName());
                            intent.putParcelableArrayListExtra("all", (ArrayList<? extends Parcelable>) dataList);
                            intent.putExtra(Base.index,index_pos);
                            mContext.setResult(Activity.RESULT_OK,intent);
                            mContext.finish();
                            break;
                        case "请假":
                            intent=new Intent(mContext,PEQualityAttenListActivity.class);
                            intent.putExtra(Base.title,type);
                            intent.putExtra(Base.type,TagFinal.TRUE);
                            mContext.startActivity(intent);
                            break;
                        case "学习态度采集":
                            intent=new Intent(mContext,PEQualityAttitudeActivity.class);
                            intent.putExtra(Base.title,type);
                            intent.putExtra(Base.type,TagFinal.TRUE);
                            mContext.startActivity(intent);
                            break;
//                        case "健康教育知识"://PEQualityKnowledgeActivity
//                            intent=new Intent(mContext,PEQualityKnowledgeActivity.class);
//                            intent.putExtra(Base.title,type);
//                            mContext.startActivity(intent);
//                            break;

//                        case "体能":
//                            intent=new Intent(mContext,PEQualityTeaSuggestActivity.class);
//                            intent.putExtra(Base.title,type);
//                            intent.putExtra(Base.type,"体能");
//                            mContext.startActivity(intent);
//                            break;
                        case "课后作业":
                            intent=new Intent(mContext,PEQualityHomeworkActivity.class);
                            intent.putExtra(Base.title,type);
                            intent.putExtra(Base.type,TagFinal.TRUE);
                            mContext.startActivity(intent);
                            break;
//                        case "国家体质标准":
//                            intent=new Intent(mContext,PEQualityStandardListActivity.class);
//                            intent.putExtra(Base.title,type);
//                            intent.putExtra(Base.type,TagFinal.TRUE);
//                            mContext.startActivity(intent);
//                            break;
                        case "膳食建议":
                            intent=new Intent(mContext,PEQualityTeaSuggestActivity.class);
                            intent.putExtra(Base.title,type);
                            intent.putExtra(Base.type,type);
                            mContext.startActivity(intent);
                            break;

                        case "课堂表现":
                            intent=new Intent(mContext,PEQualityTeaSuggestActivity.class);
                            intent.putExtra(Base.title,type);
                            intent.putExtra(Base.type,type);
                            mContext.startActivity(intent);
                            break;

                        case "运动处方":
                            intent=new Intent(mContext,PERecipeActivity.class);
                            intent.putExtra(Base.title,"运动处方");
                            intent.putExtra(Base.type,TagFinal.TRUE);
                            mContext.startActivity(intent);
                            break;

                        case "荣誉比赛":
                            intent=new Intent(mContext,PEHonorMainActivity.class);
                            intent.putExtra(Base.title,bean.getName());
                            intent.putExtra(Base.type,TagFinal.TRUE);
                            mContext.startActivity(intent);
                            break;
                    }

                }
            });
        }
    }






}
