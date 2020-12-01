package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;

import com.yfy.charting.formatter.IndexAxisValueFormatter;
import com.yfy.charting.formatter.ValueFormatter;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.charting.animation.Easing;
import com.yfy.charting.charts.LineChart;
import com.yfy.charting.components.Legend;
import com.yfy.charting.components.XAxis;
import com.yfy.charting.components.YAxis;
import com.yfy.charting.data.Entry;
import com.yfy.charting.data.LineData;
import com.yfy.charting.data.LineDataSet;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfy on 2017/12/27.
 */

public class PEScoreAlterMainAdapter extends BaseRecyclerAdapter {

    public List<KeyValue> dataList;

    public PEScoreAlterMainAdapter(Activity mContext) {
        super(mContext);
        this.dataList = new ArrayList<>();

    }

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getView_type();
    }

    @NonNull
    @Override
    public ReViewHolder initViewHolder(@NonNull ViewGroup parent, int position) {
        if (position == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.p_e_score_item_layout, parent, false));
        }
        if (position == TagFinal.TYPE_SELECT_GROUP) {
            return new LineChartH(inflater.inflate(R.layout.public_type_line_chart, parent, false));
        }

        return new ErrorHolder(parent);
    }


    @Override
    public void bindHolder(@NonNull ReViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.left_title.setText(iHolder.bean.getTitle());
            iHolder.left_sub.setText(iHolder.bean.getLeft_title());
            iHolder.right_score.setText(StringUtils.stringToGetTextJoint("得分:%1$s",iHolder.bean.getRight_name()));
            iHolder.right_weight.setText(StringUtils.stringToGetTextJoint("占比:%1$s",iHolder.bean.getRight_value()));
        }
        if (holder instanceof LineChartH) {
            LineChartH chartH = (LineChartH) holder;
            chartH.bean = dataList.get(position);
            chartH.left_title.setText(chartH.bean.getTitle());
            chartH.initChart(chartH.mChart);

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ItemHolder extends ReViewHolder {
        TextView left_title;
        TextView left_sub;
        TextView right_weight;
        TextView right_score;
        KeyValue bean;
        ItemHolder(View itemView) {
            super(itemView);
            left_title =  itemView.findViewById(R.id.p_e_score_title);
            left_sub =  itemView.findViewById(R.id.p_e_score_math);
            right_weight =  itemView.findViewById(R.id.p_e_score_weight);
            right_score =  itemView.findViewById(R.id.p_e_score_num);

        }
    }

    public class LineChartH extends ReViewHolder {
        TextView left_title;
        LineChart mChart;
        KeyValue bean;
        LineChartH(View itemView) {
            super(itemView);
            left_title =  itemView.findViewById(R.id.p_e_score_title);
            mChart =  itemView.findViewById(R.id.p_e_score_line_chart);

        }


        private void setData(float range) {

            List<String> xVals=StringUtils.listToStringSplitCharacters("18年-上,18年-下,19年-上,19年-下,20年-上,21,22",",");

            ArrayList<Entry> yVals = new ArrayList<>();
            int i = 0;
            for (String s:xVals) {

                float mult = (range + 1);
                float val = (float) (Math.random() * mult) + 3;// + (float)

                yVals.add(new Entry(i,val));
                i++;
            }

            XAxis xAxis = mChart.getXAxis();
            //自定义设置横坐标
            ValueFormatter xValueFormatter = new IndexAxisValueFormatter(xVals);
            xAxis.setValueFormatter(xValueFormatter);

            // 折线图链接线设置
            LineDataSet set1 = new LineDataSet(yVals, "z");

            //设置线样式
            set1.enableDashedLine(10f, 5f, 0f);
            //line 颜色
            set1.setColor(ColorRgbUtil.getBaseColor());
            //点颜色
            set1.setCircleColor(ColorRgbUtil.getBaseColor());
            //线粗细
            set1.setLineWidth(1f);
            set1.setCircleSize(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(7f);
//            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

            //链接线数组
//            ArrayList<LineDataSet> dataSets = new ArrayList<>();
//            dataSets.add(set1);

            LineData data = new LineData(set1);


            float ratio = (float) xVals.size()/(float) 6;
            //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
            mChart.zoom(ratio,1f,0,0);
            // set data
            mChart.setData(data);
            mChart.invalidate();
        }
//
        private void initChart(LineChart mChart){



            //设置描述文本不显示
            mChart.getDescription().setEnabled(false);
            //设置是否显示表格背景
            mChart.setDrawGridBackground(true);
            //设置是否可以触摸
            mChart.setTouchEnabled(true);
            mChart.setDragDecelerationFrictionCoef(0.9f);
            //设置是否可以拖拽
            mChart.setDragEnabled(true);
            //设置是否可以缩放
            mChart.setScaleEnabled(false);
            mChart.setDrawGridBackground(false);
            mChart.setHighlightPerDragEnabled(true);
            mChart.setPinchZoom(true);
            //设置背景颜色
//            mChart.setBackgroundColor(ColorAndImgUtils.CHART_BACKGROUND_COLOR);


            //设置从X轴出来的动画时间
            //mLineChart.animateX(1500);
            //设置XY轴动画
            mChart.animateXY(1500,1500, Easing.EaseInSine, Easing.EaseInSine);






            XAxis xAxis = mChart.getXAxis();

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawLabels(true);
            //X轴线绘制 true false
            xAxis.setDrawAxisLine(true);
            //X网格图
            xAxis.setDrawGridLines(false);
            xAxis.setTextSize(8f);
            //设置一页最大显示个数为6，超出部分就滑动


            YAxis leftAxis = mChart.getAxisLeft();//
            YAxis rightAxis = mChart.getAxisRight();//右边数据
//            rightAxis.setDrawTopYLabelEntry(false);
            //是否显示
            rightAxis.setEnabled(false);
            rightAxis.setDrawGridLines(false);

            //是否显示
            leftAxis.setEnabled(true);
            //Y轴线绘制 true false
            leftAxis.setDrawAxisLine(true);
            //Y网格图
            leftAxis.setDrawGridLines(false);
            leftAxis.setAxisMaxValue(100f);
            leftAxis.setAxisMinValue(0f);
            //从0开始
            leftAxis.setStartAtZero(true);
            leftAxis.setTextSize(7f);


            Legend l = mChart.getLegend();
            l.setEnabled(false);//

            setData(100);
        }

    }













}
