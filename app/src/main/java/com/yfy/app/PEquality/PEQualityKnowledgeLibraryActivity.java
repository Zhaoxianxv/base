package com.yfy.app.PEquality;

import android.graphics.Color;
import android.os.Bundle;


import com.yfy.app.PEquality.adapter.PEKnowledgeLibAdapter;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.recycerview.DefaultItemAnimator;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.recycerview.EndlessRecyclerOnScrollListener;
import com.yfy.final_tag.recycerview.RecycleViewDivider;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Response;

public class PEQualityKnowledgeLibraryActivity extends BaseActivity {
    private static final String TAG = PEQualityKnowledgeLibraryActivity.class.getSimpleName();



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
    public PEKnowledgeLibAdapter adapter;
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
        recyclerView.addItemDecoration(new RecycleViewDivider(
                mActivity,
                LinearLayoutManager.HORIZONTAL,
                1,
                getResources().getColor(R.color.Gray)));
        adapter=new PEKnowledgeLibAdapter(mActivity);
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

        KeyValue one=new KeyValue("奥林匹克运动会的发源地","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);
        KeyValue two=new KeyValue("奥林匹克运动会","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);
        KeyValue three=new KeyValue("发源地","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);
        KeyValue four=new KeyValue("奥林匹克运动会的发源地","奥林匹克运动会发源于两千多年前的古希腊，1894年06月23日，当被尊称为“奥林匹克之父”的法国教育家皮埃尔·德·顾拜旦与12个国家的79名代表决定成立国际奥委会",TagFinal.TYPE_ITEM);

        keyValue_adapter.add(one);
        keyValue_adapter.add(two);
        keyValue_adapter.add(three);
        keyValue_adapter.add(three);
        keyValue_adapter.add(three);
        keyValue_adapter.add(three);
        keyValue_adapter.add(three);
        keyValue_adapter.add(three);
        keyValue_adapter.add(four);
        keyValue_adapter.add(four);
        keyValue_adapter.add(four);
        keyValue_adapter.add(four);
        keyValue_adapter.add(four);

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
        //获取参数
        req.setSession_key(Base.user.getSession_key());

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
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                    Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                }else{
                    toastShow("error");
                }
            }

        }else{
            try {
                assert response.errorBody()!=null;
                String s=response.errorBody().string();
                Logger.e(StringUtils.getTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            toastShow(StringUtils.getTextJoint("数据错误:%1$d",response.code()));
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
