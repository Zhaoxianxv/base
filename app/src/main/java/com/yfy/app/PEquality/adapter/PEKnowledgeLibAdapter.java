package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
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

public class PEKnowledgeLibAdapter extends BaseRecyclerAdapter {

    public List<KeyValue> dataList;

    public void setDataList(List<KeyValue> dataList) {
        this.dataList = dataList;
    }

    public PEKnowledgeLibAdapter(Activity mContext) {
        super(mContext);
        dataList =new ArrayList<>();

    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getView_type();
    }

    @Override
    public ReViewHolder initViewHolder(@NonNull ViewGroup viewGroup, int position) {
        if (position ==TagFinal.TYPE_ITEM) {
            return new LibItemH(inflater.inflate(R.layout.knowledge_library_pager_item_layout, viewGroup,false));
        }
        if (position == TagFinal.TYPE_FOOTER) {
            return new FootViewHolder(inflater.inflate(R.layout.recyclerview_refresh_footer, viewGroup,false));
        }
        return new ErrorHolder(viewGroup);
    }

    @Override
    public void bindHolder(@NonNull ReViewHolder holder, int position) {
        if (holder instanceof LibItemH){
            LibItemH libH = (LibItemH) holder;
            libH.bean= dataList.get(position);
            libH.title.setText(libH.bean.getName());
            libH.content.setText(libH.bean.getValue());
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
        return dataList.size();
    }

    public class LibItemH extends ReViewHolder {
        TextView title;
        TextView content;
        RelativeLayout layout;
        KeyValue bean;
        LibItemH(View itemView) {
            super(itemView);
            layout =  itemView.findViewById(R.id.lib_layout);
            title =  itemView.findViewById(R.id.lib_title);
            content=  itemView.findViewById(R.id.lib_content);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //单/多选

                }
            });

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


}
