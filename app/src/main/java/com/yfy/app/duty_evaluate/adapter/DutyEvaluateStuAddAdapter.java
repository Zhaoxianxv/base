package com.yfy.app.duty_evaluate.adapter;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.internal.FlowLayout;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by zxx.
 * Date: 2021/1/20
 */
public class DutyEvaluateStuAddAdapter  extends BaseRecyclerAdapter {



    private List<KeyValue> dataList;
    public DutyEvaluateStuAddAdapter(Activity mContext) {
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
            return new ItemHolder(inflater.inflate(R.layout.duty_evaluate_stu_add_item, parent, false));
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
            iHolder.title.setText(StringUtils.stringToGetTextJoint("%1$s",iHolder.bean.getTitle()));
            iHolder.setFlowLayoutTop(iHolder.bean.getCpwBeanArrayList());

        }

    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }


    private class ItemHolder extends ReViewHolder {
        TextView title;
        FlowLayout flow;
        int index;
        KeyValue bean;
        public ItemHolder(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.duty_evaluate_stu_add_parent_title);
            flow =  itemView.findViewById(R.id.public_type_flow_flow);

        }


        private void setFlowLayoutTop(List<CPWBean> top_jz){




            LayoutInflater mInflater = LayoutInflater.from(mContext);
            if (flow.getChildCount()!=0){
                flow.removeAllViews();
            }
            for (CPWBean bean:top_jz){
                RelativeLayout view_layout = (RelativeLayout) mInflater.inflate(R.layout.duty_evaluate_stu_add,flow, false);

                AppCompatTextView title=view_layout.findViewById(R.id.duty_evaluate_stu_add_child_title);
                RatingBar stu_rb =view_layout.findViewById(R.id.duty_evaluate_stu_do_rating);
                RatingBar family_rb=view_layout.findViewById(R.id.duty_evaluate_family_do_rating);

                title.setTextColor(ColorRgbUtil.getGrayText());
                title.setText(bean.getName());

                stu_rb.setMax(2);
                stu_rb.setNumStars(2);
                stu_rb.setRating(2);
                stu_rb.setStepSize(1);
                stu_rb.setIsIndicator(false);

                family_rb.setMax(2);
                family_rb.setNumStars(2);
                family_rb.setRating(2);
                family_rb.setStepSize(1);
                family_rb.setIsIndicator(false);

                stu_rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        String select_score=String .valueOf(MathTool.getIntToDecimals(ratingBar.getRating()));
                        Logger.e(select_score);
                    }
                });
                family_rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        String select_score=String .valueOf(MathTool.getIntToDecimals(ratingBar.getRating()));
                        Logger.e(select_score);

                    }
                });

                flow.addView(view_layout);
            }
        }
    }


}
