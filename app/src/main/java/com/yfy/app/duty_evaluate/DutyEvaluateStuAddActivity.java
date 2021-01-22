package com.yfy.app.duty_evaluate;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yfy.app.bean.KeyValue;
import com.yfy.app.duty_evaluate.adapter.DutyEvaluateStuAddAdapter;
import com.yfy.app.duty_evaluate.bean.DutyEvaluateRes;
import com.yfy.app.duty_evaluate.bean.InfoBean;
import com.yfy.app.duty_evaluate.bean.SeBean;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.dialog.CPWBean;
import com.yfy.final_tag.hander.AssetsAsyncTask;
import com.yfy.final_tag.hander.AssetsGetFileData;
import com.yfy.final_tag.stringtool.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.viewtools.ViewTool;
import com.yfy.view.SQToolBar;

import java.util.ArrayList;
import java.util.List;


public class DutyEvaluateStuAddActivity extends BaseActivity implements AssetsGetFileData {
    private static final String TAG = DutyEvaluateStuAddActivity.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swip_recycler_main);
        Logger.e(TAG);
        getData();
        initRecycler();

        getAssetsData("duty_evaluate_get_stu_detail.txt");
    }


    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    private String year_value,month_value;
    public void getData(){
        Intent intent=getIntent();
        year_value=intent.getStringExtra(Base.year_value);
        month_value=intent.getStringExtra(Base.month_value);
        initSQToolbar(Base.user.getName());
    }

    public void initSQToolbar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenuText(TagFinal.ONE_INT,"完成");
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
               finish();
            }
        });

    }



    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    public DutyEvaluateStuAddAdapter adapter;
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
                getAssetsData("duty_evaluate_get_stu_detail.txt");
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线

        adapter=new DutyEvaluateStuAddAdapter(mActivity);
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


























    //async task

    AssetsAsyncTask mTask;
    public void getAssetsData(String file_name){
        showProgressDialog("");
        mTask=new AssetsAsyncTask(this);
        mTask.execute(file_name);
    }


    private List<KeyValue> adapter_data_list=new ArrayList<>();

    private void initAdapterData(DutyEvaluateRes res){
        adapter_data_list.clear();


        for (InfoBean info:res.getInfo()){
            KeyValue bean=new KeyValue();
            bean.setView_type(TagFinal.TYPE_ITEM);
            bean.setTitle(info.getParent_title());
            bean.setValue(info.getParent_all_score());

            List<CPWBean> item_list=new ArrayList<>();
            for (SeBean seBean:info.getEvaluate_list()){
                CPWBean cpwBean=new CPWBean();
                cpwBean.setName(seBean.getTitle());
                cpwBean.setOne(seBean.getStu_score());
                cpwBean.setTwo(seBean.getFather_score());
                item_list.add(cpwBean);
            }

            bean.setCpwBeanArrayList(item_list);
            adapter_data_list.add(bean);
        }


        adapter.setDataList(adapter_data_list);
        adapter.setLoadState(TagFinal.LOADING_END);
    }


    @Override
    public void doUpData(String content) {
        dismissProgressDialog();
        closeSwipeRefresh();
        if (StringJudge.isEmpty(content)){
            ViewTool.showToastShort(mActivity,"没有数据，请从新尝试");
        }else{
            DutyEvaluateRes res=gson.fromJson(content,DutyEvaluateRes.class);
            initAdapterData(res);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTask!=null&&mTask.getStatus()==AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
        }
    }
}
