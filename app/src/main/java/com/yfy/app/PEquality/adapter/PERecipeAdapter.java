package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.base.adapter.BaseRecyclerAdapter;
import com.yfy.base.adapter.ReViewHolder;
import com.yfy.final_tag.data.TagFinal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PERecipeAdapter extends BaseRecyclerAdapter {

    private List<KeyValue> dataList;
    private Activity mContext;
    private int loadState = 2;
    public void setDataList(List<KeyValue> dataList) {

        this.dataList = dataList;
//        getRandomHeights(dataList);
    }

    public PERecipeAdapter(Activity mContext) {
        super(mContext);
        this.mContext=mContext;
        this.dataList = new ArrayList<>();

    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TagFinal.TYPE_FOOTER;
        } else {
            return dataList.get(position).getView_type();
        }
    }

    @Override
    public ReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TagFinal.TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_e_recipe_item_layout, parent, false);
            return new IHolder(view);

        }
        if (viewType == TagFinal.TYPE_TXT_LEFT_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_type_txt_left_title, parent, false);
            return new TxtLeftTitleH(view);

        }

        if (viewType == TagFinal.TYPE_FOOTER) {
            return new FootViewHolder(inflater.inflate(R.layout.recyclerview_refresh_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ReViewHolder holder, int position) {
        if (holder instanceof IHolder) {
            IHolder iHolder = (IHolder) holder;
            iHolder.bean=dataList.get(position);
            iHolder.title.setText(iHolder.bean.getKey());
            iHolder.title_sub.setText(iHolder.bean.getValue());
        }
        if (holder instanceof TxtLeftTitleH) {
            TxtLeftTitleH txtLeftTitleH = (TxtLeftTitleH) holder;
            txtLeftTitleH.bean=dataList.get(position);
            txtLeftTitleH.title.setText(txtLeftTitleH.bean.getValue());
        }
        if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case TagFinal.LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case TagFinal.LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case TagFinal.LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    private class TxtLeftTitleH extends ReViewHolder {


        AppCompatTextView title;
        KeyValue bean;
        TxtLeftTitleH(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.public_type_txt_left_title);
        }
    }

    private class IHolder extends ReViewHolder {

        RelativeLayout layout;

        AppCompatTextView title;
        AppCompatTextView title_sub;
        KeyValue bean;
        IHolder(View itemView) {
            super(itemView);
            layout=  itemView.findViewById(R.id.p_e_recipe_layout);
            title =  itemView.findViewById(R.id.p_e_recipe_title);
            title_sub=  itemView.findViewById(R.id.p_e_recipe_title_sub);
        }
    }

    private class FootViewHolder extends ReViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;
        RelativeLayout allEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading =  itemView.findViewById(R.id.pb_loading);
            tvLoading =  itemView.findViewById(R.id.tv_loading);
            llEnd =  itemView.findViewById(R.id.ll_end);
            allEnd =  itemView.findViewById(R.id.recycler_bottom);

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
