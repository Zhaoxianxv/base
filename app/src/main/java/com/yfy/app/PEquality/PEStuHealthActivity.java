package com.yfy.app.PEquality;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yfy.app.PEquality.adapter.PEStuHealthAdapter;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.final_tag.data.Base;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.recycerview.EndlessRecyclerOnScrollListener;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PEStuHealthActivity extends BaseActivity {
    private static final String TAG = PEStuHealthActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        getData();
        initSQToolbar();
        initRecycler();
        setAdapterData();


    }

    private String title;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);

    }
    private void initSQToolbar() {
        assert toolbar!=null;
        toolbar.setTitle(title);


    }


    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    public PEStuHealthAdapter adapter;
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
        adapter=new PEStuHealthAdapter(mActivity);
        recyclerView.setAdapter(adapter);

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
        keyValue_adapter.add(new KeyValue("mmHg","96","血压",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("次/分","75","脉率",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("ml","75","肺活量",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("cm","135","身高",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("cm","40","体重",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("kg/m²","40","BMI",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("","","左眼视力",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("","4.0","右眼视力",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("","良好","心",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("","良好","肺",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("","良好","肝",TagFinal.TYPE_ITEM));
        keyValue_adapter.add(new KeyValue("","良好","脾",TagFinal.TYPE_ITEM));

        adapter.setDataList(keyValue_adapter);
        adapter.setLoadState(TagFinal.LOADING_END);
    }


    /**
     * ----------------------------retrofit-----------------------
     */
    public void getTerm() {
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetTermListReq req = new UserGetTermListReq();
        req.setSession_key(Base.user.getSession_key());
        //获取参数
        reqBody.userGetTermListReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().user_get_term_list_api(env);
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.userGetTermListRes !=null){
                String result=b.userGetTermListRes.result;
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    Logger.e("");
                }else{
                    toastShow("error");
                }
            }

        }else{
            try {
                assert response.errorBody() != null;
                String s=response.errorBody().string();
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            toastShow(StringUtils.stringToGetTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        toastShow(R.string.fail_do_not);
        Logger.e("onFailure  :"+call.request().headers().toString());
        dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }
}
