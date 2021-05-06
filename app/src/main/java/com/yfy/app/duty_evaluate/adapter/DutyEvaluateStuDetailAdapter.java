package com.yfy.app.duty_evaluate.adapter;

import android.app.Activity;
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
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.ConvertObject;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;


/**
 * Created by zxx.
 * Date: 2021/1/19
 */
public class DutyEvaluateStuDetailAdapter extends BaseRecyclerAdapter {


    private List<KeyValue> dataList;
    public DutyEvaluateStuDetailAdapter(Activity mContext) {
        super(mContext);
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
    public ReViewHolder initViewHolder(ViewGroup parent, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.duty_evaluate_tea_recode_item_layout, parent, false));
        }
        if (position == TagFinal.TYPE_TOP) {
            return new ItemHolder(inflater.inflate(R.layout.duty_evaluate_tea_recode_item_layout, parent, false));
        }
        if (position == TagFinal.TYPE_TXT) {
            return new TagHolder(inflater.inflate(R.layout.duty_evaluate_stu_detail_item_tag, parent, false));
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
            iHolder.title.setText(StringUtils.stringToGetTextJoint("%1$s:%2$s",iHolder.bean.getTitle(),iHolder.bean.getValue()));
            iHolder.setFlowLayoutTop(iHolder.bean.getCpwBeanArrayList(), iHolder.bean.getView_type());

        }

    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }


    private class TagHolder extends ReViewHolder {


        public TagHolder(View itemView) {
            super(itemView);



        }
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


        }


        private void setFlowLayoutTop(List<CPWBean> top_jz,int type){




            LayoutInflater mInflater = LayoutInflater.from(mContext);
            if (flow.getChildCount()!=0){
                flow.removeAllViews();
            }
            int position=0;
            for (CPWBean bean:top_jz){
                RelativeLayout view_layout = (RelativeLayout) mInflater.inflate(R.layout.public_type_progress_name,flow, false);
                TextView title=view_layout.findViewById(R.id.moral_convention_item_name);
                ProgressBar se_progressbar=view_layout.findViewById(R.id.moral_convention_item_progressbar);
                ProgressBar ratingBar=view_layout.findViewById(R.id.moral_convention_item_progressbar_two);

                int score=MathTool.randomIntMinMax(40,30);
                ratingBar.setProgress(score);
                title.setText(StringUtils.stringToGetTextJoint("%1$s%2$s-%3$s:\t",score,"%",bean.getName()));
                if (type == TagFinal.TYPE_TOP) {
                    ratingBar.setMax(100);
                    ratingBar.setVisibility(View.VISIBLE);
                    se_progressbar.setVisibility(View.GONE);
                    switch (position){
                        case 0:
                            ViewTool.alterProgressBarFirstBackgroundColor(ratingBar,Color.parseColor("#ECA742"),Color.TRANSPARENT);
                            break;
                        case 1:
                            ViewTool.alterProgressBarFirstBackgroundColor(ratingBar,Color.parseColor("#62D0F5"),Color.TRANSPARENT);
                            break;
                        case 2:
                            ViewTool.alterProgressBarFirstBackgroundColor(ratingBar,Color.parseColor("#F6E155"),Color.TRANSPARENT);
                            break;
                        case 3:
                            ViewTool.alterProgressBarFirstBackgroundColor(ratingBar,Color.parseColor("#E8958B"),Color.TRANSPARENT);
                            break;
                        case 4:
                            ViewTool.alterProgressBarFirstBackgroundColor(ratingBar,Color.parseColor("#7BE8BC"),Color.TRANSPARENT);
                            break;
                        default:
                            ViewTool.alterProgressBarFirstBackgroundColor(ratingBar,Color.parseColor("#ECA742"),Color.TRANSPARENT);
                            break;
                    }
                    position++;
                }else{
                    ratingBar.setVisibility(View.VISIBLE);
                    se_progressbar.setVisibility(View.VISIBLE);
                    ratingBar.setMax(15);
                    se_progressbar.setMax(15);

                    ViewTool.alterProgressBarFirstBackgroundColor(
                            ratingBar,
                            ColorRgbUtil.getResourceColor(mContext,R.color.user_type_stu),
                            Color.TRANSPARENT);
                    ViewTool.alterProgressSecondaryBackgroundColor(
                            se_progressbar,
                            ColorRgbUtil.getResourceColor(mContext,R.color.user_type_family),
                            ColorRgbUtil.getResourceColor(mContext,R.color.user_type_tea),
                            Color.TRANSPARENT);


                    int one= ConvertObject.getInstance().getInt(bean.getOne());
                    int two=one+ ConvertObject.getInstance().getInt(bean.getTwo());
                    int three=MathTool.randomIntMinMax(5,9);
                    Logger.e(String.valueOf(three));
                    ratingBar.setProgress(one);
                    se_progressbar.setSecondaryProgress(two);
                    se_progressbar.setProgress(three);
                }

                title.setTextColor(ColorRgbUtil.getGrayText());
                flow.addView(view_layout);
            }
        }
    }

}
