package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.design.internal.FlowLayout;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yfy.app.PEquality.tea.PEQualityTeaSuggestActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.video.PlayDirectlyActivity;
import com.yfy.base.R;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.video_jcv.JCVideoPlayerStandard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yfyandr on 2017/12/27.
 */

public class PERecipeAdapter extends BaseRecyclerAdapter {


    private String type;

    public void setType(String type) {
        this.type = type;
    }

    private List<KeyValue> dataList;
    public void setDataList(List<KeyValue> dataList) {

        this.dataList = dataList;
    }

    public PERecipeAdapter(Activity mContext) {
        super(mContext);
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
    public ReViewHolder initViewHolder(ViewGroup parent, int position) {
        //进行判断显示类型，来创建返回不同的View
        if (position == TagFinal.TYPE_ITEM) {
            return new IHolder(inflater.inflate(R.layout.p_e_recipe_item_layout, parent, false));
        }
        if (position == TagFinal.TYPE_TXT_LEFT_TITLE) {
            return new VideoJvcH(inflater.inflate(R.layout.p_e_recipe_video, parent, false));
        }
        if (position == TagFinal.TYPE_FOOTER) {
            return new FootViewHolder(inflater.inflate(R.layout.recyclerview_refresh_footer, parent, false));
        }
        return new ErrorHolder(parent);
    }

    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof IHolder) {
            IHolder iHolder = (IHolder) holder;
            iHolder.bean=dataList.get(position);
            iHolder.title.setText(iHolder.bean.getKey());
            iHolder.title_sub.setText(iHolder.bean.getValue());
        }
        if (holder instanceof VideoJvcH) {
            VideoJvcH videoJvcH = (VideoJvcH) holder;
            videoJvcH.bean=dataList.get(position);
            videoJvcH.title.setText(videoJvcH.bean.getName());
            videoJvcH.setFlowLayoutTop(videoJvcH.bean.getCpwBeanArrayList(),position%2);

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

    private class VideoJvcH extends ReViewHolder {


        AppCompatTextView title;
        FlowLayout flow;
        KeyValue bean;
        VideoJvcH(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.p_e_recipe_title);
            flow =  itemView.findViewById(R.id.public_type_flow_flow);
        }


        private void setFlowLayoutTop(List<CPWBean> top_jz,int position){

            LayoutInflater mInflater = LayoutInflater.from(mContext);
            if (flow.getChildCount()!=0){
                flow.removeAllViews();
            }
            for (int i=0;i<=position;i++){
                RelativeLayout flow_view = (RelativeLayout) mInflater.inflate(R.layout.p_e_recipe_video_item,flow, false);
                ImageView bg=flow_view.findViewById(R.id.video_play_icon);
                TextView sub_title=flow_view.findViewById(R.id.video_play_name);

                int width=ViewTool.getScreenWidth(mContext)-ViewTool.pxPointDp(mContext,30);
                Logger.e(String.valueOf(width));
                RelativeLayout.LayoutParams p= (RelativeLayout.LayoutParams) bg.getLayoutParams();
                p.width=width/2;
                flow_view.setLayoutParams(p);
                GlideTools.chanMult(mContext,R.mipmap.home_header_2,bg);

                sub_title.setText(bean.getName());
                bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url="https://www.w3school.com.cn/example/html5/mov_bbb.mp4";
                        String title=bean.getName();

                        JCVideoPlayerStandard.startFullscreen(mContext, JCVideoPlayerStandard.class, url, title);


//                        Intent intent=new Intent(mContext,PlayDirectlyActivity.class);
//                        intent.putExtra(Base.value,"http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4");
//                        intent.putExtra(Base.title,"video");
//                        mContext.startActivity(intent);
                    }
                });
                flow.addView(flow_view);
            }
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
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (type.equalsIgnoreCase(TagFinal.TRUE)){
                        Intent intent=new Intent(mContext,PEQualityTeaSuggestActivity.class);
                        intent.putExtra(Base.title,"运动处方");
                        intent.putExtra(Base.data,bean);
                        intent.putExtra(Base.type,"运动处方");
                        mContext.startActivity(intent);
                    }

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
