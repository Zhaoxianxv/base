package com.yfy.app.duty_evaluate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.internal.FlowLayout;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.duty_evaluate.DutyEvaluateStuDetailActivity;
import com.yfy.base.R;
import com.yfy.base.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

/**
 * Created by zxx.
 * Date: 2021/1/15
 */
public class DutyEvaluateRecodeAdapter extends BaseRecyclerAdapter {


    public String year_s,month_s;

    public void setYear_s(String year_s) {
        this.year_s = year_s;
    }

    public void setMonth_s(String month_s) {
        this.month_s = month_s;
    }

    private List<KeyValue> dataList;
    public DutyEvaluateRecodeAdapter(Activity mContext) {
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
    public ReViewHolder initViewHolder(ViewGroup parent, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.duty_evaluate_tea_recode_item_layout, parent, false));
        }
        return new ErrorHolder(inflater.inflate(R.layout.public_type_error, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof ItemHolder){
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.index=position;
            iHolder.title.setText(StringUtils.stringToGetTextJoint("%1$s\t·总分:%3$d·第%2$s名",iHolder.bean.getName(),position+1,MathTool.randomIntMinMax(40,30)));
            iHolder.setFlowLayoutTop(new ArrayList<CPWBean>());

        }

    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }


    private class ItemHolder extends ReViewHolder {
        RelativeLayout layout;
        TextView title;
        FlowLayout flow;
        int index;
        KeyValue bean;
        public ItemHolder(View itemView) {
            super(itemView);
            layout =  itemView.findViewById(R.id.duty_evaluate_stu_item_layout);
            title =  itemView.findViewById(R.id.public_type_flow_star_title);
            flow =  itemView.findViewById(R.id.public_type_flow_flow);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext,DutyEvaluateStuDetailActivity.class);
                    intent.putExtra(Base.stu_bean,bean);
                    intent.putExtra(Base.year_value,year_s);
                    intent.putExtra(Base.month_value,month_s);
                    mContext.startActivity(intent);
                }
            });

        }


        private void setFlowLayoutTop(List<CPWBean> top_jz){


            top_jz.add(new CPWBean("遵守纪律",""));
            top_jz.add(new CPWBean("热爱学习",""));
            top_jz.add(new CPWBean("强健体魄",""));
            top_jz.add(new CPWBean("表率文雅",""));
            top_jz.add(new CPWBean("勤于劳动",""));

            LayoutInflater mInflater = LayoutInflater.from(mContext);
            if (flow.getChildCount()!=0){
                flow.removeAllViews();
            }
            int position=0;
            for (CPWBean bean:top_jz){
                RelativeLayout view_layout = (RelativeLayout) mInflater.inflate(R.layout.public_type_progress_name,flow, false);
                TextView title=view_layout.findViewById(R.id.moral_convention_item_name);
                ProgressBar ratingBar=view_layout.findViewById(R.id.moral_convention_item_progressbar);
                ratingBar.setVisibility(View.GONE);

                ProgressBar se_progressbar=view_layout.findViewById(R.id.moral_convention_item_progressbar_two);

                title.setTextColor(ColorRgbUtil.getGrayText());
                switch (position){
                    case 0:
                        ViewTool.alterProgressBarFirstBackgroundColor(se_progressbar,Color.parseColor("#ECA742"),Color.TRANSPARENT);
                        break;
                    case 1:
                        ViewTool.alterProgressBarFirstBackgroundColor(se_progressbar,Color.parseColor("#62D0F5"),Color.TRANSPARENT);
                        break;
                    case 2:
                        ViewTool.alterProgressBarFirstBackgroundColor(se_progressbar,Color.parseColor("#F6E155"),Color.TRANSPARENT);
                        break;
                    case 3:
                        ViewTool.alterProgressBarFirstBackgroundColor(se_progressbar,Color.parseColor("#E8958B"),Color.TRANSPARENT);
                        break;
                    case 4:
                        ViewTool.alterProgressBarFirstBackgroundColor(se_progressbar,Color.parseColor("#7BE8BC"),Color.TRANSPARENT);
                        break;
                    default:
                        ViewTool.alterProgressBarFirstBackgroundColor(se_progressbar,Color.parseColor("#ECA742"),Color.TRANSPARENT);
                        break;
                }
                position++;
                se_progressbar.setMax(100);
                se_progressbar.setVisibility(View.VISIBLE);
                int score=MathTool.randomIntMinMax(40,30);
                se_progressbar.setProgress(score);
                title.setText(StringUtils.stringToGetTextJoint("%1$s%2$s-%3$s:\t",score,"%",bean.getName()));
                flow.addView(view_layout);
            }
        }
    }


}
