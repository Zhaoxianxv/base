package com.yfy.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yfy.app.PEquality.PEHonorMainActivity;
import com.yfy.app.PEquality.PEQualityAttenListActivity;
import com.yfy.app.PEquality.PEQualityAttitudeActivity;
import com.yfy.app.PEquality.PEQualityHomeworkActivity;
import com.yfy.app.PEquality.PEQualityKnowledgeActivity;
import com.yfy.app.PEquality.PEQualitySkillsActivity;
import com.yfy.app.PEquality.PEQualityStandardListActivity;
import com.yfy.app.PEquality.PEQualitySuggestActivity;
import com.yfy.app.PEquality.PERecipeActivity;
import com.yfy.app.PEquality.tea.PEQualityTeaSuggestActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.GlideTools;

import java.util.ArrayList;
import java.util.List;

public class SelectStuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mContext;
    private List<KeyValue> dataList;
    private int loadState = 2;
    public int index_pos;

    public void setIndex(int index) {
        this.index_pos = index;
    }
    public String type;

    public void setType(String type) {
        this.type = type;
    }

    public SelectStuAdapter(Activity mContext) {
        this.mContext=mContext;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TagFinal.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_stu_gridview_item, parent, false);
            return new ItemHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder){
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.index=position;
            iHolder.user_name.setText(iHolder.bean.getName());
            iHolder.user_score.setText(iHolder.bean.getRight_value());
            GlideTools.chanCircle(mContext,iHolder.bean.getValue(),iHolder.user_head,R.drawable.ic_parent_head);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }


    public class ItemHolder extends RecyclerView.ViewHolder {

        AppCompatTextView user_name;
        AppCompatTextView user_score;
        AppCompatImageView user_head;
        private RelativeLayout layout;
        private int index;
        KeyValue bean;
        public ItemHolder(View itemView) {
            super(itemView);
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
                            intent=new Intent();
                            intent.putExtra(Base.data,bean);
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
                        case "健康教育知识"://PEQualityKnowledgeActivity
                            intent=new Intent(mContext,PEQualityKnowledgeActivity.class);
                            intent.putExtra(Base.title,type);
                            mContext.startActivity(intent);
                            break;
                        case "运动技能":
                            intent=new Intent(mContext,PEQualitySkillsActivity.class);
                            intent.putExtra(Base.title,type);
                            intent.putExtra(Base.type,TagFinal.TRUE);
                            mContext.startActivity(intent);
                            break;
                        case "体能":
                            intent=new Intent(mContext,PEQualityTeaSuggestActivity.class);
                            intent.putExtra(Base.title,type);
                            intent.putExtra(Base.type,"体能");
                            mContext.startActivity(intent);
                            break;
                        case "课后作业":
                            intent=new Intent(mContext,PEQualityHomeworkActivity.class);
                            intent.putExtra(Base.title,type);
                            intent.putExtra(Base.type,TagFinal.TRUE);
                            mContext.startActivity(intent);
                            break;
                        case "国家体质标准":
                            intent=new Intent(mContext,PEQualityStandardListActivity.class);
                            intent.putExtra(Base.title,type);
                            intent.putExtra(Base.type,TagFinal.TRUE);
                            mContext.startActivity(intent);
                            break;
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
                        case "体育荣誉证书":
                            intent=new Intent(mContext,PEHonorMainActivity.class);
                            intent.putExtra(Base.title,bean.getName());
                            intent.putExtra(Base.type,TagFinal.TRUE);
                            mContext.startActivity(intent);
                            break;
                        case "体育比赛成绩":
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
