package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.internal.FlowLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.album.MultPicShowActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.charting_mp.animation.Easing;
import com.yfy.charting_mp.charts.PieChart;
import com.yfy.charting_mp.components.Legend;
import com.yfy.charting_mp.data.Entry;
import com.yfy.charting_mp.data.PieData;
import com.yfy.charting_mp.data.PieDataSet;
import com.yfy.charting_mp.utils.PercentFormatter;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.ConvertObject;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.multi.MultiPictureView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 */

public class PEHonorMainAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;
    public PEHonorMainAdapter(Activity mContext) {
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
    @NonNull
    @Override
    public ReViewHolder initViewHolder(ViewGroup parent, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.p_e_honor_main_item_layout, parent, false));
        }
        if (position == TagFinal.TYPE_CHECK) {
            return new ChartPieH(inflater.inflate(R.layout.public_chart_pie_layout, parent, false));
        }
        if (position == TagFinal.TYPE_FLOW_TITLE) {
            return new TopH(inflater.inflate(R.layout.p_e_top_detail, parent, false));
        }
        return new ErrorHolder(parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof TopH) {
            TopH topH = (TopH) holder;
            topH.bean = dataList.get(position);
            topH.setView();
            topH.setFlowLayoutTop(topH.bean.getCpwBeanArrayList());

        }
        if (holder instanceof ChartPieH) {
            ChartPieH pieH = (ChartPieH) holder;
            pieH.bean=dataList.get(position);
            pieH.setData(pieH.bean.getCpwBeanArrayList());

        }
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.index=position;

            iHolder.left_title.setText(iHolder.bean.getLeft_title());
            iHolder.left_sub.setText(iHolder.bean.getTitle());


            iHolder.left_content.setText(iHolder.bean.getContent());
            iHolder.right_state.setText(iHolder.bean.getRight());
            iHolder.right_score.setText(iHolder.bean.getRight_value());


            iHolder.setView();
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }





    private class ItemHolder extends ReViewHolder {
        TextView left_title;
        TextView left_sub;
        TextView left_stu;
        TextView left_content;
        TextView right_state;
        TextView right_score;
        View line;
        MultiPictureView multi;
        LinearLayout bg;
        RelativeLayout layout;
        KeyValue bean;
        int index;
        ItemHolder(View itemView) {
            super(itemView);
            layout =  itemView.findViewById(R.id.p_e_honor_item_layout);
            bg =  itemView.findViewById(R.id.p_e_honor_radio_group);
            line =  itemView.findViewById(R.id.p_e_honor_right_line);
            left_title =  itemView.findViewById(R.id.p_e_honor_left_title);
            left_sub =  itemView.findViewById(R.id.p_e_honor_left_sub);
            left_stu =  itemView.findViewById(R.id.p_e_honor_left_sub_name);
            left_content =  itemView.findViewById(R.id.p_e_honor_content);
            right_state =  itemView.findViewById(R.id.p_e_honor_right_state);
            right_score =  itemView.findViewById(R.id.p_e_honor_right_score);
            multi =  itemView.findViewById(R.id.p_e_honor_item_multi);
            multi.setItemClickCallback(new MultiPictureView.ItemClickCallback() {
                @Override
                public void onItemClicked(@NotNull View view, int index, @NotNull ArrayList<String> uris) {
                    Intent intent=new Intent(mContext, MultPicShowActivity.class);
                    Bundle b=new Bundle();
                    b.putStringArrayList(TagFinal.ALBUM_SINGE_URI,uris);
                    b.putInt(TagFinal.ALBUM_LIST_INDEX,index);
                    intent.putExtras(b);
                    mContext.startActivity(intent);
                }
            });
            layout.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View view) {
//                    if (Base.user.getUsertype().equalsIgnoreCase(Base.USER_TYPE_TEA)){
                    if (true){
                        Intent intent=new Intent();
                        intent.putExtra(Base.id,bean.getId());
                        intent.putExtra(Base.data,bean);
                        intent.putExtra(Base.index,index);
                        if (intentStart!=null){
                            intentStart.startIntentAdapter(intent);
                        }
                    }

                }
            });
        }

        public void setView(){

//            if (Base.user.getUsertype().equalsIgnoreCase(Base.USER_TYPE_TEA)){
            if (true){
                left_stu.setVisibility(View.VISIBLE);
                left_stu.setText(bean.getName());
            }else{
                left_stu.setVisibility(View.GONE);
            }

            if (StringJudge.isEmpty(bean.getListValue())){
                multi.setVisibility(View.GONE);
            }else{
                multi.setVisibility(View.VISIBLE);
                multi.setList(bean.getListValue());
            }

            ViewTool.alterGradientDrawableStrokeColor(mContext,bg,ColorRgbUtil.getResourceColor(mContext,R.color.red));
            switch (bean.getRight()){
                case "已通过":
                    right_score.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                    line.setBackgroundColor(ColorRgbUtil.getResourceColor(mContext,R.color.ForestGreen));
                    right_state.setTextColor(ColorRgbUtil.getResourceColor(mContext,R.color.ForestGreen));
                    right_score.setTextColor(ColorRgbUtil.getResourceColor(mContext,R.color.ForestGreen));
                    ViewTool.alterGradientDrawableStrokeColor(mContext,bg,ColorRgbUtil.getResourceColor(mContext,R.color.ForestGreen));
                    break;
                case "已拒绝":
                    right_state.setTextColor(ColorRgbUtil.getResourceColor(mContext,R.color.Gray));
                    right_score.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                    ViewTool.alterGradientDrawableStrokeColor(mContext,bg,ColorRgbUtil.getResourceColor(mContext,R.color.Gray));
                    break;
                case "待审核":
                    right_state.setTextColor(ColorRgbUtil.getResourceColor(mContext,R.color.OrangeRed));
                    right_score.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                    ViewTool.alterGradientDrawableStrokeColor(mContext,bg,ColorRgbUtil.getResourceColor(mContext,R.color.OrangeRed));
                    break;
            }
        }


    }



    class TopH extends ReViewHolder {
        AppCompatTextView title;
        TextView title_star;
        FlowLayout flow;
        RelativeLayout layout;
        LinearLayout star_layout;
        KeyValue bean;
        TopH(View itemView) {
            super(itemView);
            star_layout =  itemView.findViewById(R.id.public_type_flow_star_layout);
            layout =  itemView.findViewById(R.id.public_type_flow_layout);
            title =  itemView.findViewById(R.id.public_type_flow_title);
            title_star =  itemView.findViewById(R.id.public_type_flow_star_title);
            flow =  itemView.findViewById(R.id.public_type_flow_flow);
        }


        public void setView(){
            title_star.setText("你已进入我校体育人才储备库");
            title.setText("已通过审核的荣誉比赛纳入数据统计");
            if (MathTool.stringToInt(bean.getValue())>=60){
                star_layout.setVisibility(View.VISIBLE);
            }else{
                star_layout.setVisibility(View.GONE);
            }
        }

        private void setFlowLayoutTop(List<CPWBean> top_jz){

            LayoutInflater mInflater = LayoutInflater.from(mContext);
            if (flow.getChildCount()!=0){
                flow.removeAllViews();
            }
            for (CPWBean bean:top_jz){
                final TextView tv = (TextView) mInflater.inflate(R.layout.check_flowlayout_tv,flow, false);
                tv.setTextColor(ColorRgbUtil.getGrayText());

                tv.setText(bean.getName());

                flow.addView(tv);
            }
        }



    }

    class ChartPieH extends ReViewHolder {
        CardView cardView;
        PieChart mChart;
        KeyValue bean;
        ChartPieH(View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.public_chart_pie_card);
            mChart=  itemView.findViewById(R.id.public_chart_pie_chart);

            //----------重新设置card_View高度--------
            int width=ViewTool.getScreenWidth(mContext)-ViewTool.dpPointPx(mContext,20);
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) cardView.getLayoutParams();
//            params.height=width;
//            cardView.setLayoutParams(params);

            mChart.setUsePercentValues(true);
            mChart.setDescription("");
            mChart.setDragDecelerationFrictionCoef(0.95f);
            mChart.setDrawHoleEnabled(true);
//            mChart.setDragDecelerationEnabled(false);
            mChart.setTouchEnabled(false);
            mChart.setHoleColorTransparent(true);
            mChart.setTransparentCircleColor(Color.WHITE);
            mChart.setTransparentCircleAlpha(110);
            mChart.setHoleRadius(40f);
            mChart.setTransparentCircleRadius(50f);
            mChart.setDrawCenterText(true);
            mChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            mChart.setRotationEnabled(true);
            // mChart.setUnit(" €");
            // mChart.setDrawUnitsInChart(true);
            // add a selection listener
//            mChart.setOnChartValueSelectedListener();
            mChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
            // mChart.spin(2000, 0, 360);

            Legend l = mChart.getLegend();
            l.setEnabled(false);
            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

        }
        private void setData(List<CPWBean> list) {

            mChart.setCenterText(StringUtils.getTextJoint("总分:%1$s",bean.getValue()));




            ArrayList<Entry> yVals1 = new ArrayList<>();
            ArrayList<String> xVals = new ArrayList<>();



            if (StringJudge.isEmpty(list)){
                xVals.add("无数据");
                yVals1.add(new Entry(5f, 0));
            }else{
                for (int i = 0; i < list.size(); i++) {
                    CPWBean bean=list.get(i);
                    yVals1.add(new Entry(ConvertObject.getInstance().getFloat(bean.getValue()), i));
                    xVals.add(bean.getName());
                }
            }




            PieDataSet dataSet = new PieDataSet(yVals1, "");
            dataSet.setDrawValues(true);
            dataSet.setSliceSpace(1f);
            dataSet.setSelectionShift(5f);

            // add a lot of colors

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#99C71585"));
            colors.add(Color.parseColor("#997B68EE"));
            colors.add(Color.parseColor("#9900CED1"));
            colors.add(Color.parseColor("#99008000"));
            colors.add(Color.parseColor("#99FFA500"));
            dataSet.setColors(colors);

            PieData data = new PieData(xVals, dataSet);
            data.setDrawValues(true);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(9f);
            data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(tf);
            mChart.setData(data);

            // undo all highlights
            mChart.highlightValues(null);

            mChart.invalidate();
        }
    }





    public StartIntentInterface intentStart;

    public void setIntentStart(StartIntentInterface intentStart) {
        this.intentStart = intentStart;
    }
}
