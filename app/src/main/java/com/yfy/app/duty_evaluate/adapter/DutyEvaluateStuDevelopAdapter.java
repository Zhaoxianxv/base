package com.yfy.app.duty_evaluate.adapter;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by zxx.
 * Date: 2021/1/20
 */
public class DutyEvaluateStuDevelopAdapter extends BaseRecyclerAdapter {


    private List<KeyValue> dataList;

    public DutyEvaluateStuDevelopAdapter(Activity mContext) {
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
            return new ItemHolder(inflater.inflate(R.layout.duty_evaluate_stu_develop_item, parent, false));
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
            iHolder.title.setText(iHolder.bean.getTitle());

            if (iHolder.bean.getTitle().equalsIgnoreCase("1月·未完成")){
                ViewTool.alterProgressSecondaryBackgroundColor(
                        iHolder.progressBar,
                        ColorRgbUtil.getResourceColor(mContext,R.color.user_type_stu),
                        ColorRgbUtil.getResourceColor(mContext,R.color.light_gray),
                        ColorRgbUtil.getResourceColor(mContext,R.color.user_type_tea));
            }else if (iHolder.bean.getTitle().equalsIgnoreCase("12月·未完成")){
                ViewTool.alterProgressSecondaryBackgroundColor(
                        iHolder.progressBar,
                        ColorRgbUtil.getResourceColor(mContext,R.color.light_gray),
                        ColorRgbUtil.getResourceColor(mContext,R.color.user_type_family),
                        ColorRgbUtil.getResourceColor(mContext,R.color.user_type_tea));
            }else{
                ViewTool.alterProgressSecondaryBackgroundColor(
                        iHolder.progressBar,
                        ColorRgbUtil.getResourceColor(mContext,R.color.user_type_stu),
                        ColorRgbUtil.getResourceColor(mContext,R.color.user_type_stu),
                        ColorRgbUtil.getResourceColor(mContext,R.color.user_type_stu));
            }

        }

    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }


    private class ItemHolder extends ReViewHolder {

        AppCompatTextView title;
        ProgressBar progressBar;
        RelativeLayout layout;
        int index;
        KeyValue bean;
        public ItemHolder(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.duty_evaluate_normal_item_title);
            progressBar =  itemView.findViewById(R.id.duty_evaluate_normal_item_progress);
            layout =  itemView.findViewById(R.id.duty_evaluate_normal_item_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClieckAdapterLayout!=null){
                        onClieckAdapterLayout.layoutOnClick(bean);
                    }
                }
            });

        }
    }




    public OnClickAdapterLayout onClieckAdapterLayout;

    public void setOnClieckAdapterLayout(OnClickAdapterLayout onClieckAdapterLayout) {
        this.onClieckAdapterLayout = onClieckAdapterLayout;
    }

    public interface OnClickAdapterLayout {
        void layoutOnClick(KeyValue keyValue);
    }

}
