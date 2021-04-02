package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.adapter.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.adapter.ReViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * Created by yfy on 2017/12/27.
 */

public class PEQualityAttitudeDetailAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;
    public int loadState = 2;

    public PEQualityAttitudeDetailAdapter(Activity mContext) {
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
    public ReViewHolder initViewHolder( ViewGroup parent, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TagFinal.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_type_txt, parent, false);
            return new ItemHolder(view);
        }

        return new ErrorHolder(parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.key.setText(iHolder.bean.getKey());
            iHolder.value.setText(iHolder.bean.getValue());
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ItemHolder extends ReViewHolder {
        public TextView key;
        public TextView value;
        KeyValue bean;

        public ItemHolder(View itemView) {
            super(itemView);
            key = itemView.findViewById(R.id.public_type_txt_key);
            value = itemView.findViewById(R.id.public_type_txt_value);
        }
    }


    /**
     * 设置上拉加载状态
     *
     * @param loadState 1.正在加载 2.加载完成 3.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
}
