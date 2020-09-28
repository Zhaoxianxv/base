package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.base.adapter.BaseRecyclerAdapter;
import com.yfy.base.adapter.ReViewHolder;
import com.yfy.charting_mp.animation.Easing;
import com.yfy.charting_mp.charts.LineChart;
import com.yfy.charting_mp.components.Legend;
import com.yfy.charting_mp.components.LimitLine;
import com.yfy.charting_mp.components.XAxis;
import com.yfy.charting_mp.components.YAxis;
import com.yfy.charting_mp.data.Entry;
import com.yfy.charting_mp.data.LineData;
import com.yfy.charting_mp.data.LineDataSet;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PEScoreMainAdapter extends BaseRecyclerAdapter {

    public List<KeyValue> dataList;

    public PEScoreMainAdapter(Activity mContext) {
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
    public ReViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.p_e_score_item_layout, parent, false));
        }
        if (viewType == TagFinal.TYPE_SELECT_GROUP) {
            return new LineChartH(inflater.inflate(R.layout.p_e_score_year_layout, parent, false));
        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull ReViewHolder holder, int position) {
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

            List<String> yearlist=StringUtils.listToStringSplitCharacters("18年-上,18年-下,19年-上,19年-下,20年-上",",");
            ArrayList<String> xVals = new ArrayList<String>();
            for (String s:yearlist) {
                xVals.add(s);
            }

            ArrayList<Entry> yVals = new ArrayList<Entry>();
            int i = 0;
            for (String s:yearlist) {

                float mult = (range + 1);
                float val = (float) (Math.random() * mult) + 3;// + (float)

                yVals.add(new Entry(val, i));
                i++;
            }

            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(yVals, "");


            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleSize(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);


            ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
            dataSets.add(set1); // add the datasets


            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);


            // set data
            mChart.setData(data);
        }
//
        public void initChart(LineChart mChart){

            // set this to true to draw the grid background, false if not
            mChart.setDrawGridBackground(false);
            mChart.setDescription("");
            mChart.setTouchEnabled(false);
            // if true, dragging is enabled for the chart
            mChart.setDragEnabled(false);

            // if disabled, scaling can be done on x- and y-axis separately

            //
            mChart.setPinchZoom(false);

           //Set this to true to enable scaling (zooming in and out by gesture)
            mChart.setScaleEnabled(false);
            mChart.setScaleXEnabled(false);
            mChart.setScaleYEnabled(false);
            //绘制边框4边
            mChart.setDrawBorders(false);
//            mChart.setBorderColor(ColorRgbUtil.getBaseColor());
            //




            XAxis xAxis = mChart.getXAxis();

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawLabels(true);//
            xAxis.setDrawAxisLine(false);
            //网格图
            xAxis.setDrawGridLines(false);
            xAxis.setTextSize(6f);

            YAxis leftAxis = mChart.getAxisLeft();//
            YAxis rightAxis = mChart.getAxisRight();//右边数据
//            rightAxis.setDrawTopYLabelEntry(false);

            rightAxis.setEnabled(false);
            rightAxis.setDrawGridLines(false);
            leftAxis.setDrawGridLines(false);
            leftAxis.setAxisMaxValue(100f);
            leftAxis.setAxisMinValue(0f);
            leftAxis.setStartAtZero(true);//从0开始
            leftAxis.setEnabled(false);


            Legend l = mChart.getLegend();
            l.setEnabled(false);//

            setData(100);
        }

    }




}
