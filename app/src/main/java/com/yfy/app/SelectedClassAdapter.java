package com.yfy.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.duty_evaluate.DutyEvaluateTeaDoActivity;
import com.yfy.base.R;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class SelectedClassAdapter extends BaseRecyclerAdapter {


    private String mode_type;

    private List<KeyValue> dataList;

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    public void setMode_type(String mode_type) {
        this.mode_type = mode_type;
    }

    public SelectedClassAdapter(Activity mContext){
        super(mContext);
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
            return new SelectedClassH(inflater.inflate(R.layout.public_txt_check_layout, parent, false));
        }
        return new ErrorHolder(parent);
    }

    @Override
    public void bindHolder(ReViewHolder holder, int position) {

        if (holder instanceof SelectedClassH) {
            SelectedClassH selectedClassH = (SelectedClassH) holder;
            selectedClassH.bean=dataList.get(position);
            selectedClassH.lift_title.setText(selectedClassH.bean.getTitle());
            selectedClassH.right_value.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class SelectedClassH extends ReViewHolder {
        AppCompatTextView lift_title;
        AppCompatImageView right_value;
        RelativeLayout layout;
        KeyValue bean;
        SelectedClassH(View itemView) {
            super(itemView);
            lift_title=  itemView.findViewById(R.id.public_txt_check_layout_left_title);
            right_value=  itemView.findViewById(R.id.public_txt_check_right_value);
            layout=  itemView.findViewById(R.id.public_txt_check_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (mode_type){
                        case "duty_evaluate":
                            Intent intent=new Intent(mContext,DutyEvaluateTeaDoActivity.class);
                            intent.putExtra(Base.class_bean,bean);
                            mContext.startActivity(intent);
                            break;
                    }
                }
            });

        }
    }







}
