package com.yfy.app.drawableBg;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yfy.app.welcome.Utils;
import com.yfy.base.R;
import com.yfy.final_tag.banner.ADInfo;
import com.yfy.final_tag.banner.CycleViewPager;
import com.yfy.final_tag.banner.ViewFactory;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.BaseRecyclerAdapter;
import com.yfy.final_tag.recycerview.ReViewHolder;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.viewtools.ViewTool;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;

/**
 * Date: 2016-05-03
 * Time: 13:47
 * FIXME
 */


public class CounselAdpater extends BaseRecyclerAdapter {
    private static final int VIEW_TYPE_LIVE_TAG = 1;//轮播
    private static final int VIEW_TYPE_NEWS = 2;//新闻
    private static final int VIEW_TYPE_LOAD_MORE = 6;//底部自动加载区

    private List<ADInfo> adInfos = new ArrayList<>();
    private List<News> newses = new ArrayList<>();
    private ViewPagerHolder viewPagerHolder;
    private Context mActivity;

    public CounselAdpater(Activity context) {
        super(context);
        this.mActivity=context;
        viewPagerHolder=new ViewPagerHolder(View.inflate(context, R.layout.drawable_banner, null));
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //广告
            return VIEW_TYPE_LIVE_TAG;
        }else if (position == getItemCount() - 1) {
            //最后一个元素显示加载
            return VIEW_TYPE_LOAD_MORE;
        } else {
            //新闻
            return VIEW_TYPE_NEWS;
        }
    }
    @Override
    public ReViewHolder initViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LIVE_TAG) {
            //轮播
            return viewPagerHolder;
        } else if (viewType == VIEW_TYPE_NEWS) {
            //新闻
            return new NewsHolder(inflater.inflate(R.layout.drawable_news_adpater, parent, false));
        } else {
             return new FootViewHolder(inflater.inflate(R.layout.recyclerview_refresh_footer, parent, false));
        }
    }

    @Override
    public void bindHolder(ReViewHolder holder, int position) {
        if (holder instanceof NewsHolder) {
            bindNewsData((NewsHolder)holder,position);
        }
        if (holder instanceof FootViewHolder) {
            FootViewHolder footH = (FootViewHolder) holder;
            switch (loadState) {
                case TagFinal.LOADING: // 正在加载
                    footH.pbLoading.setVisibility(View.VISIBLE);
                    footH.tvLoading.setVisibility(View.VISIBLE);
                    footH.llEnd.setVisibility(View.GONE);
                    break;
                case TagFinal.LOADING_COMPLETE: // 加载完成
                    footH.pbLoading.setVisibility(View.INVISIBLE);
                    footH.tvLoading.setVisibility(View.INVISIBLE);
                    footH.llEnd.setVisibility(View.GONE);
                    break;
                case TagFinal.LOADING_END: // 加载到底
                    footH.pbLoading.setVisibility(View.GONE);
                    footH.tvLoading.setVisibility(View.GONE);
                    footH.llEnd.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return newses.size() + 2;//新闻数+轮播+提醒
    }


    /**
     * 轮播图
     */
    class ViewPagerHolder extends ReViewHolder {
        CycleViewPager pager_ads;
        ViewPagerHolder(View itemView) {
            super(itemView);
            FragmentActivity activity = (FragmentActivity) mContext;
            pager_ads = (CycleViewPager) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_content);
//            pager_ads = (CycleViewPager) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_content);
            // 设置循环，在调用setData方法前调用
            pager_ads.setCycle(true);
            pager_ads.setWheel(true);
            pager_ads.setTime(5000);
            //设置圆点指示图标组居中显示，默认靠右
            pager_ads.setIndicatorCenter();
        }
    }

    /**
     * 新闻列表
     */
    class NewsHolder extends ReViewHolder {
        TextView title;
        TextView content;
        ImageView img;
        View more;
        News news;
        public NewsHolder(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.news_title);
            content =  itemView.findViewById(R.id.news_content);
            img =  itemView.findViewById(R.id.news_img);
            more = itemView.findViewById(R.id.more);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    ViewTool.showToastShort(mContext,"more");
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
    /**
     * 刷新“广告”的显示数据
     *
     * @param adInfos 刷新的数据
     */
    public void setADInfos(List<ADInfo> adInfos) {
        if (Utils.isEmpty(adInfos)) {
            return;
        }
        this.adInfos.addAll(adInfos);
        List<ImageView> views = generateImageViews(adInfos);
        viewPagerHolder.pager_ads.setData(views, adInfos, imageCycleViewListener);
    }

    /**
     * 初始化广告的操作
     *
     * @param

     */
    public List<ImageView> generateImageViews(List<ADInfo> infos) {
        List<ImageView> views = new ArrayList<>();
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(mContext, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(mContext, infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(mContext, infos.get(0).getUrl()));
        return views;
    }

    /**
     * 首页轮播图片点击事件
     */
    CycleViewPager.ImageCycleViewListener imageCycleViewListener = new CycleViewPager.ImageCycleViewListener() {
        @Override
        public void onImageClick(ADInfo info, int postion, View imageView) {
            Logger.e(
                    info.getUrl());
        }
    };

    private void bindNewsData(NewsHolder holder, int position) {
        News news =newses.get(position-1);
        assert news != null;
        if(news.getContent()!=null){
            holder.content.setText(Html.fromHtml(news.getContent()));
        }
        holder.title.setText(news.getTitle());
        Glide.with(mContext).load(news.getNews_img()).into(holder.img);
        holder.news = news;
    }

    public void addNewses(List<News> newses) {
        if (Utils.isNotEmpty(newses)) {
            this.newses.addAll(newses);
        }
    }
    public void clearNewses(){
        newses.clear();
    }

    public int livesCount() {
        return newses.size();
    }
}
