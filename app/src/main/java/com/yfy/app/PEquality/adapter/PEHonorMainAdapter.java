package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.PEquality.tea.PEQualityTeaSuggestActivity;
import com.yfy.app.album.MultPicShowActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.data.ColorRgbUtil;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.multi.MultiPictureView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PEHonorMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mContext;
    private List<KeyValue> dataList;
    private int loadState = 2;

    public PEHonorMainAdapter(Activity mContext) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_e_honor_main_item_layout, parent, false);
            return new ItemHolder(view);
        }

        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder iHolder = (ItemHolder) holder;
            iHolder.bean = dataList.get(position);
            iHolder.left_title.setText(iHolder.bean.getLeft_title());
            iHolder.left_sub.setText(iHolder.bean.getTitle());
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
                case "已提交":
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


    private class ItemHolder extends RecyclerView.ViewHolder {
        TextView left_title;
        TextView left_sub;
        TextView right_state;
        TextView right_score;
        View line;
        MultiPictureView multi;
        LinearLayout bg;
        RelativeLayout layout;
        KeyValue bean;
        ItemHolder(View itemView) {
            super(itemView);
            layout =  itemView.findViewById(R.id.p_e_honor_item_layout);
            bg =  itemView.findViewById(R.id.p_e_honor_radio_group);
            line =  itemView.findViewById(R.id.p_e_honor_right_line);
            left_title =  itemView.findViewById(R.id.p_e_honor_left_title);
            left_sub =  itemView.findViewById(R.id.p_e_honor_left_sub);
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
                        ViewTool.showToastShort(mContext,"荣誉审核");
                        Intent intent=new Intent(mContext,PEQualityTeaSuggestActivity.class);
                        intent.putExtra(Base.title,bean.getTitle());
                        intent.putExtra(Base.type,"admin");
                        mContext.startActivity(intent);
                    }
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
