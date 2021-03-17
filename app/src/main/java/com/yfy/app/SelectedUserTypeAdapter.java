package com.yfy.app;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class SelectedUserTypeAdapter extends BaseRecyclerAdapter {



    private List<KeyValue> dataList;

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }



    public SelectedUserTypeAdapter(Activity mContext){
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

            return new SelectUserTypeH(inflater.inflate(R.layout.public_txt_check_layout, parent, false));

        }
        return new ErrorHolder(parent);
    }

    @Override
    public void bindHolder(ReViewHolder holder, int position) {

        if (holder instanceof SelectUserTypeH) {
            SelectUserTypeH userTpeH = (SelectUserTypeH) holder;
            userTpeH.bean=dataList.get(position);
            userTpeH.lift_title.setText(userTpeH.bean.getTitle());
            if (userTpeH.bean.isIs_selected()){
                userTpeH.right_value.setImageResource(R.drawable.ic_radio_button_checked);
            }else{
                userTpeH.right_value.setImageResource(R.drawable.ic_radio_button_unchecked);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class SelectUserTypeH extends ReViewHolder {
        AppCompatTextView lift_title;
        AppCompatImageView right_value;
        RelativeLayout layout;
        KeyValue bean;
        SelectUserTypeH(View itemView) {
            super(itemView);
            lift_title=  itemView.findViewById(R.id.public_txt_check_layout_left_title);
            right_value=  itemView.findViewById(R.id.public_txt_check_right_value);
            layout=  itemView.findViewById(R.id.public_txt_check_layout);
            layout.setOnClickListener(new NoFastClickListener() {
                @Override
                public void fastClick(View v) {
                    //单/多选

                    initData(bean);
                    if (modeItem!=null){
                        modeItem.modeItem(bean);
                    }
                }
            });

        }
    }



    private void initData(KeyValue bean){
        for (KeyValue keyValue:dataList){
            keyValue.setIs_selected(false);
        }
        bean.setIs_selected(true);
        notifyDataSetChanged();
    }




    public interface UserTypeItem {
        void modeItem(KeyValue bean);
    }

    private UserTypeItem modeItem;

    public void setModeItem(UserTypeItem modeItem) {
        this.modeItem = modeItem;
    }




}
