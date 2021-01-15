package com.yfy.app.duty_evaluate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import com.yfy.app.bean.KeyValue;
import com.yfy.app.duty_evaluate.DutyEvaluateStuDetailActivity;
import com.yfy.base.R;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/1/15
 */
public class DutyEvaluateRecodeAdapter extends BaseRecyclerAdapter {


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
            iHolder.title.setText(iHolder.bean.getName());


        }

    }

    @Override
    public int getItemCount() {
        return dataList.size() ;
    }


    private class ItemHolder extends ReViewHolder {

        AppCompatTextView title;
        AppCompatTextView value;
        int index;
        KeyValue bean;
        public ItemHolder(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.duty_evaluate_tea_do_tab_item_title);
            value =  itemView.findViewById(R.id.duty_evaluate_tea_do_tab_item_value);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext,DutyEvaluateStuDetailActivity.class);
                    intent.putExtra(Base.stu_bean,bean);
                    mContext.startActivity(intent);
                }
            });

        }
    }


}
