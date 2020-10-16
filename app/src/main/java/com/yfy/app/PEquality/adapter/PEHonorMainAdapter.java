package com.yfy.app.PEquality.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.FlowLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.PEquality.PEHonorMainActivity;
import com.yfy.app.album.MultPicShowActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.charting_mp.animation.Easing;
import com.yfy.charting_mp.charts.PieChart;
import com.yfy.charting_mp.components.Legend;
import com.yfy.charting_mp.data.Entry;
import com.yfy.charting_mp.data.PieData;
import com.yfy.charting_mp.data.PieDataSet;
import com.yfy.charting_mp.utils.ColorTemplate;
import com.yfy.charting_mp.utils.PercentFormatter;
import com.yfy.final_tag.banner.BaseViewPager;
import com.yfy.final_tag.data.MathTool;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.ConvertObjtect;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.dialog.CPWListBeanView;
import com.yfy.final_tag.permission.PermissionTools;
import com.yfy.final_tag.stringtool.RegexUtils;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
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
    private PEHonorMainActivity mActivity;
    private boolean is_stu=false;
    public PEHonorMainAdapter(PEHonorMainActivity mContext) {
        super(mContext);
        this.mActivity = mContext;
        this.dataList = new ArrayList<>();

    }

    public void setIs_stu(boolean is_stu) {
        this.is_stu = is_stu;
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
    public ReViewHolder initViewHolder( ViewGroup parent, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TagFinal.TYPE_ITEM) {
            return new ItemHolder(inflater.inflate(R.layout.p_e_honor_main_item_layout, parent, false));
        }
        if (position == TagFinal.TYPE_CHECK) {
            return new ChartPieH(inflater.inflate(R.layout.public_chart_pie_layout, parent, false));
        }
        if (position == TagFinal.TYPE_FLOW_TITLE) {
            return new TopH(inflater.inflate(R.layout.public_type_flow, parent, false));
        }
        return new ErrorHolder(parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof TopH) {
            TopH topH = (TopH) holder;
            topH.bean = dataList.get(position);
            topH.title.setText(topH.bean.getTitle());
            topH.right_arrow.setVisibility(View.GONE);
            topH.setFlowLayoutTop(topH.bean.getCpwBeanArrayList());

        }
        if (holder instanceof ChartPieH) {
            ChartPieH pieH = (ChartPieH) holder;
            pieH.setData();

        }
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.index=position;
            iHolder.initDialogList();
            iHolder.left_title.setText(iHolder.bean.getLeft_title());
            iHolder.left_sub.setText(iHolder.bean.getTitle());
            iHolder.left_stu.setText(StringUtils.stringToGetTextJoint("学生%1$d",position));
            if (is_stu){
                iHolder.left_stu.setVisibility(View.VISIBLE);
            }else{
                iHolder.left_stu.setVisibility(View.GONE);
            }
            iHolder.left_content.setText(iHolder.bean.getContent());
            iHolder.right_state.setText(iHolder.bean.getRight());
            iHolder.right_score.setText(iHolder.bean.getRight_value());
            ViewTool.alterGradientDrawableStrokeColor(mContext,iHolder.bg,mContext.getResources().getColor(R.color.red));
            if (StringJudge.isEmpty(iHolder.bean.getListValue())){
//                iHolder.multi.setVisibility(View.GONE);
                List<String> list=new ArrayList<>();
                list.add(Base.user.getHeadPic());
                list.add(Base.user.getHeadPic());
                iHolder.multi.setList(list);
            }else{
                iHolder.multi.setVisibility(View.VISIBLE);
                iHolder.multi.setList(iHolder.bean.getListValue());
            }
            switch (iHolder.bean.getRight()){
                case "已通过":
                    iHolder.right_score.setVisibility(View.VISIBLE);
                    iHolder.line.setVisibility(View.VISIBLE);
                    iHolder.line.setBackgroundColor(ColorRgbUtil.getForestGreen());
                    iHolder.right_state.setTextColor(ColorRgbUtil.getForestGreen());
                    iHolder.right_score.setTextColor(ColorRgbUtil.getForestGreen());
                    ViewTool.alterGradientDrawableStrokeColor(mContext,iHolder.bg,ColorRgbUtil.getForestGreen());
                    break;
                case "已拒绝":
                    iHolder.right_state.setTextColor(ColorRgbUtil.getGray());
                    iHolder.right_score.setVisibility(View.GONE);
                    iHolder.line.setVisibility(View.GONE);
                    ViewTool.alterGradientDrawableStrokeColor(mContext,iHolder.bg,ColorRgbUtil.getGray());
                    break;
                case "待审核":
                    iHolder.right_state.setTextColor(ColorRgbUtil.getOrangeRed());
                    iHolder.right_score.setVisibility(View.GONE);
                    iHolder.line.setVisibility(View.GONE);
                    ViewTool.alterGradientDrawableStrokeColor(mContext,iHolder.bg,ColorRgbUtil.getOrangeRed());
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }




    class ChartPieH extends ReViewHolder {
        CardView cardView;
        PieChart mChart;
        ChartPieH(View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.public_chart_pie_card);
            mChart=  itemView.findViewById(R.id.public_chart_pie_chart);

            //----------重新设置card_View高度--------
            int width=ViewTool.getScreenWidth(mContext)-ViewTool.dip2px(mContext,20);
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
        List<KeyValue> pieData=new ArrayList<>();
        private void setData() {
            float all_score=0f;
            List<String> names_list=StringUtils.listToStringSplitCharacters("校级运动会,市级比赛,国家际比赛",",");
            for (String s:names_list){
                KeyValue bean=new KeyValue();
                bean.setName(s);
                bean.setValue(String.valueOf(MathTool.getRandomInt(5,15)));
                pieData.add(bean);
            }
            for (KeyValue key:pieData){
                if (!key.getValue().equals("0")){
                    all_score+=ConvertObjtect.getInstance().getFloat(key.getValue());
                }
            }

            ArrayList<Entry> yVals1 = new ArrayList<>();
            ArrayList<String> xVals = new ArrayList<>();

            if (StringJudge.isEmpty(pieData)){
                xVals.add("无数据");
                yVals1.add(new Entry(5f, 0));
            }else{
                for (int i = 0; i < pieData.size(); i++) {
                    yVals1.add(new Entry(ConvertObjtect.getInstance().getFloat(pieData.get(i).getValue()), i));
                    xVals.add(pieData.get(i).getName());
                }
            }

            mChart.setCenterText(StringUtils.getTextJoint("总分:%1$s",MathTool.stringToGetTwoToDecimals(all_score)));

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
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.getType().equalsIgnoreCase(TagFinal.TRUE)){


                        setCPWlListBeanData(bean);
                    }
                }
            });
        }

        CPWListBeanView cpwListBeanView;
        List<CPWBean> cpwBeans=new ArrayList<>();
        private void setCPWlListBeanData(KeyValue bean){
            switch (bean.getRight()){
                case "已通过":
                   return;
                case "已拒绝":
                    return;
                case "待审核":
                    if (StringJudge.isEmpty(cpwBeans)){
                        List<String> list=StringUtils.listToStringSplitCharacters("已通过,已拒绝",",");
                        for(String s:list){
                            CPWBean cpwBean =new CPWBean();
                            cpwBean.setName(s);
                            cpwBean.setId(s);
                            cpwBeans.add(cpwBean);
                        }
                    }
                    cpwListBeanView.setDatas(cpwBeans);
                    cpwListBeanView.showAtCenter();
                    break;
            }


        }
        private void initDialogList(){
            cpwListBeanView = new CPWListBeanView(mActivity);
            cpwListBeanView.setOnPopClickListenner(new CPWListBeanView.OnPopClickListenner() {
                @Override
                public void onClick(CPWBean cpwBean, String type) {
                    cpwListBeanView.dismiss();
                    bean.setRight(cpwBean.getName());
                    if(cpwBean.getName().equalsIgnoreCase("已通过")){
                        bean.setRight_value("20\t分");
                    }
                    mActivity.showProgressDialog("");
                    bg.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mActivity.dismissProgressDialog();
                            notifyItemChanged(index,bean);
                        }
                    },1000);

                }
            });
        }


    }



    class TopH extends ReViewHolder {
        AppCompatTextView title;
        AppCompatImageView right_arrow;
        FlowLayout flow;
        RelativeLayout layout;
        KeyValue bean;
        TopH(View itemView) {
            super(itemView);
            layout =  itemView.findViewById(R.id.public_type_flow_layout);
            title =  itemView.findViewById(R.id.public_type_flow_title);
            right_arrow =  itemView.findViewById(R.id.public_type_flow_arrow);
            flow =  itemView.findViewById(R.id.public_type_flow_flow);
        }


        private void setFlowLayoutTop(List<CPWBean> top_jz){

            LayoutInflater mInflater = LayoutInflater.from(mContext);
            if (flow.getChildCount()!=0){
                flow.removeAllViews();
            }
            for (CPWBean bean:top_jz){
                RelativeLayout view_layout = (RelativeLayout) mInflater.inflate(R.layout.public_detail_top_item,flow, false);
                TextView key=view_layout.findViewById(R.id.seal_detail_key);
                TextView value=view_layout.findViewById(R.id.seal_detail_value);
                RatingBar ratingBar=view_layout.findViewById(R.id.seal_detail_value_star);
                LinearLayout linearLayout=view_layout.findViewById(R.id.public_detail_txt_layout);
                MultiPictureView multi=view_layout.findViewById(R.id.public_detail_layout_multi);

                key.setTextColor(ColorRgbUtil.getGrayText());
                value.setTextColor(ColorRgbUtil.getGrayText());
                key.setText(bean.getName());
                switch (bean.getType()){
                    case "star":
                        linearLayout.setVisibility(View.VISIBLE);
                        multi.setVisibility(View.GONE);
                        if (bean.getValue().equals("")){
                            ratingBar.setRating(0f);
                        }else{
                            ratingBar.setRating(ConvertObjtect.getInstance().getFloat(bean.getValue()));
                        }
                        ratingBar.setVisibility(View.VISIBLE);
                        value.setVisibility(View.GONE);
                        break;
                    case "image":
                        linearLayout.setVisibility(View.GONE);
                        multi.setVisibility(View.VISIBLE);

//                        multi.clearItem();
//                        if (StringJudge.isEmpty(bean.getListValue())){
//                            multi.setVisibility(View.GONE);
//                        }else{
//                            multi.setVisibility(View.VISIBLE);
//                            multi.setList(bean.getListValue());
//                            multi.setItemClickCallback(new MultiPictureView.ItemClickCallback() {
//                                @Override
//                                public void onItemClicked(@NotNull View view, int index, @NotNull ArrayList<String> uris) {
//                                    Intent intent=new Intent(mContext, MultPicShowActivity.class);
//                                    Bundle b=new Bundle();
//                                    b.putStringArrayList(TagFinal.ALBUM_SINGE_URI,uris);
//                                    b.putInt(TagFinal.ALBUM_LIST_INDEX,index);
//                                    intent.putExtras(b);
//                                    mContext.startActivity(intent);
//                                }
//                            });
//                        }
                        break;
                    default:
                        linearLayout.setVisibility(View.VISIBLE);
                        multi.setVisibility(View.GONE);
                        value.setText(bean.getValue());
                        ratingBar.setVisibility(View.GONE);
                        value.setVisibility(View.VISIBLE);
                        break;
                }
                final String content=bean.getValue().trim();
                if (RegexUtils.isMobilePhoneNumber(content)){
                    SpannableString ss = new SpannableString(content);
                    ss.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View view) {

                            PermissionTools.tryTellPhone(mContext);
                        }
                    }, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF3030")), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    value.setText(ss);
                    value.setMovementMethod(LinkMovementMethod.getInstance());
                }
                flow.addView(view_layout);
            }
        }



    }



}
