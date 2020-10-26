package com.yfy.app.PEquality.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yfy.app.PEquality.tea.PEQualityTeaSuggestActivity;
import com.yfy.app.bean.KeyValue;
import com.yfy.base.R;
import com.yfy.final_tag.glide.GlideTools;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.video_jcv.JCVideoPlayer;
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
//        getRandomHeights(dataList);
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_e_recipe_item_layout, parent, false);
            return new IHolder(view);

        }
        if (position == TagFinal.TYPE_TXT_LEFT_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_type_video_jcv, parent, false);
            return new VideoJvcH(view);

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
//            videoJvcH.title.setText(videoJvcH.bean.getValue());
            videoJvcH.jcVideoPlayer.setUp(videoJvcH.bean.getValue(), JCVideoPlayer.SCREEN_LAYOUT_LIST, videoJvcH.bean.getName()

            );
            GlideTools.loadImage(mContext, videoJvcH.bean.getKey(), videoJvcH.jcVideoPlayer.thumbImageView);

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
        JCVideoPlayerStandard jcVideoPlayer;
        KeyValue bean;
        VideoJvcH(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.public_type_video_jvc_name);
            jcVideoPlayer =  itemView.findViewById(R.id.public_type_video_jvc_play);
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
