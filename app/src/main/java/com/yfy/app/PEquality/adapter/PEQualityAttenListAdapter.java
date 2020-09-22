package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yfy.app.album.MultPicShowActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.view.multi.MultiPictureView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PEQualityAttenListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mContext;
    private List<KeyValue> dataList;
    private int loadState = 2;

    public PEQualityAttenListAdapter(Activity mContext) {
        this.mContext = mContext;
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TagFinal.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_e_atten_item_layout, parent, false);
            return new ItemHolder(view);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.left_title.setText(iHolder.bean.getTitle());
            iHolder.left_sub.setText(iHolder.bean.getLeft_title());
            iHolder.right_state.setText(iHolder.bean.getRight());
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
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private class ItemHolder extends RecyclerView.ViewHolder {
        TextView left_title;
        TextView left_sub;
        TextView right_state;
        MultiPictureView multi;
        KeyValue bean;
        ItemHolder(View itemView) {
            super(itemView);
            left_title =  itemView.findViewById(R.id.p_e_atten_left_title);
            left_sub =  itemView.findViewById(R.id.p_e_atten_left_sub);
            right_state =  itemView.findViewById(R.id.p_e_atten_right_state);
            multi =  itemView.findViewById(R.id.p_e_atten_item_multi);
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
