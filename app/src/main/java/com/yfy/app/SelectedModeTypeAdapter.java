package com.yfy.app;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfy.AAChartCore.ChartsDemo.MainContent.AAChartMainActivity;
import com.yfy.app.PEquality.PEQualityMainTestActivity;
import com.yfy.app.PEquality.tea.PETeaMainActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.chart.BarChartActivity;
import com.yfy.app.chart.EChartSActivity;
import com.yfy.app.drawableBg.DrawableBgActivity;
import com.yfy.app.drawableBg.MaterialMainActivity;
import com.yfy.app.drawableBg.SingleActivity;
import com.yfy.app.drawableBg.TableLayoutActivity;
import com.yfy.app.drawableBg.widget.ButtomActivity;
import com.yfy.app.drawableBg.widget.EditTextActivity;
import com.yfy.app.drawableBg.widget.ImageViewActivity;
import com.yfy.app.drawableBg.widget.ListViewActivity;
import com.yfy.app.drawableBg.widget.OrthogonActivity;
import com.yfy.app.duty_evaluate.DutyEvaluateStuMainActivity;
import com.yfy.app.httppost.HttpPostMainActivity;
import com.yfy.app.httppost.retrofitclient.RetrofitMainActivity;
import com.yfy.app.spannable_string.SpannableStringMainActivity;
import com.yfy.app.voice.VoiceMainActivity;
import com.yfy.base.Base;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.recycerview.adapter.StartIntentInterface;
import com.yfy.greendao.view.AddStuActivity;
import com.yfy.greendao.view.TypeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class SelectedModeTypeAdapter extends BaseRecyclerAdapter {



    private List<KeyValue> dataList;

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }


    public SelectedModeTypeAdapter(Activity context) {
        super(context);
        this.dataList = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        return TagFinal.TYPE_ITEM;
    }

    @Override
    public ReViewHolder initViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TagFinal.TYPE_ITEM) {
            return new SelectedClassH(inflater.inflate(R.layout.selected_singe_item_layout, parent, false));
        }
        return new ErrorHolder(parent);
    }

    @Override
    public void bindHolder(ReViewHolder holder, int position) {

        if (holder instanceof SelectedClassH) {
            SelectedClassH selectedTermH = (SelectedClassH) holder;
            selectedTermH.bean=dataList.get(position);
            selectedTermH.name.setText(selectedTermH.bean.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class SelectedClassH extends ReViewHolder {
        TextView name;
        TextView type;
        KeyValue bean;
        SelectedClassH(View itemView) {
            super(itemView);
            name=  itemView.findViewById(R.id.selected_item_name);
            type=  itemView.findViewById(R.id.selected_item_type);
            name.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View v) {
                    Intent intent=new Intent();
                    switch (bean.getTitle()){
                        //main
                        case "TypeActivity":
                            intent.setClass(mContext, TypeActivity.class);
                            break;
                        case "视频":
//                            intent.setClass(mContext, BarChartActivity.class);

                            return;
//                            break;
                        case "drawable":
                            intent.setClass(mContext, DrawableBgActivity.class);
                            break;
                        case "SpannableStringMainActivity":
                            intent.setClass(mContext, SpannableStringMainActivity.class);
                            break;
                        case "BarChartActivity":
                            intent.setClass(mContext, BarChartActivity.class);
                            break;
                        case "EChartSActivity":
                            intent.setClass(mContext, EChartSActivity.class);
                            break;
                        case "Voice":
                            intent.setClass(mContext, VoiceMainActivity.class);
                            break;
                        case "Version":
                            intent.setClass(mContext, VersionDetailActivity.class);
                            break;
                        case "HttpPostMainActivity":
                            intent.setClass(mContext, HttpPostMainActivity.class);
                            break;
                        case "RetrofitMainActivity":
                            intent.setClass(mContext, RetrofitMainActivity.class);
                            break;
                        case "体育素质(tea)":
                            intent.putExtra(Base.title,"体育素质评价");
                            intent.setClass(mContext, PETeaMainActivity.class);
                            break;
                        case "体育素质(stu)":
                            intent.setClass(mContext, PEQualityMainTestActivity.class);
                            break;
                        case "德育评价(tea)":
                            intent.putExtra(Base.mode_type,"duty_evaluate");
                            intent.setClass(mContext, SelectedClassActivity.class);
                            break;
                        case "德育评价(stu)":
                            intent.setClass(mContext, DutyEvaluateStuMainActivity.class);
                            break;
                        case "AAChartMain":
                            intent.setClass(mContext, AAChartMainActivity.class);
                            break;
                            //----DrawableBgActivity----------
                        case "OrthogonActivity":
                            intent.setClass(mContext, OrthogonActivity.class);
                            break;
                        case "EditTextActivity":
                            intent.setClass(mContext, EditTextActivity.class);
                            break;
                        case "ButtomActivity":
                            intent.setClass(mContext, ButtomActivity.class);
                            break;
                        case "ImageViewActivity":
                            intent.setClass(mContext, ImageViewActivity.class);
                            break;
                        case "ListViewActivity":
                            intent.setClass(mContext, ListViewActivity.class);
                            break;
                        case "TableLayoutActivity":
                            intent.setClass(mContext, TableLayoutActivity.class);
                            break;
                        case "MaterialMainActivity":
                            intent.setClass(mContext, MaterialMainActivity.class);
                            break;
                        case "SingleActivity":
                            intent.setClass(mContext, SingleActivity.class);
                            break;
                            //-----------------typeActivity
                        case "stu":
                            intent.putExtra(Base.type,bean.getTitle());
                            intent.putExtra(Base.title,"添加学生");
                            intent.setClass(mContext, AddStuActivity.class);

                            break;
                        case "class":
                            intent.putExtra(Base.type,bean.getTitle());
                            intent.putExtra(Base.title,"添加班级");
                            intent.setClass(mContext, AddStuActivity.class);

                            break;
                        case "term":
                            intent.putExtra(Base.type,bean.getTitle());
                            intent.putExtra(Base.title,"添加学期");
                            intent.setClass(mContext, AddStuActivity.class);

                            break;
                        default:
                            break;
                    }

                    if (intentStart!=null){
                        intentStart.startIntentAdapter(intent);
                    }
                }
            });
        }
    }


    public StartIntentInterface intentStart;

    public void setIntentStart(StartIntentInterface intentStart) {
        this.intentStart = intentStart;
    }


}
