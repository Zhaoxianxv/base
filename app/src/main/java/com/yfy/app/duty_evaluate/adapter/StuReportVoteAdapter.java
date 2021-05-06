package com.yfy.app.duty_evaluate.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.duty_evaluate.bean.ReportThree;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class StuReportVoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<ReportThree> dataList=new ArrayList<>();
    private Activity mContext;
    private String user_type,show_type;

    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public void setDataList(List<ReportThree> dataList) {
        this.dataList = dataList;
//        getRandomHeights(dataList);
    }

    public List<ReportThree> getDataList() {
        return dataList;
    }

    // 当前加载状态，默认为加载完成
    private int loadState = 2;

    public StuReportVoteAdapter(Activity mContext){
        this.mContext=mContext;
        this.dataList = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        return dataList.get(position).getView_type();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View


        if (viewType == TagFinal.TYPE_CHILD){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stu_report_table_child_layout, parent, false);
            return new ChildHolder(view);
        }
        if (viewType == TagFinal.TYPE_PARENT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_item_singe_top_txt_center, parent, false);
            return new ParentHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChildHolder) {
            ChildHolder cHolder = (ChildHolder) holder;
            cHolder.bean=dataList.get(position);
            cHolder.index=position;
            cHolder.checkedTextView.setText(cHolder.bean.getTablename());
            if (show_type.equalsIgnoreCase("tea_vote")){
                if (cHolder.bean.getTeacherselect().equalsIgnoreCase(TagFinal.TRUE)){
                    cHolder.checkedTextView.setChecked(true);
                }else{
                    cHolder.checkedTextView.setChecked(false);
                }
            }else{
                if (cHolder.bean.getStuselect().equalsIgnoreCase(TagFinal.TRUE)){
                    cHolder.checkedTextView.setChecked(true);
                }else{
                    cHolder.checkedTextView.setChecked(false);
                }
            }


        }
        if (holder instanceof ParentHolder) {
            ParentHolder pHolder = (ParentHolder) holder;
            pHolder.bean=dataList.get(position);
            pHolder.time.setText(pHolder.bean.getTablename());
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }

    public class ChildHolder extends RecyclerView.ViewHolder {
        CheckedTextView checkedTextView;
        RelativeLayout layout;
        public ReportThree bean;
        int index;
        ChildHolder(View itemView) {
            super(itemView);
            checkedTextView=  itemView.findViewById(R.id.stu_report_child_check);
            layout=  itemView.findViewById(R.id.stu_report_child_check_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (show_type.equalsIgnoreCase("tea_vote")&&user_type.equalsIgnoreCase(Base.USER_TYPE_TEA)){
                        if (bean.getTeacherselect().equalsIgnoreCase(TagFinal.TRUE)){
                            bean.setTeacherselect(TagFinal.FALSE);
                        }else {
                            bean.setTeacherselect(TagFinal.TRUE);
                        }
                        notifyItemChanged(index,bean);

                    }
                    if (show_type.equalsIgnoreCase("stu_vote")&&user_type.equalsIgnoreCase(Base.USER_TYPE_STU)){
                        if (bean.getStuselect().equalsIgnoreCase(TagFinal.TRUE)){
                            bean.setStuselect(TagFinal.FALSE);
                        }else {
                            bean.setStuselect(TagFinal.TRUE);
                        }
                        notifyItemChanged(index,bean);
                    }


                }
            });

//            checkedTextView.

        }
    }

    public class ParentHolder extends RecyclerView.ViewHolder {
        TextView time;
        public ReportThree bean;
        ParentHolder(View itemView) {
            super(itemView);
            time= (TextView) itemView.findViewById(R.id.public_top_text);
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
