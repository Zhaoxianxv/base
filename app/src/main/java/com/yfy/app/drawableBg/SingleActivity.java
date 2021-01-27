package com.yfy.app.drawableBg;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;


import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.banner.ADInfo;

import java.util.ArrayList;
import java.util.List;


public class SingleActivity extends BaseActivity {



    private CounselAdpater adpater;
    @BindView(R.id.show_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.show_recycierview_news)
    RecyclerView recyclerView;


    private List<ADInfo> banner=new ArrayList();
    private List<News> news=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable_show_single_new);
        getData();
        initView();
    }

    private void initView() {
        adpater=new CounselAdpater(mActivity);
        recyclerView.setAdapter(adpater);
        adpater.setADInfos(banner);
        adpater.addNewses(news);


    }


    //模拟数据
    private void getData(){
        for (int i=0;i<4;i++){
            ADInfo adInfo=new ADInfo();
            adInfo.setId(0);
            adInfo.setUrl("http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
            banner.add(adInfo);
        }
        for (int i=0;i<10;i++){
            News msg=new News();
            msg.setTitle("msg  :  "+i);
            msg.setAdd_time("time");
            msg.setContent("content  :  "+i);
            msg.setNews_img("");
            news.add(msg);

        }
    }
}
