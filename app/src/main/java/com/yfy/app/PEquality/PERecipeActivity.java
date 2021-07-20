package com.yfy.app.PEquality;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.yfy.app.PEquality.adapter.PERecipeAdapter;
import com.yfy.app.PEquality.tea.PEQualityTeaSuggestActivity;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.listener.NoFastClickListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.EndlessRecyclerOnScrollListener;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.video_jcv.JCVideoPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Response;

public class PERecipeActivity extends BaseActivity {
    private static final String TAG = PERecipeActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        getData();
        initSQToolbar();
        initRecycler();
        setAdapterData();
    }


    private String title,type;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
        type=getIntent().getStringExtra(Base.type);
    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);
        if (type.equalsIgnoreCase(TagFinal.FALSE))return;
        toolbar.addMenuText(TagFinal.ONE_INT,"添加");
        toolbar.setOnMenuClickListener(new NoFastClickListener() {
            @Override
            public void fastClick(View view) {
                Intent intent=new Intent(mActivity,PEQualityTeaSuggestActivity.class);
                intent.putExtra(Base.title,title);
                intent.putExtra(Base.type,title);
                startActivity(intent);
            }
        });

    }


    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    public PERecipeAdapter adapter;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);
        swipeRefreshLayout =  findViewById(R.id.public_swip);
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                closeSwipeRefresh();
            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadState(TagFinal.LOADING);
                adapter.setLoadState(TagFinal.LOADING_END);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        xlist.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
//        recyclerView.addItemDecoration(new RecycleViewDivider(
//                mActivity,
//                LinearLayoutManager.HORIZONTAL,
//                1,
//                getResources().getColor(R.color.Gray)));
        adapter=new PERecipeAdapter(mActivity);
        recyclerView.setAdapter(adapter);
        adapter.setType(type);

    }






    public void closeSwipeRefresh(){
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 200);
        }
    }

    public List<KeyValue> keyValue_adapter=new ArrayList<>();

    private void setAdapterData(){
        keyValue_adapter.clear();



        keyValue_adapter.add(new KeyValue("给我的处方","碳水化合物摄入不足 ,脂肪和蛋白质摄入过多 ,部分维生素摄入不足 ,三餐热能分配不合理 ,存在钙,铁,锌摄入不足及运动中脱水 ,补液不科学.同时分析了运动员膳食不科学的原因 ,并提出相应的改进建议","",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603269209118&di=b3b911cc2c6b8e07f7ff9b163a58a641&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D2404852592%2C1529663443%26fm%3D214%26gp%3D0.jpg",
                "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4",
                "强体能锻炼",
                TagFinal.TYPE_TXT_LEFT_TITLE));
        keyValue_adapter.add(new KeyValue(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603269209118&di=b3b911cc2c6b8e07f7ff9b163a58a641&imgtype=0&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D2404852592%2C1529663443%26fm%3D214%26gp%3D0.jpg",
                "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4",
                "短跑锻炼",
                TagFinal.TYPE_TXT_LEFT_TITLE));

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }

    /**
     * ----------------------------retrofit-----------------------
     */
    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        ViewTool.dismissProgressDialog();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.userGetTermListRes !=null){
                String result=b.userGetTermListRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                }else{
                    ViewTool.showToastShort(mActivity,"error");
                }
            }

        }else{
            try {
                assert response.errorBody() != null;
                String s=response.errorBody().string();
                Logger.e(StringUtils.getTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            ViewTool.showToastShort(mActivity,StringUtils.getTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        ViewTool.showToastShort(mActivity,R.string.fail_do_not);
        Logger.e("onFailure  :"+call.request().headers().toString());
        ViewTool.dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }



    //配置video_jvc
    @Override
    public void onBackPressed() {

        Logger.e("onBackPressed");
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.e("onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    /**
     * 双击退出函数
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }


    private void exitBy2Click() {
//        Logger.e(String.valueOf(JCVideoPlayer.backPress()));//不能重复调用
        if (!JCVideoPlayer.backPress()) {
            JCVideoPlayer.releaseAllVideos();
            finish();
        }
    }


}
