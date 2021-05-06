package com.yfy.app.duty_evaluate.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfy.app.duty_evaluate.bean.ReportThree;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;


/**
 * Created by yfy1 on 2016/10/17.
 */
public class StuReportVoteAdapter extends BaseRecyclerAdapter {



    private List<ReportThree> dataList;
    private String user_type,show_type;

    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public void setDataList(List<ReportThree> dataList) {
        this.dataList = dataList;
    }

    public List<ReportThree> getDataList() {
        return dataList;
    }



    public StuReportVoteAdapter(Activity mContext){
        super(mContext);
        this.dataList = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        return dataList.get(position).getView_type();
    }

    @Override
    public ReViewHolder initViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View

        if (viewType == TagFinal.TYPE_CHILD){
            View view = inflater.inflate(R.layout.stu_report_table_child_layout, parent, false);
            return new ChildHolder(view);
        }
        if (viewType == TagFinal.TYPE_PARENT){
            View view = inflater.inflate(R.layout.public_item_singe_top_txt_center, parent, false);
            return new ParentHolder(view);
        }

        return null;
    }

    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof ChildHolder) {
            ChildHolder cHolder = (ChildHolder) holder;
            cHolder.bean=dataList.get(position);
            cHolder.index=position;
            cHolder.checkedTextView.setText(cHolder.bean.getTablename());

            cHolder.setView();


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

    public class ChildHolder extends ReViewHolder {
        TextView checkedTextView;
        AppCompatImageView score_one;
        AppCompatImageView score_two;
        AppCompatImageView score_three;
        public ReportThree bean;
        int index;
        ChildHolder(View itemView) {
            super(itemView);
            score_one=  itemView.findViewById(R.id.stu_report_child_check_one);
            score_two=  itemView.findViewById(R.id.stu_report_child_check_two);
            score_three=  itemView.findViewById(R.id.stu_report_child_check_three);
            checkedTextView=  itemView.findViewById(R.id.stu_report_child_check);
            score_one.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View view) {
                    setScore("0");
                }
            });
            score_two.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View view) {
                    setScore("1");
                }
            });
            score_three.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View view) {
                    setScore("2");
                }
            });

        }
        public void setView(){
            if (show_type.equalsIgnoreCase("tea_vote")){
                if (StringJudge.isEmpty(bean.getTeacherselect())){
                    setScoreState("");
                }else{
                    setScoreState(bean.getTeacherselect());
                }
            }else{
                if (StringJudge.isEmpty(bean.getStuselect())){
                    setScoreState("");
                }else{
                    setScoreState(bean.getStuselect());
                }
            }
        }
        public void setScore(String score){
            if (show_type.equalsIgnoreCase("tea_vote")){
                bean.setTeacherselect(score);
            }else{
                bean.setStuselect(score);
            }
            notifyItemChanged(index,bean);
        }

        public void setScoreState(String score_type){
            ViewTool.alterBSDSColor(score_one, ColorRgbUtil.getResourceColor(mContext,R.color.Gainsboro));
            ViewTool.alterBSDSColor(score_two, ColorRgbUtil.getResourceColor(mContext,R.color.Gainsboro));
            ViewTool.alterBSDSColor(score_three, ColorRgbUtil.getResourceColor(mContext,R.color.Gainsboro));
            switch (score_type){
                case "0":
                    ViewTool.alterBSDSColor(score_one, ColorRgbUtil.getResourceColor(mContext,R.color.app_base_color));
                    break;
                case "1":
                    ViewTool.alterBSDSColor(score_two, ColorRgbUtil.getResourceColor(mContext,R.color.app_base_color));
                    break;
                case "2":
                    ViewTool.alterBSDSColor(score_three, ColorRgbUtil.getResourceColor(mContext,R.color.app_base_color));
                    break;

            }

        }
    }

    public class ParentHolder extends ReViewHolder {
        TextView time;
        public ReportThree bean;
        ParentHolder(View itemView) {
            super(itemView);
            time=  itemView.findViewById(R.id.public_top_text);
        }
    }





}
