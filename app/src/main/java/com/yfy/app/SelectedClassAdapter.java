package com.yfy.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfy.app.bean.KeyValue;
import com.yfy.app.bean.TermBean;
import com.yfy.base.R;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yfy1 on 2016/10/17.
 */
public class SelectedClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<KeyValue> dataList;
    private Activity mContext;

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
//        getRandomHeights(dataList);
    }
    // 当前加载状态，默认为加载完成
    private int loadState = 2;


    public SelectedClassAdapter(Activity mContext){
        this.mContext=mContext;
        this.dataList = new ArrayList<>();
    }


    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        return TagFinal.TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TagFinal.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_singe_item_layout, parent, false);
            return new SelectedTermH(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SelectedTermH) {
            SelectedTermH selectedTermH = (SelectedTermH) holder;
            selectedTermH.bean=dataList.get(position);
            selectedTermH.name.setText(selectedTermH.bean.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class SelectedTermH extends RecyclerView.ViewHolder {
        TextView name;
        TextView type;
        KeyValue bean;
        SelectedTermH(View itemView) {
            super(itemView);
            name=  itemView.findViewById(R.id.selected_item_name);
            type=  itemView.findViewById(R.id.selected_item_type);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //单/多选
                    Intent intent=new Intent(mContext,SelectStuActivity.class);
                    intent.putExtra(Base.title,bean.getTitle());
                    mContext.startActivity(intent);
                }
            });

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